package iofiles;

import exceptions.EncoderFileException;
import org.apache.log4j.Logger;
import secure.DataBlock;
import secure.HashUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlainFile extends File {
    private static Logger log = Logger.getLogger(PlainFile.class);
    private List<DataBlock> dataList;


    public PlainFile(String pathname) {
        super(pathname);
    }

    private boolean isValidFilepath() {
        if (this.getPath() == null) {
            return false;
        }
        if (this.getPath().isEmpty()) {
            return false;
        }
        return true;
    }

    private byte[] readDataFromFile() throws EncoderFileException {
        if (!isValidFilepath() || !this.exists()) {
            log.error("Reading file to decode: file not found");
            throw new EncoderFileException("Reading file to decode: file not found");
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] result = null;
        try {
            FileInputStream stream = new FileInputStream(this);
            int size = 0;
            while (stream.available() > 0) {
                size += stream.available();
                result = new byte[stream.available()];
                stream.read(result, 0, stream.available());
                bytes.write(result);
            }

        } catch (IOException e) {
            log.error("Reading file to decode: error");
            throw new EncoderFileException("Reading file to decode: error");
        }
        return result;
    }

    private void writeDataToFile(byte[] data) throws EncoderFileException {
        if (!isValidFilepath()) {
            log.error("Writing decrypted file: invalid filepath");
            throw new EncoderFileException("Writing decrypted file: invalid filepath");
        }


        try {
            FileOutputStream stream = new FileOutputStream(this);
            stream.write(data);
        } catch (IOException e) {
            log.error("Writing decrypted file: error");
            throw new EncoderFileException("Writing decrypted file: error");
        }
    }

    private DataBlock getSizeBlock(int size) {
        byte[] tmp = new byte[DataBlock.BLOCK_SIZE];
        for (int i = 0; i < 4; i++) {
            tmp[i] = (byte) ((size >> i * 8) & 0xFF);
        }
        return new DataBlock(tmp);
    }

    private int getSizeValue(DataBlock block) {
        byte[] tmp = block.getBytes();
        int size = 0;
        for (int i = 0; i < 4; i++) {
            size |= (tmp[i] & 0xFF) << i * 8;
        }
        return size;
    }

    private List<DataBlock> createDataList(byte[] array) {
        List<DataBlock> result = new ArrayList<>();
        result.add(getSizeBlock(array.length));
        int pos = 0;
        while (pos < array.length) {
            int length = (array.length - pos) > DataBlock.BLOCK_SIZE ? DataBlock.BLOCK_SIZE : array.length - pos;
            byte[] temp = new byte[DataBlock.BLOCK_SIZE];
            System.arraycopy(array, pos, temp, 0, length);
            DataBlock block = new DataBlock(temp);
            result.add(block);
            pos += 16;
        }
        DataBlock hash = HashUtils.getHash(result);
        result.add(hash);
        return result;
    }

    private byte[] createByteArray(List<DataBlock> data) throws EncoderFileException {
        if (data.size() <= 2) {
            log.error("Decrypting file: invalid data");
            throw new EncoderFileException("Decrypting file: invalid data");
        }
        int size = getSizeValue(data.get(0));
        DataBlock hashBlock = data.remove(data.size() - 1);
        if (!HashUtils.checkHash(data, hashBlock)) {
            log.error("Decrypting file: invalid data");
            throw new EncoderFileException("Decrypting file: invalid data");
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Iterator<DataBlock> iterator = data.iterator();
        if (iterator.hasNext()) {
            iterator.next();
        }
        int pos = 0;
        while ((pos < size) && iterator.hasNext()) {
            DataBlock current = iterator.next();
            byte[] tmp = current.getBytes();
            int length = (size - pos) > 16 ? 16 : size - pos;
            stream.write(tmp, 0, length);
            pos += DataBlock.BLOCK_SIZE;
        }
        return stream.toByteArray();
    }

    public List<DataBlock> getData() throws EncoderFileException {
        if (dataList == null) {
            dataList = createDataList(readDataFromFile());
        }
        return dataList;
    }

    public void writeData(List<DataBlock> data) throws EncoderFileException {
        this.dataList = data;
        writeDataToFile(createByteArray(data));
    }
}
