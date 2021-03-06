package com.nokia.ads.common.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * This class contains routines for hashing.
 *
 * <p>The hash takes a byte array representing arbitrary data (a number, String, or Object) and
 * turns it into a small, hopefully unique, number. There are additional convenience functions which
 * hash int, long, and String types.
 *
 * <p>There are 32 and 64 bit versions for generating differently sized hash values. There are
 * methods which take seeds and ones that do not. If no seed is supplied, digits of pi are used.
 * Supplying different seed values should yield independent hash values, useful for implementing
 * bloom filters, for example.
 *
 * <p><b>Note</b>:The hashing algorithm used here is also employed by the {@link StringHash} class.
 * If you modify the implementation here, you should make the corresponding modifications to
 * StringHash as well.
 *
 * <p><b>Note</b>: this hash has weaknesses in the two most-significant key bits and in the three
 * least-significant seed bits. The weaknesses are small and practically speaking, will not affect
 * the distribution of hash values. Still, it would be good practice not to choose seeds 0, 1, 2, 3,
 * ..., n to yield n, independent hash functions. Use pseudo-random seeds instead. See HashTest for
 * details on testing for weaknesses.
 *
 * <p>The original code for the hash function is courtesy
 * <a href="http://burtleburtle.net/bob/hash/evahash.html">Bob Jenkins</a>.
 *
 * @see StringHash
 */

public final class JenkinsHash {
  private JenkinsHash() {}

//  private static final Pattern FPRINT96_FORMAT =
//      Pattern.compile("\\p{XDigit}{8}_\\p{XDigit}{8}_\\p{XDigit}{8}");

  /** Default hash seed (32 bit) */
  private static final int SEED32 = 0x12b9b0a1; // pi; an arbitrary number

  /** Default hash seed (64 bit) */
  static final long SEED64 = 0x2b992ddfa23249d6L; // more of pi

  /** Hash constant (32 bit) */
  private static final int CONSTANT32 = 0x9e3779b9; // golden ratio; arbitrary

  /** Hash constant (64 bit) */
  static final long CONSTANT64 = 0xe08c1d668b756f82L; // more of golden

  /*******************
   * INTEGER HASHING *
   *******************/

  /**
   * Hash an int to a 32 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the int to hash
   * @return 32 bit hash value
   */
  public static int hash32(int value) {
    return mix32(value, CONSTANT32, SEED32);
  }

  /**
   * Hash an int to a 32 bit value using the supplied seed.
   *
   * @param value the int to hash
   * @param seed the seed
   * @return 32 bit hash value
   */
  public static int hash32(int value, int seed) {
    return mix32(value, CONSTANT32, seed);
  }

//  /**
//   * Returns a <a href="http://en.wikipedia.org/wiki/Consistent_hashing"> consistent hash</a> for a
//   * given 64-bit hash code. The consistent hash is an approximately uniformly-distributed value
//   * between zero and {@code n - 1} inclusive, having the property that, for a fixed {@code h},
//   * {@code consistentHash32(h, n)} equals either {@code consistentHash32(h, n - 1)} (with
//   * probability 1-1/n) or {@code n} (with probability 1/n). Informally, when adding a new bucket
//   * (shard), rehashing the values causes every value to either remain in the same bucket or move to
//   * the last bucket.
//   *
//   * @param h the hash code to assign to a bucket
//   * @param n the number of buckets
//   * @return bucket that h is hashed to, bounded between 0 to n - 1 inclusive
//   */
//  public static int consistentHash32(long h, int n) {
//    Preconditions.checkArgument(n > 0, "n must be positive: %s", n);
//
//    int b = 0; // The current bucket number, 1 <= b <= n (initially invalid)
//    int j = 1; // The destination of the next jump
//
//    // Jump from bucket to bucket until the next candidate is too large
//    while (j > 0 && j <= n) {
//      b = j;
//      h = 2862933555777941757L * h + 1;
//
//      // The code below implements j = floor(b/r) + 1.
//      double inv = ((double) (1L << 31)) / ((double) (int) ((h >>> 33) + 1));
//      j = (int) (b * inv + 1);
//    }
//
//    return b - 1;
//  }

