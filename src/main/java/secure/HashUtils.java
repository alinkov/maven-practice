package secure;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public final class HashUtils {

    private HashUtils() {
    }

    public static DataBlock getHash(List<DataBlock> data) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (DataBlock block : data) {
            try {
                stream.write(block.getBytes());
            } catch (IOException e) {
            }
        }
        byte[] hash = DigestUtils.md5(stream.toByteArray());
        return new DataBlock(hash);
    }

    public static boolean checkHash(List<DataBlock> data, DataBlock hash) {
        return hash.equals(getHash(data));
    }
}
