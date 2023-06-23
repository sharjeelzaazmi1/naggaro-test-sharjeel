package com.naggaro.javatest.Utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
public class StringUtils {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    private final char[] symbols;

    private final char[] buf;

    public StringUtils(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public StringUtils(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public StringUtils(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public StringUtils() {
        this(21);
    }

    public static long getHash(byte[] bytes) {

        CRC32 crc32 = new CRC32();
        Adler32 adl32 = new Adler32();

        crc32.update(bytes);
        adl32.update(bytes);

        long crc = crc32.getValue();
        long adl = adl32.getValue();

        return (crc << 32) | adl;
    }


}