  /****************
   * LONG HASHING *
   ****************/

  /**
   * Hash a long to a 32 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the long to hash
   * @return 32 bit hash value
   */
  public static int hash32(long value) {
    return (int) mix64(value, CONSTANT64, SEED64);
  }

  /**
   * Hash a long to a 32 bit value using the supplied seed. The hash is computed to 64 bits and the
   * lower 32 are returned.
   *
   * @param value the long to hash
   * @param seed the seed
   * @return 32 bit hash value
   */
  public static int hash32(long value, long seed) {
    return (int) mix64(value, CONSTANT64, seed);
  }

  /**
   * Hash a long to a 64 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the long to hash
   * @return 64 bit hash value
   */
  public static long hash64(long value) {
    return mix64(value, CONSTANT64, SEED64);
  }

  /**
   * Hash a long to a 64 bit value using the supplied seed.
   *
   * @param value the long to hash
   * @param seed the seed
   * @return 64 bit hash value
   */
  public static long hash64(long value, long seed) {
    return mix64(value, CONSTANT64, seed);
  }

  /******************
   * STRING HASHING *
   ******************/

  /**
   * Hash a string to a 32 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the string to hash
   * @return 32 bit hash value
   */
  public static int hash32(String value) {
    return hash32(value, SEED32);
  }

  /**
   * Hash a string to a 32 bit value using the supplied seed.
   *
   * @param value the string to hash
   * @param seed the seed
   * @return 32 bit hash value
   */
  public static int hash32(String value, int seed) {
    if (value == null) {
      return hash32(null, 0, 0, seed);
    }
    return hash32(value.getBytes(), seed);
  }

  /**
   * Hash a string to a 64 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the string to hash
   * @return 64 bit hash value
   */
  public static long hash64(String value) {
    return hash64(value, SEED64);
  }

  /**
   * Hash a string to a 64 bit value using the supplied seed.
   *
   * @param value the string to hash
   * @param seed the seed
   * @return 64 bit hash value
   */
  public static long hash64(String value, long seed) {
    if (value == null) {
      return hash64(null, 0, 0, seed);
    }
    return hash64(value.getBytes(), seed);
  }

  /**
   * Generates a fingerprint of an input string.
   *
   *
   */
  public static long fingerprint(String value) {
    // tural - changed input to getBytes
    byte[] temp = value.getBytes(Charsets.UTF8);
    return fingerprint(temp, 0, temp.length);
  }

  /**
   * Generate a 12 byte (96-bit) fingerprint from a string.
   *
   * <p>NOTE: All characters in the string will be converted to bytes via UTF-8. If your string is
   * not UTF-8 or a proper subset, get the bytes yourself and use @code{fprint96(byte[] value)}
   * instead.
   *
   * @param value a string from which to generate the 96-bit fingerprint
   * @return a 12 byte array which is the 96-bit fingerprint
   */
  public static byte[] fprint96(String value) {
    return fprint96(value.getBytes(Charsets.UTF8));
  }

