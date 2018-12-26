import exceptions.EncoderFileException;
import iofiles.CipherFile;
import iofiles.PlainFile;
import org.apache.log4j.Logger;
import secure.AES;
import secure.DataBlock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public final class Encoder {
    private Encoder() {
    }

    private static Logger log = Logger.getLogger(Encoder.class);

    public static void main(String[] args) {

        log.info("Start program");
        if (ArgumentsUtils.isHelpKey(args) || args.length == 0) {
            printHelp();
            return;
        }
        if (ArgumentsUtils.isVersionKey(args)) {
            printProperties();
            return;
        }

        Operation operation = ArgumentsUtils.getOperation(args);
        log.info("Operation type: " + operation);
        try {
            if (operation == Operation.ENCRYPT) {
                String plainPath = ArgumentsUtils.getPlainPath(args);
                if (plainPath == null) {
                    log.error("Plain filepath not specified with " + operation + " operation.");
                    System.err.println("No Path to file to encode");
                    return;
                }
                String cipherPath = ArgumentsUtils.getCipherPath(args);
                if (cipherPath == null) {
                    log.warn("Cipher filepath not specified");
                    File tmp = new File(plainPath);
                    String path = tmp.getParent();
                    String name = tmp.getName();
                    name = name.substring(0, name.indexOf('.'));
                    name += ".cip";
                    cipherPath = path + File.separator + name;
                }
                log.info("Plain filepath: " + plainPath);
                log.info("Cipher filepath: " + cipherPath);
                PlainFile plainFile = new PlainFile(plainPath);
                CipherFile cipherFile = new CipherFile(cipherPath);
                AES aes = new AES(ArgumentsUtils.getKey(args));
                List<DataBlock> data = plainFile.getData();
                data = aes.encryptData(data);
                cipherFile.writeData(data);
                log.info("Data encrypted");
            } else {
                String cipherPath = ArgumentsUtils.getCipherPath(args);
                if (cipherPath == null) {
                    log.error("Cipher filepath not specified with " + operation + " operation.");
                    System.err.println("No Path to file to decode");
                    return;
                }
                String plainPath = ArgumentsUtils.getPlainPath(args);
                if (plainPath == null) {
                    log.warn("Plain filepath not specified");
                    File tmp = new File(cipherPath);
                    String path = tmp.getParent();
                    String name = tmp.getName();
                    name = name.substring(0, name.indexOf('.'));
                    name += ".pla";
                    plainPath = path + File.separator + name;
                }
                log.info("Plain filepath: " + plainPath);
                log.info("Cipher filepath: " + cipherPath);
                PlainFile plainFile = new PlainFile(plainPath);
                CipherFile cipherFile = new CipherFile(cipherPath);
                AES aes = new AES(ArgumentsUtils.getKey(args));
                List<DataBlock> data = cipherFile.getData();
                data = aes.decryptData(data);
                plainFile.writeData(data);
                log.info("Data decrypted");
            }
        } catch (EncoderFileException e) {
            System.err.println(e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            System.err.print("Unknown error");
            log.error("Unknown error");
        }


    }

    public static void printProperties() {
        log.info("Print version");
        Properties property = new Properties();
        try (InputStream inputStream = Encoder.class.getClassLoader().getResourceAsStream("resource.properties")) {
            property.load(inputStream);
            String version = property.getProperty("project.version");
            String name = property.getProperty("project.name");
            System.out.println("Program " + name + ", version " + version);
        } catch (IOException e) {
            log.error("Properties file not found");
        }
    }

    public static void printHelp() {
        log.info("Print help");
        try (InputStream inputStream = Encoder.class.getClassLoader().getResourceAsStream("help.txt")) {
            while (inputStream.available() > 0) {
                System.out.write(inputStream.read());
            }
        } catch (IOException e) {
            log.error("Help file not found");
        }

    }
}