package com.nokia.ads.common.queue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.nokia.ads.common.util.IOHelper;
import com.nokia.ads.common.util.Log;

@SuppressWarnings("serial")
public abstract class SerializableQueue<T> implements SerializableQueueIface<T> {

	static public class SerializerType {
		final public static int JAVA = 1;
		final public static int THRIFT = 2;
	}

	protected ConcurrentLinkedQueue<T> queue;

	protected static final int EOF_MARK = -1;

	protected static final String JAVA_FILE_SUFFIX = ".pojo";
	protected static final String THRIFT_FILE_SUFFIX = ".thft";

	protected static final Map<Integer, String> SERIALIZER_TYPE_FILE_EXT_MAP = new HashMap<Integer, String>() {

		{
			put(SerializerType.JAVA, JAVA_FILE_SUFFIX);
			put(SerializerType.THRIFT, THRIFT_FILE_SUFFIX);
		}

	};

	protected static final Map<Integer, SuffixFileFilter> SERIALIZER_TYPE_FILE_FILTER_MAP = new HashMap<Integer, SuffixFileFilter>() {

		{
			put(SerializerType.JAVA, new SuffixFileFilter(JAVA_FILE_SUFFIX));
			put(SerializerType.THRIFT, new SuffixFileFilter(THRIFT_FILE_SUFFIX));
		};
	};

	private static final Log CP_LOGGER = Log.getLogger(SerializableQueue.class);

	protected final int serializerType;

	protected final int maxEntryPerFile;

	protected final String queuePath;

	protected final File queueDir;

	protected final SuffixFileFilter fileFilter;

	protected SerializableQueue(String queuePath, int maxEntryPerFile,
			int serializerType) {
		this.queue = new ConcurrentLinkedQueue<T>();
		this.queuePath = queuePath.endsWith(File.separator) ? queuePath
				: (queuePath + File.separator);
		if (!IOHelper.dirExists(queuePath)) {
			throw new IllegalArgumentException("Invalid queue path: "
					+ queuePath);
		}
		this.queueDir = new File(queuePath);
		this.maxEntryPerFile = maxEntryPerFile;
		this.serializerType = serializerType;
		this.fileFilter = SERIALIZER_TYPE_FILE_FILTER_MAP.get(serializerType);
	}

	protected abstract byte[] toBytes(T t);

	protected abstract T toObject(byte[] bytes);

	@Override
	public void add(T t) {
		queue.add(t);
	}

	@Override
	public void addAll(Collection<T> c) {
		queue.addAll(c);
	}

	@Override
	public Iterator<T> iterator() {
		return queue.iterator();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public void clear() {
		queue.clear();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public int serialize(Iterator<T> iterator) {

		int numSerialized = 0;

		if (!iterator.hasNext()) {
			return numSerialized;
		}

		String fileName = null;
		String fileExt = null;

		fileExt = SERIALIZER_TYPE_FILE_EXT_MAP.get(serializerType);

		File file = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;

		int fileEntryCount = 0;
		int totalByteCount = 0;
		byte[] buffer = null;
		int bufferLength = 0;

		fileName = queuePath + System.currentTimeMillis() + fileExt;
		file = new File(fileName);
		try {
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);

			while (iterator.hasNext()) {

				if (fileEntryCount >= maxEntryPerFile) {

					oos.writeInt(EOF_MARK);
					oos.flush();
					bos.flush();
					fos.flush();
					oos.close();
					bos.close();
					fos.close();

					numSerialized += fileEntryCount;

					fileName = queuePath + System.currentTimeMillis() + fileExt;
					file = new File(fileName);
					fos = new FileOutputStream(file);
					oos = new ObjectOutputStream(fos);
					fileEntryCount = 0;
				}

				T params = iterator.next();

				buffer = toBytes(params);

				if (buffer != null) {
					bufferLength = buffer.length;
					if (bufferLength > 0) {
						oos.writeInt(bufferLength);
						oos.write(buffer);
						iterator.remove();
						fileEntryCount++;
						totalByteCount += bufferLength;
					}
				}

			}

			numSerialized += fileEntryCount;
			oos.writeInt(EOF_MARK);
			oos.flush();
			bos.flush();
			fos.flush();

		} catch (FileNotFoundException e) {
			CP_LOGGER.warn("File not found during serialization: ", e);
		} catch (IOException e) {
			CP_LOGGER.warn("IOException during serialization: ", e);
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(fos);
			CP_LOGGER.debug(totalByteCount + " bytes written...");
		}

		return numSerialized;

	}

	@Override
	public int serializeAll() {

		int numSerialized = 0;

		if (!queue.isEmpty()) {
			Iterator<T> iterator = queue.iterator();
			numSerialized += serialize(iterator);
			CP_LOGGER.debug(numSerialized + " entries serialized..");
		}

		return numSerialized;

	}

	@Override
	public int deserialize(File file) {

		int numDeserialized = 0;
		boolean success = true;

		String fileName = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;

		try {
			fileName = file.getCanonicalPath();
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);

			byte[] buffer = null;

			int len = ois.readInt();

			while (len > 0) {
				buffer = new byte[len];
				ois.readFully(buffer);

				T t = toObject(buffer);

				if (t == null) {
					success = false;
					CP_LOGGER
							.warn("Unexpected null object while deserializing...");
					break;
				}

				queue.add(t);
				numDeserialized++;
				len = ois.readInt();
			}

			if (success) {
				FileUtils.forceDelete(file);
			}

		} catch (IOException e) {
			CP_LOGGER.warn("Error deserializing Object file: " + fileName, e);
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(fis);
		}

		CP_LOGGER.debug(numDeserialized + " entries from " + fileName
				+ " loaded to queue...");

		return numDeserialized;

	}

	@Override
	public int deserializeAll() {

		int numDeserialized = 0;

		List<File> files = listQueueFiles();

		for (File file : files) {
			if (file.length() > 0) {
				numDeserialized += deserialize(file);
			}
		}

		CP_LOGGER.debug(numDeserialized + " entries deserialized...");

		return numDeserialized;
	}

	@Override
	public List<File> listQueueFiles() {
		return (List<File>) FileUtils.listFiles(queueDir, fileFilter, null);
	}

}
