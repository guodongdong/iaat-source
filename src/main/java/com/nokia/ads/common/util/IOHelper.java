package com.nokia.ads.common.util;

import java.io.*;

/**
 * Helper class for dealing with IO streams
 */
public class IOHelper {
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	public static int copy(final InputStream input, final OutputStream output)
			throws IOException {
		return copy(input, output, DEFAULT_BUFFER_SIZE);
	}

	public static int copyAndCloseInput(final InputStream input,
			final OutputStream output) throws IOException {
		try {
			return copy(input, output, DEFAULT_BUFFER_SIZE);
		} finally {
			input.close();
		}
	}

	public static int copyAndCloseInput(final InputStream input,
			final OutputStream output, int bufferSize) throws IOException {
		try {
			return copy(input, output, bufferSize);
		} finally {
			input.close();
		}
	}

	public static int copy(final InputStream input, final OutputStream output,
			int bufferSize) throws IOException {
		int avail = input.available();
		if (avail > 262144) {
			avail = 262144;
		}
		if (avail > bufferSize) {
			bufferSize = avail;
		}
		final byte[] buffer = new byte[bufferSize];
		int n;
		n = input.read(buffer);
		int total = 0;
		while (-1 != n) {
			output.write(buffer, 0, n);
			total += n;
			n = input.read(buffer);
		}
		return total;
	}

	public static void copy(final Reader input, final Writer output,
			final int bufferSize) throws IOException {
		final char[] buffer = new char[bufferSize];
		int n;
		n = input.read(buffer);
		while (-1 != n) {
			output.write(buffer, 0, n);
			n = input.read(buffer);
		}
	}

	public static String toString(final InputStream input) throws IOException {
		StringBuilder buf = new StringBuilder();
		final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int n;
		n = input.read(buffer);
		while (-1 != n) {
			buf.append(new String(buffer, 0, n));
			n = input.read(buffer);
		}
		input.close();
		return buf.toString();
	}

	public static String toString(final Reader input) throws IOException {
		StringBuilder buf = new StringBuilder();
		final char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		int n;
		n = input.read(buffer);
		while (-1 != n) {
			buf.append(new String(buffer, 0, n));
			n = input.read(buffer);
		}
		input.close();
		return buf.toString();
	}

	public static String readStringFromStream(InputStream in)
			throws IOException {
		StringBuilder sb = new StringBuilder(1024);
		for (int i = in.read(); i != -1; i = in.read()) {
			sb.append((char) i);
		}
		in.close();
		return sb.toString();
	}

	public static byte[] readBytesFromStream(InputStream in) throws IOException {
		int i = in.available();
		if (i < DEFAULT_BUFFER_SIZE) {
			i = DEFAULT_BUFFER_SIZE;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream(i);
		copy(in, bos);
		in.close();
		return bos.toByteArray();
	}

	public static boolean dirExists(String path) {
		if (!isReadableDir(path)) {
			File dir = new File(path);
			try {
				return dir.mkdirs();
			} catch (SecurityException e) {
				return false;
			}
		}
		return true;
	}

	public static boolean isReadableDir(String dirName) {
		return isReadableDir(new File(dirName));
	}

	/**
	 * @return true if the argument is a readable directory
	 */
	public static boolean isReadableDir(File d) {
		return d.exists() && d.isDirectory() && d.canRead();
	}
}
