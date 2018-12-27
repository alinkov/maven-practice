import hhtask.exceptions.EncoderFileException;
import hhtask.iofiles.PlainFile;
import org.junit.Test;
import hhtask.secure.AES;
import hhtask.secure.DataBlock;
import hhtask.secure.KeysUtils;


import java.util.List;


import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestEncrypting {

    @Test
    public void testEncrypting() throws EncoderFileException {
        PlainFile file = new PlainFile("src/test/resources/testfile.txt");
        List<DataBlock> plainData = file.getData();
        AES aes1 = new AES(KeysUtils.getKeyFromString("123"));
        List<DataBlock> cipherData = aes1.encryptData(plainData);
        List<DataBlock> decryptedData1 = aes1.decryptData(cipherData);
        AES aes2 = new AES(KeysUtils.getKeyFromString("124"));
        List<DataBlock> decryptedData2 = aes2.decryptData(cipherData);

        boolean result1 = true;
        for (int i = 0; i < plainData.size(); i++) {
            if (!plainData.get(i).equals(decryptedData1.get(i))) {
                result1 = false;
                break;
            }
        }

        boolean result2 = true;
        for (int i = 0; i < plainData.size(); i++) {
            if (!plainData.get(i).equals(decryptedData2.get(i))) {
                result2 = false;
                break;
            }
        }
        assertTrue(result1);
        assertFalse(result2);
    }

}