  /**
   * Generate a 12 byte (96-bit) fingerprint from an array of bytes.
   *
   * @param value bytes from which to generate the 96-bit fingerprint
   * @return a 12 byte array which is the 96-bit fingerprint
   */
  public static byte[] fprint96(byte[] value) {
    // Can't use MessageDigestAlgorithm from j.c.g.common.io. :(
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-1");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-1 should be available in the JVM.");
    }
    md.update(value);
    byte[] hash = md.digest();
    return Arrays.copyOfRange(hash, 0, 12);
  }

  /**
   * Return a key from the 96-bit fingerprint of the given string's UTF-8 bytes.
   *
   * <p>The result is not usually human readable. If you want that then you should use {@code
   * fprint96AsString}. This functionality mirrors that of 'util/hash/fingerprint96' and the {@code
   * AsKey()} method.
   *
   * @param value string from which to get UTF-8 bytes return the fprint96 key
   * @return the direct String representation of the fprint96 as UTF-8.
   */
  public static String fprint96AsKey(String value) {
    byte[] fp96 = fprint96(value.getBytes(Charsets.UTF8));
    char[] chars = new char[12];
    for (int i = 0; i < 12; i++) {
      chars[i] = (char) (fp96[i] & 0xFF);
    }
    return new String(chars);
  }

  /**
   * This is the inverse of {@code fprint96AsKey(byte[] value)}.
   *
   * <p>Given the key string of a fprint96 convert it back into the fprint96 bytes.
   *
   * @param key a 12 character string
   * @return the 96-bit fingerprint as bytes
   */
  static byte[] keyToFprint96(String key) {
    byte[] fprint96 = new byte[12];
    for (int i = 0; i < 12; i++) {
      fprint96[i] = (byte) (key.charAt(i) & 0xFF);
    }
    return fprint96;
  }

  /**********************
   * BYTE ARRAY HASHING *
   **********************/

  /**
   * Hash byte array to a 32 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the bytes to hash
   * @return 32 bit hash value
   */
  public static int hash32(byte[] value) {
    return hash32(value, 0, value == null ? 0 : value.length);
  }

  /**
   * Hash byte array to a 32 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the bytes to hash
   * @param offset the starting position of value where bytes are used for the hash computation
   * @param length number of bytes of value that are used for the hash computation
   * @return 32 bit hash value
   */
  public static int hash32(byte[] value, int offset, int length) {
    return hash32(value, offset, length, SEED32);
  }

  /**
   * Hash byte array to a 32 bit value using the supplied seed.
   *
   * @param value the bytes to hash
   * @param seed the seed
   * @return 32 bit hash value
   */
  public static int hash32(byte[] value, int seed) {
    return hash32(value, 0, value == null ? 0 : value.length, seed);
  }

  /**
   * Hash byte array to a 32 bit value using the supplied seed.
   *
   * @param value the bytes to hash
   * @param offset the starting position of value where bytes are used for the hash computation
   * @param length number of bytes of value that are used for the hash computation
   * @param seed the seed
   * @return 32 bit hash value
   */
  @SuppressWarnings("fallthrough")
  private static int hash32(byte[] value, int offset, int length, int seed) {
    int a = CONSTANT32;
    int b = a;
    int c = seed;
    int keylen;

    for (keylen = length; keylen >= 12; keylen -= 12, offset += 12) {
      a += word32At(value, offset);
      b += word32At(value, offset + 4);
      c += word32At(value, offset + 8);

      // Mix
      a -= b; a -= c; a ^= c >>> 13;
      b -= c; b -= a; b ^= a << 8;
      c -= a; c -= b; c ^= b >>> 13;
      a -= b; a -= c; a ^= c >>> 12;
      b -= c; b -= a; b ^= a << 16;
      c -= a; c -= b; c ^= b >>> 5;
      a -= b; a -= c; a ^= c >>> 3;
      b -= c; b -= a; b ^= a << 10;
      c -= a; c -= b; c ^= b >>> 15;
    }

    // Hash any remaining bytes
    c += length;
    switch (keylen) { // deal with rest. Cases fall through
      case 11:
        c += (value[offset + 10]) << 24;
      case 10:
        c += (value[offset + 9] & 0xff) << 16;
      case 9:
        c += (value[offset + 8] & 0xff) << 8;
        // the first byte of c is reserved for the length
      case 8:
        b += word32At(value, offset + 4);
        a += word32At(value, offset);
        break;
      case 7:
        b += (value[offset + 6] & 0xff) << 16;
      case 6:
        b += (value[offset + 5] & 0xff) << 8;
      case 5:
        b += (value[offset + 4] & 0xff);
      case 4:
        a += word32At(value, offset);
        break;
      case 3:
        a += (value[offset + 2] & 0xff) << 16;
      case 2:
        a += (value[offset + 1] & 0xff) << 8;
      case 1:
        a += (value[offset + 0] & 0xff);
        // case 0 : nothing left to add
    }
    return mix32(a, b, c);
  }

  /**
   * Hash byte array to a 64 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the bytes to hash
   * @return 64 bit hash value
   */
  public static long hash64(byte[] value) {
    return hash64(value, 0, value == null ? 0 : value.length);
  }

  /**
   * Hash byte array to a 64 bit value. The digits of pi are used for the hash seed.
   *
   * @param value the bytes to hash
   * @param offset the starting position of value where bytes are used for the hash computation
   * @param length number of bytes of value that are used for the hash computation
   * @return 64 bit hash value
   */
  public static long hash64(byte[] value, int offset, int length) {
    return hash64(value, offset, length, SEED64);
  }

  /**
   * Hash byte array to a 64 bit value using the supplied seed.
   *
   * @param value the bytes to hash
   * @param seed the seed
   * @return 64 bit hash value
   */
  public static long hash64(byte[] value, long seed) {
    return hash64(value, 0, value == null ? 0 : value.length, seed);
  }

  /**
   * Hash byte array to a 64 bit value using the supplied seed.
   *
   * @param value the bytes to hash
   * @param offset the starting position of value where bytes are used for the hash computation
   * @param length number of bytes of value that are used for the hash computation
   * @param seed the seed
   * @return 64 bit hash value
   */
  @SuppressWarnings("fallthrough")
  public static long hash64(byte[] value, int offset, int length, long seed) {
    long a = CONSTANT64;
    long b = a;
    long c = seed;
    int keylen;

    for (keylen = length; keylen >= 24; keylen -= 24, offset += 24) {
      a += word64At(value, offset);
      b += word64At(value, offset + 8);
      c += word64At(value, offset + 16);

      // Mix
      a -= b; a -= c; a ^= c >>> 43;
      b -= c; b -= a; b ^= a << 9;
      c -= a; c -= b; c ^= b >>> 8;
      a -= b; a -= c; a ^= c >>> 38;
      b -= c; b -= a; b ^= a << 23;
      c -= a; c -= b; c ^= b >>> 5;
      a -= b; a -= c; a ^= c >>> 35;
      b -= c; b -= a; b ^= a << 49;
      c -= a; c -= b; c ^= b >>> 11;
      a -= b; a -= c; a ^= c >>> 12;
      b -= c; b -= a; b ^= a << 18;
      c -= a; c -= b; c ^= b >>> 22;
    }

    c += length;
    switch (keylen) { // deal with rest. Cases fall through
      case 23:
        c += ((long) value[offset + 22]) << 56;
      case 22:
        c += (value[offset + 21] & 0xffL) << 48;
      case 21:
        c += (value[offset + 20] & 0xffL) << 40;
      case 20:
        c += (value[offset + 19] & 0xffL) << 32;
      case 19:
        c += (value[offset + 18] & 0xffL) << 24;
      case 18:
        c += (value[offset + 17] & 0xffL) << 16;
      case 17:
        c += (value[offset + 16] & 0xffL) << 8;
        // the first byte of c is reserved for the length
      case 16:
        b += word64At(value, offset + 8);
        a += word64At(value, offset);
        break;
      case 15:
        b += (value[offset + 14] & 0xffL) << 48;
      case 14:
        b += (value[offset + 13] & 0xffL) << 40;
      case 13:
        b += (value[offset + 12] & 0xffL) << 32;
      case 12:
        b += (value[offset + 11] & 0xffL) << 24;
      case 11:
        b += (value[offset + 10] & 0xffL) << 16;
      case 10:
        b += (value[offset + 9] & 0xffL) << 8;
      case 9:
        b += (value[offset + 8] & 0xffL);
      case 8:
        a += word64At(value, offset);
        break;
      case 7:
        a += (value[offset + 6] & 0xffL) << 48;
      case 6:
        a += (value[offset + 5] & 0xffL) << 40;
      case 5:
        a += (value[offset + 4] & 0xffL) << 32;
      case 4:
        a += (value[offset + 3] & 0xffL) << 24;
      case 3:
        a += (value[offset + 2] & 0xffL) << 16;
      case 2:
        a += (value[offset + 1] & 0xffL) << 8;
      case 1:
        a += (value[offset + 0] & 0xffL);
        // case 0: nothing left to add
    }
    return mix64(a, b, c);
  }

  /**
   * Treating an array of bytes a word at a time is far more efficient than byte-by-byte. For 32 bit
   * words, treat the bytes as signed for compatibility with the C++ hash code.
   * This is not done for 64 bit and should be removed at the same time is nixed (if
   * ever).
   */
  private static int word32At(byte[] bytes, int offset) {
    return bytes[offset + 0]
        + (bytes[offset + 1] << 8)
        + (bytes[offset + 2] << 16)
        + (bytes[offset + 3] << 24);
  }

  private static long word64At(byte[] bytes, int offset) {
    return (bytes[offset + 0] & 0xffL)
        + ((bytes[offset + 1] & 0xffL) << 8)
        + ((bytes[offset + 2] & 0xffL) << 16)
        + ((bytes[offset + 3] & 0xffL) << 24)
        + ((bytes[offset + 4] & 0xffL) << 32)
        + ((bytes[offset + 5] & 0xffL) << 40)
        + ((bytes[offset + 6] & 0xffL) << 48)
        + ((bytes[offset + 7] & 0xffL) << 56);
  }

  /**
   * Generates a fingerprint of the specified bytes.
   *
   * @param value the bytes to fingerprint
   * @return 64 bit fingerprint
   * @see Hash#fingerprint(String)
   */
  public static long fingerprint(byte[] value) {
    return fingerprint(value, 0, value == null ? 0 : value.length);
  }

  /**
   * Generates a fingerprint of bytes {@code value[offset]} through {@code value[offset+length-1]}.
   *
   * @param value the bytes to fingerprint
   * @param offset the starting position of value where bytes are used for the fingerprint
   *        computation
   * @param length number of bytes that are used for the fingerprint computation
   * @return 64 bit fingerprint
   * @see Hash#fingerprint(String)
   */
  public static long fingerprint(byte[] value, int offset, int length) {
    int hi = hash32(value, offset, length, 0);
    int lo = hash32(value, offset, length, 102072);
    if ((hi == 0) && (lo == 0 || lo == 1)) {
      // Turn 0/1 into another fingerprint
      hi ^= 0x130f9bef;
      lo ^= 0x94a0a928;
    }
    return (((long) hi) << 32) | (lo & 0xffffffffl);
  }

  /***********************
   * BYTE BUFFER HASHING *
   ***********************/

  /**
   * Returns the hash32 of the specified number of bytes in the specified byte buffer, starting at
   * its current position. The specified buffer is must have <tt>LITTLE_ENDIAN</tt> byte order.
   *
   * <p>This call leaves the byte buffer's position in an undefined state but has no effect on the
   * mark.
   *
   * @throws NullPointerException if <tt>buf</tt> is null
   * @throws java.nio.BufferUnderflowException if buffer has fewer than <tt>length</tt>bytes
   *         remaining.
   * @throws IllegalArgumentException if <tt>buf</tt> byte order is <tt>BIG_ENDIAN</tt>.
   */
  @SuppressWarnings("fallthrough")
  public static int hash32(ByteBuffer buf, int length) {
    if (buf.order() != ByteOrder.LITTLE_ENDIAN)
      throw new IllegalArgumentException("Buffer must be little endian");

    int a = CONSTANT32;
    int b = a;
    int c = SEED32;

    // Hash all complete groups of 12 bytes
    int numGroups = length / 12;
    for (int i = 0; i < numGroups; i++) {
      a += getInt(buf);
      b += getInt(buf);
      c += getInt(buf);

      // Mix
      a -= b; a -= c; a ^= c >>> 13;
      b -= c; b -= a; b ^= a << 8;
      c -= a; c -= b; c ^= b >>> 13;
      a -= b; a -= c; a ^= c >>> 12;
      b -= c; b -= a; b ^= a << 16;
      c -= a; c -= b; c ^= b >>> 5;
      a -= b; a -= c; a ^= c >>> 3;
      b -= c; b -= a; b ^= a << 10;
      c -= a; c -= b; c ^= b >>> 15;
    }

    // Hash any remaining bytes
    c += length;
    int position = buf.position();
    switch (length - numGroups * 12) {
      case 11:
        c += (buf.get(position + 10)) << 24;
      case 10:
        c += (buf.get(position + 9) & 0xff) << 16;
      case 9:
        c += (buf.get(position + 8) & 0xff) << 8;
        // the first byte of c is reserved for the length
      case 8:
        b += getInt(buf, position + 4);
        a += getInt(buf, position);
        break;
      case 7:
        b += (buf.get(position + 6) & 0xff) << 16;
      case 6:
        b += (buf.get(position + 5) & 0xff) << 8;
      case 5:
        b += (buf.get(position + 4) & 0xff);
      case 4:
        a += getInt(buf, position);
        break;
      case 3:
        a += (buf.get(position + 2) & 0xff) << 16;
      case 2:
        a += (buf.get(position + 1) & 0xff) << 8;
      case 1:
        a += (buf.get(position) & 0xff);
        // case 0 : nothing left to add
    }

    return mix32(a, b, c);
  }

  /**
   * Returns the hash64 of the specified number of bytes in the specified byte buffer, starting at
   * its current position. The specified buffer is must have <tt>LITTLE_ENDIAN</tt> byte order.
   *
   * <p>This call leaves the byte buffer's position in an undefined state but has no effect on the
   * mark.
   *
   * @throws NullPointerException if <tt>buf</tt> is null
   * @throws java.nio.BufferUnderflowException if buffer has fewer than <tt>length</tt> bytes
   *         remaining.
   * @throws IllegalArgumentException if <tt>buf</tt> byte order is <tt>BIG_ENDIAN</tt>.
   */
  @SuppressWarnings("fallthrough")
  public static long hash64(ByteBuffer buf, int length) {
    if (buf.order() != ByteOrder.LITTLE_ENDIAN)
      throw new IllegalArgumentException("Buffer must be little endian");

    long a = CONSTANT64;
    long b = a;
    long c = SEED64;

    // Hash all complete groups of 24 bytes
    int numGroups = length / 24;
    for (int i = 0; i < numGroups; i++) {
      a += buf.getLong();
      b += buf.getLong();
      c += buf.getLong();

      // Mix
      a -= b; a -= c; a ^= c >>> 43;
      b -= c; b -= a; b ^= a << 9;
      c -= a; c -= b; c ^= b >>> 8;
      a -= b; a -= c; a ^= c >>> 38;
      b -= c; b -= a; b ^= a << 23;
      c -= a; c -= b; c ^= b >>> 5;
      a -= b; a -= c; a ^= c >>> 35;
      b -= c; b -= a; b ^= a << 49;
      c -= a; c -= b; c ^= b >>> 11;
      a -= b; a -= c; a ^= c >>> 12;
      b -= c; b -= a; b ^= a << 18;
      c -= a; c -= b; c ^= b >>> 22;
    }

    // Hash any remaining bytes
    c += length;
    int position = buf.position();
    switch (length - numGroups * 24) {
      case 23:
        c += ((long) buf.get(position + 22)) << 56;
      case 22:
        c += (buf.get(position + 21) & 0xffL) << 48;
      case 21:
        c += (buf.get(position + 20) & 0xffL) << 40;
      case 20:
        c += (buf.get(position + 19) & 0xffL) << 32;
      case 19:
        c += (buf.get(position + 18) & 0xffL) << 24;
      case 18:
        c += (buf.get(position + 17) & 0xffL) << 16;
      case 17:
        c += (buf.get(position + 16) & 0xffL) << 8;
        // the first byte of c is reserved for the length
      case 16:
        b += buf.getLong(position + 8);
        a += buf.getLong(position);
        break;
      case 15:
        b += (buf.get(position + 14) & 0xffL) << 48;
      case 14:
        b += (buf.get(position + 13) & 0xffL) << 40;
      case 13:
        b += (buf.get(position + 12) & 0xffL) << 32;
      case 12:
        b += (buf.get(position + 11) & 0xffL) << 24;
      case 11:
        b += (buf.get(position + 10) & 0xffL) << 16;
      case 10:
        b += (buf.get(position + 9) & 0xffL) << 8;
      case 9:
        b += (buf.get(position + 8) & 0xffL);
      case 8:
        a += buf.getLong(position);
        break;
      case 7:
        a += (buf.get(position + 6) & 0xffL) << 48;
      case 6:
        a += (buf.get(position + 5) & 0xffL) << 40;
      case 5:
        a += (buf.get(position + 4) & 0xffL) << 32;
      case 4:
        a += (buf.get(position + 3) & 0xffL) << 24;
      case 3:
        a += (buf.get(position + 2) & 0xffL) << 16;
      case 2:
        a += (buf.get(position + 1) & 0xffL) << 8;
      case 1:
        a += (buf.get(position + 0) & 0xffL);
        // case 0: nothing left to add
    }

    return mix64(a, b, c);
  }

  /**
   * Returns the int stored at the current position in the specified byte buffer plus its {@link
   * #addSignCruft "sign extension cruft"}. The byte buffer's ordering is assume to be
   * LITTLE_ENDIAN.
   *
   * <p>Increments the position by four.
   */
  private static int getInt(ByteBuffer buf) {
    return addSignCruft(buf.getInt());
  }

  /**
   * Returns the int stored at the specified position in the specified byte buffer plus its {@link
   * #addSignCruft "sign extension cruft"}. The byte buffer's ordering is assume to be
   * LITTLE_ENDIAN.
   */
  private static int getInt(ByteBuffer buf, int pos) {
    return addSignCruft(buf.getInt(pos));
  }

  /**
   * Returns the specified integer plus its "sign extension cruft."
   *
   * <p>Note to would-be refactorers: Do not be tempted to replace this method and the two above it
   * by simpler methods that read the bytes one at a time. This turns out to be performance critical
   * code, and these methods were carefully constructed to run very fast with the aid of extensive
   * benchmarking.
   */
  private static int addSignCruft(int i) {
    return (i & ~0x80808080) - (i & 0x80808080);
  }

  /*******************
   * MIXING METHODS *
   *******************/

  /**
   * Mixes ints a, b, and c, and returns the final value of c.
   */
  private static int mix32(int a, int b, int c) {
    a -= b; a -= c; a ^= c >>> 13;
    b -= c; b -= a; b ^= a << 8;
    c -= a; c -= b; c ^= b >>> 13;
    a -= b; a -= c; a ^= c >>> 12;
    b -= c; b -= a; b ^= a << 16;
    c -= a; c -= b; c ^= b >>> 5;
    a -= b; a -= c; a ^= c >>> 3;
    b -= c; b -= a; b ^= a << 10;
    c -= a; c -= b; c ^= b >>> 15;
    return c;
  }

  /**
   * Mixes longs a, b, and c, and returns the final value of c.
   */
  static long mix64(long a, long b, long c) {
    a -= b; a -= c; a ^= c >>> 43;
    b -= c; b -= a; b ^= a << 9;
    c -= a; c -= b; c ^= b >>> 8;
    a -= b; a -= c; a ^= c >>> 38;
    b -= c; b -= a; b ^= a << 23;
    c -= a; c -= b; c ^= b >>> 5;
    a -= b; a -= c; a ^= c >>> 35;
    b -= c; b -= a; b ^= a << 49;
    c -= a; c -= b; c ^= b >>> 11;
    a -= b; a -= c; a ^= c >>> 12;
    b -= c; b -= a; b ^= a << 18;
    c -= a; c -= b; c ^= b >>> 22;
    return c;
  }
}