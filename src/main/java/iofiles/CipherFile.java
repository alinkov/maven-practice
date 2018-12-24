package iofiles;

import exceptions.EncoderFileException;
import org.apache.log4j.Logger;
import secure.DataBlock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CipherFile extends File {

    private List<DataBlock> dataList;
    private static Logger log = Logger.getLogger(CipherFile.class);

    public CipherFile(String pathname) {
        super(pathname);
    }

    private byte[] readDataFromFile() throws EncoderFileException {
        if (!isValidFilepath() || !this.exists()) {
            log.error("Readind encrypted file: file not found");
            throw new EncoderFileException("Readind encrypted file: file not found");
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
            if (size % 16 != 0) {
                log.error("Readind encrypted file: invalid file");
                throw new EncoderFileException("Readind encrypted file: invalid file");
            }

        } catch (IOException e) {
            log.error("Readind encrypted file: error");
            throw new EncoderFileException("Readind encrypted file: error");
        }
        return result;
    }

    private void writeDataToFile(byte[] data) throws EncoderFileException {
        if (!isValidFilepath()) {
            log.error("Writing encrypted file: Invalid filepath");
            throw new EncoderFileException("Writing encrypted file: Invalid filepath");
        }

        try {
            FileOutputStream stream = new FileOutputStream(this);
            stream.write(data);
        } catch (IOException e) {
            log.error("Writing encrypted file: error");
            throw new EncoderFileException("Writing encrypted file: error");
        }
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


    private List<DataBlock> createDataList(byte[] array) {
        List<DataBlock> result = new ArrayList<>(array.length / DataBlock.BLOCK_SIZE);
        int length = DataBlock.BLOCK_SIZE;
        int pos = 0;
        while (pos < array.length) {
            byte[] temp = new byte[length];
            System.arraycopy(array, pos, temp, 0, length);
            DataBlock block = new DataBlock(temp);
            result.add(block);
            pos += length;
        }
        return result;
    }

    private byte[] createByteArray(List<DataBlock> data) throws EncoderFileException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for (DataBlock block : data) {
            try {
                stream.write(block.getBytes());
            } catch (IOException e) {

            }
        }
        return stream.toByteArray();
    }
}
