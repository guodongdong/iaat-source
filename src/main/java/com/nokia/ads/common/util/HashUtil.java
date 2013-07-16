package com.nokia.ads.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

import org.apache.commons.lang.StringUtils;

public class HashUtil {

	/**/
	public static String md5HexString(String stringToHash) {
		byte[] md5b = md5(stringToHash);
		if (md5b == null) {
			return null;
		}
		return String.format("%032X", new BigInteger(1, md5b));
	}

	public static byte[] md5(String text) {
		if (StringUtils.isEmpty(text)) {
			return null;
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		md.update(text.getBytes());
		byte[] md5hash = md.digest();
		return md5hash;
	}

	public static byte[] sha1(String text) {
		if (StringUtils.isEmpty(text)) {
			return null;
		}
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			sha1.update(text.getBytes());
			return sha1.digest();
		} catch (Exception e) {
			return null;
		}
	}

	public enum HashAlgorithm {

		/**
		 * Native hash (String.hashCode()).
		 */
		NATIVE_HASH,
		/**
		 * CRC_HASH as used by the perl API. This will be more consistent both
		 * across multiple API users as well as java versions, but is mostly
		 * likely significantly slower.
		 */
		CRC_HASH,
		/**
		 * FNV hashes are designed to be fast while maintaining a low collision
		 * rate. The FNV speed allows one to quickly hash lots of data while
		 * maintaining a reasonable collision rate.
		 * 
		 * @see <a href="http://www.isthe.com/chongo/tech/comp/fnv/">fnv
		 *      comparisons</a>
		 * @see <a href="http://en.wikipedia.org/wiki/Fowler_Noll_Vo_hash">fnv
		 *      at wikipedia</a>
		 */
		FNV1_64_HASH,
		/**
		 * Variation of FNV.
		 */
		FNV1A_64_HASH,
		/**
		 * 32-bit FNV1.
		 */
		FNV1_32_HASH,
		/**
		 * 32-bit FNV1a.
		 */
		FNV1A_32_HASH,
		/**
		 * MD5-based hash algorithm used by ketama.
		 */
		KETAMA_HASH;

		private static final long FNV_64_INIT = 0xcbf29ce484222325L;
		private static final long FNV_64_PRIME = 0x100000001b3L;

		private static final long FNV_32_INIT = 2166136261L;
		private static final long FNV_32_PRIME = 16777619;

		private static MessageDigest md5Digest = null;

		static {
			try {
				md5Digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("MD5 not supported", e);
			}
		}

		/**
		 * Compute the hash for the given key.
		 * 
		 * @return a positive integer hash
		 */
		public long hash(final String k) {
			long rv = 0;
			int len = k.length();
			switch (this) {
			case NATIVE_HASH:
				rv = k.hashCode();
				break;
			case CRC_HASH:
				// return (crc32(shift) >> 16) & 0x7fff;
				CRC32 crc32 = new CRC32();
				crc32.update(getKeyBytes(k));
				rv = (crc32.getValue() >> 16) & 0x7fff;
				break;
			case FNV1_64_HASH:
				// Thanks to pierre@demartines.com for the pointer
				rv = FNV_64_INIT;
				for (int i = 0; i < len; i++) {
					rv *= FNV_64_PRIME;
					rv ^= k.charAt(i);
				}
				break;
			case FNV1A_64_HASH:
				rv = FNV_64_INIT;
				for (int i = 0; i < len; i++) {
					rv ^= k.charAt(i);
					rv *= FNV_64_PRIME;
				}
				break;
			case FNV1_32_HASH:
				rv = FNV_32_INIT;
				for (int i = 0; i < len; i++) {
					rv *= FNV_32_PRIME;
					rv ^= k.charAt(i);
				}
				break;
			case FNV1A_32_HASH:
				rv = FNV_32_INIT;
				for (int i = 0; i < len; i++) {
					rv ^= k.charAt(i);
					rv *= FNV_32_PRIME;
				}
				break;
			case KETAMA_HASH:
				byte[] bKey = computeMd5(k);
				rv = ((long) (bKey[3] & 0xFF) << 24)
						| ((long) (bKey[2] & 0xFF) << 16)
						| ((long) (bKey[1] & 0xFF) << 8) | (bKey[0] & 0xFF);
				break;
			default:
				assert false;
			}
			return rv & 0xffffffffL; /* Truncate to 32-bits */
		}

		/**
		 * Get the md5 of the given key.
		 */
		public static byte[] computeMd5(String k) {
			MessageDigest md5;
			try {
				md5 = (MessageDigest) md5Digest.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException("clone of MD5 not supported", e);
			}
			md5.update(getKeyBytes(k));
			return md5.digest();
		}

		public static byte[] getKeyBytes(String k) {
			try {
				return k.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
