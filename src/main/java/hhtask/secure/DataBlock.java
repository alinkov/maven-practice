package hhtask.secure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DataBlock {
    public static final int BLOCK_SIZE = 16;
    private final byte[] blockData;

    public DataBlock(final byte[] data) {
        this.blockData = data;
    }


    public byte[] getBytes() {
        return blockData;
    }

    public static DataBlock xorBlocks(final DataBlock a, final DataBlock b) {
        byte[] bytes = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            bytes[i] = (byte) (a.getBytes()[i] ^ b.getBytes()[i]);
        }
        return new DataBlock(bytes);
    }

    @Override
    public String toString() {
        return "DataBlock" + Arrays.toString(blockData);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataBlock block = (DataBlock) o;
        if (this.getBytes().length != block.getBytes().length) {
            return false;
        }
        for (int i = 0; i < this.getBytes().length; i++) {
            if (this.getBytes()[i] != block.getBytes()[i]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return blockData.hashCode();
    }

    public static List<DataBlock> getList(final byte[] array) {
        List<DataBlock> result = new ArrayList<>();
        int pos = 0;
        while (pos < array.length) {
            int length;
            if ((array.length - pos) > BLOCK_SIZE) {
                length = BLOCK_SIZE;
            } else {
                length = array.length - pos;
            }
            byte[] temp = new byte[BLOCK_SIZE];
            System.arraycopy(array, pos, temp, 0, length);
            DataBlock block = new DataBlock(temp);
            result.add(block);
            pos += BLOCK_SIZE;
        }
        return result;
    }
}
