package hhtask;

import hhtask.exceptions.EncoderFileException;
import hhtask.secure.KeysUtils;

import java.io.File;


public final class ArgumentsUtils {
    private ArgumentsUtils() {
    }

    public static Operation getOperation(final String[] args) {
        for (String str : args) {
            if ("-e".equals(str)) {
                return Operation.ENCRYPT;
            }
            if ("-d".equals(str)) {
                return Operation.DECRYPT;
            }
        }
        return Operation.ENCRYPT;
    }


    public static byte[] getKey(final String[] args) throws EncoderFileException {
        int i;
        for (i = 0; i < args.length; i++) {
            if ("-k".equals(args[i])) {
                i++;
                break;
            }
        }
        if ((i > args.length - 1) || args[i] == null || args[i].isEmpty() || (args[i].startsWith("-"))) {
            File file = new File("key.key");
            if (file.exists()) {
                byte[] key = KeysUtils.readKeyFromFile("key.key");
                return key;
            }
            byte[] key = KeysUtils.generateKey();
            KeysUtils.saveKeyToFile("key.key", key);
            return key;
        }
        String str = args[i];

        if (str.endsWith(".key")) {
            return KeysUtils.readKeyFromFile(str);
        }
        return KeysUtils.getKeyFromString(str);
    }


    public static String getPlainPath(final String[] args) {
        int i;
        for (i = 0; i < args.length; i++) {
            if ("-p".equals(args[i])) {
                i++;
                break;
            }
        }
        if ((i > args.length - 1) || args[i] == null || args[i].isEmpty() || (args[i].startsWith("-"))) {
            return null;
        } else {
            return args[i];
        }
    }

    public static String getCipherPath(final String[] args) {
        int i;
        for (i = 0; i < args.length; i++) {
            if ("-c".equals(args[i])) {
                i++;
                break;
            }
        }
        if ((i > args.length - 1) || args[i] == null || args[i].isEmpty() || (args[i].startsWith("-"))) {
            return null;
        } else {
            return args[i];
        }
    }

    public static boolean isHelpKey(final String[] args) {
        for (String str : args) {
            if ("-h".equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isVersionKey(final String[] args) {
        for (String str : args) {
            if ("-v".equals(str)) {
                return true;
            }
        }
        return false;
    }


}
