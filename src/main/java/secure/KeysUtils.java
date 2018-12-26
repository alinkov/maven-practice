package secure;


import exceptions.EncoderFileException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public final class KeysUtils {
    private static final int KEY_SIZE = 16;
    private static final int BYTE_MASK = 0xFF;
    private static final int LONG_BYTES_COUNT = 8;
    private static final int BYTE_BITS_COUNT = 8;
    private static Logger log = Logger.getLogger(KeysUtils.class);

    private KeysUtils() {
    }

    public static byte[] getKeyFromString(String str) {
        log.info("Creating key from password");
        return DigestUtils.md5(str);
    }

    public static byte[] generateKey() {
        log.info("Generating key");
        byte[] temp = new byte[KEY_SIZE];
        long nanotime = System.nanoTime();
        for (int i = 0; i < LONG_BYTES_COUNT; i++) {
            temp[i] = (byte) ((nanotime >> i * BYTE_BITS_COUNT) & BYTE_MASK);
        }
        long systemtime = System.currentTimeMillis();
        for (int i = 0; i < LONG_BYTES_COUNT; i++) {
            temp[i + LONG_BYTES_COUNT] = (byte) ((systemtime >> i * BYTE_BITS_COUNT) & BYTE_MASK);
        }
        return DigestUtils.md5(temp);
    }

    public static byte[] readKeyFromFile(String filepath) throws EncoderFileException {
        log.info("Reading keyfrom file");
        if (filepath == null || filepath.isEmpty() || !filepath.endsWith(".key")) {
            log.error("Reading key file: file not found");
            throw new EncoderFileException("Reading key file: file not found");
        }
        File keyFile = new File(filepath);
        byte[] result = new byte[KEY_SIZE];
        try (FileInputStream stream = new FileInputStream(keyFile)) {
            if (stream.available() != KEY_SIZE) {
                log.error("Reading key file: invalid key file");
                throw new EncoderFileException("Reading key file: invalid key file");
            }
            stream.read(result, 0, KEY_SIZE);
        } catch (IOException e) {
            log.error("Reading key file: error");
            throw new EncoderFileException("Reading key file: error");
        }
        return result;
    }

    public static void saveKeyToFile(String filepath, final byte[] key) throws EncoderFileException {
        log.info("Saving key to file");
        if (filepath == null || filepath.isEmpty()) {
            log.error("Writing key file: invalid filename");
            throw new EncoderFileException("Writing key file: invalid filename");
        }
        if (!filepath.endsWith(".key")) {
            filepath += ".key";
        }
        File keyFile = new File(filepath);
        try (FileOutputStream stream = new FileOutputStream(keyFile)) {
            stream.write(key);
        } catch (IOException e) {
            log.error("Writing key file: error");
            throw new EncoderFileException("Writing key file: error");
        }
    }
}
