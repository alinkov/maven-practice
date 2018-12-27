package hhtask.secure;


import java.util.ArrayList;
import java.util.List;

public final class AES {

    private static final int[] SBOX = {
            0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
            0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
            0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
            0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
            0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
            0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
            0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
            0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
            0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
            0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
            0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
            0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
            0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
            0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
            0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
            0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
    };

    private static final int[] ISBOX = {
            0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
            0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
            0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
            0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
            0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
            0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
            0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
            0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
            0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
            0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
            0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
            0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
            0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
            0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
            0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
            0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
    };

    private static final byte[] INITIAL_VECTOR = {
            0x3A, 0x3B, 0x61, -0x4F, 0x4F, -0x40, 0x6E, -0x4B, 0x2B, 0x22, 0x0D, -0x68, 0x41, 0x12, 0x36, 0x11
    };


    private static final int[] RCON = {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x30, 0x40, 0x1b, 0x36
    };
    private static final int AES_BLOCK_SIZE = 16;
    private static final int AES_KEY_SIZE = 16;

    private int[] expandedKey = new int[11 * AES_KEY_SIZE];

    public AES(final byte[] key) {
        expandKey(key);
    }

    public List<DataBlock> encryptData(final List<DataBlock> data) {
        List<DataBlock> result = new ArrayList<>(data.size());
        DataBlock feedBack = new DataBlock(INITIAL_VECTOR);
        for (int i = 0; i < data.size(); i++) {
            DataBlock plain = DataBlock.xorBlocks(data.get(i), feedBack);
            DataBlock cipher = encryptBlock(plain);
            feedBack = cipher;
            result.add(cipher);
        }
        return result;
    }

    public List<DataBlock> decryptData(final List<DataBlock> data) {
        List<DataBlock> result = new ArrayList<>(data.size());
        DataBlock feedBack = new DataBlock(INITIAL_VECTOR);
        for (int i = 0; i < data.size(); i++) {
            DataBlock temp = data.get(i);
            DataBlock plain = decryptBlock(temp);
            plain = DataBlock.xorBlocks(plain, feedBack);
            feedBack = temp;
            result.add(plain);
        }
        return result;
    }


    private DataBlock encryptBlock(final DataBlock block) {

        DataBlock result = addRoundKey(0, block);
        for (int i = 1; i <= 9; i++) {
            result = subBytes(result);
            result = shiftRows(result);
            result = mixColumns(result);
            result = addRoundKey(i, result);
        }
        result = subBytes(result);
        result = shiftRows(result);
        result = addRoundKey(10, result);
        return result;
    }


    private DataBlock decryptBlock(final DataBlock block) {

        DataBlock result = addRoundKey(10, block);
        for (int i = 9; i >= 1; i--) {
            result = invShiftRows(result);
            result = invSubBytes(result);
            result = addRoundKey(i, result);
            result = invMixColumns(result);
        }
        result = invShiftRows(result);
        result = invSubBytes(result);
        result = addRoundKey(0, result);
        return result;
    }


    private DataBlock addRoundKey(final int round, final DataBlock block) {
        byte[] array = block.getBytes();
        byte[] result = new byte[AES_BLOCK_SIZE];
        for (int i = 0; i < AES_BLOCK_SIZE; i++) {
            result[i] = (byte) (array[i] ^ 0xFF & expandedKey[round * 16 + i]);
        }
        return new DataBlock(result);
    }

    private void expandKey(final byte[] key) {
        for (int i = 0; i < key.length; i++) {
            expandedKey[i] = 0xFF & key[i];
        }

        for (int i = 4; i < 44; i++) {
            int pad = i * 4;
            if (i % 4 == 0) {
                expandedKey[pad + 0] = SBOX[expandedKey[pad + 1 - 4]] ^ expandedKey[pad + 0 - 16] ^ RCON[(i / 4) - 1];
                expandedKey[pad + 1] = SBOX[expandedKey[pad + 2 - 4]] ^ expandedKey[pad + 1 - 16];
                expandedKey[pad + 2] = SBOX[expandedKey[pad + 3 - 4]] ^ expandedKey[pad + 2 - 16];
                expandedKey[pad + 3] = SBOX[expandedKey[pad + 0 - 4]] ^ expandedKey[pad + 3 - 16];
            } else {
                expandedKey[pad + 0] = expandedKey[pad + 0 - 4] ^ expandedKey[pad + 0 - 16];
                expandedKey[pad + 1] = expandedKey[pad + 1 - 4] ^ expandedKey[pad + 1 - 16];
                expandedKey[pad + 2] = expandedKey[pad + 2 - 4] ^ expandedKey[pad + 2 - 16];
                expandedKey[pad + 3] = expandedKey[pad + 3 - 4] ^ expandedKey[pad + 3 - 16];
            }
        }


    }

    private DataBlock subBytes(final DataBlock block) {
        byte[] array = block.getBytes();
        byte[] result = new byte[AES_BLOCK_SIZE];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) SBOX[array[i] & 0xFF];
        }
        return new DataBlock(result);
    }


    private DataBlock invSubBytes(final DataBlock block) {
        byte[] array = block.getBytes();
        byte[] result = new byte[AES_BLOCK_SIZE];
        for (int i = 0; i < array.length; i++) {
            result[i] = (byte) ISBOX[array[i] & 0xFF];
        }
        return new DataBlock(result);
    }


    private DataBlock shiftRows(final DataBlock block) {
        byte[] array = block.getBytes();
        byte[] result = new byte[AES_BLOCK_SIZE];
        result[0] = array[0];
        result[1] = array[5];
        result[2] = array[10];
        result[3] = array[15];
        result[4] = array[4];
        result[5] = array[9];
        result[6] = array[14];
        result[7] = array[3];
        result[8] = array[8];
        result[9] = array[13];
        result[10] = array[2];
        result[11] = array[7];
        result[12] = array[12];
        result[13] = array[1];
        result[14] = array[6];
        result[15] = array[11];
        return new DataBlock(result);
    }


    private DataBlock invShiftRows(final DataBlock block) {
        byte[] array = block.getBytes();
        byte[] result = new byte[AES_BLOCK_SIZE];
        result[0] = array[0];
        result[1] = array[13];
        result[2] = array[10];
        result[3] = array[7];
        result[4] = array[4];
        result[5] = array[1];
        result[6] = array[14];
        result[7] = array[11];
        result[8] = array[8];
        result[9] = array[5];
        result[10] = array[2];
        result[11] = array[15];
        result[12] = array[12];
        result[13] = array[9];
        result[14] = array[6];
        result[15] = array[3];
        return new DataBlock(result);
    }


    private DataBlock mixColumns(final DataBlock block) {
        byte[] array = block.getBytes();
        byte[] result = new byte[AES_BLOCK_SIZE];

        for (int i = 0; i < 4; i++) {
            byte a0 = array[i * 4 + 0];
            byte a1 = array[i * 4 + 1];
            byte a2 = array[i * 4 + 2];
            byte a3 = array[i * 4 + 3];

            result[i * 4 + 0] = (byte) (mul2(a0) ^ mul3(a1) ^ mul1(a2) ^ mul1(a3));
            result[i * 4 + 1] = (byte) (mul1(a0) ^ mul2(a1) ^ mul3(a2) ^ mul1(a3));
            result[i * 4 + 2] = (byte) (mul1(a0) ^ mul1(a1) ^ mul2(a2) ^ mul3(a3));
            result[i * 4 + 3] = (byte) (mul3(a0) ^ mul1(a1) ^ mul1(a2) ^ mul2(a3));
        }
        return new DataBlock(result);
    }


    private DataBlock invMixColumns(final DataBlock block) {
        byte[] array = block.getBytes();
        byte[] result = new byte[AES_BLOCK_SIZE];

        for (int i = 0; i < 4; i++) {
            byte a0 = array[i * 4 + 0];
            byte a1 = array[i * 4 + 1];
            byte a2 = array[i * 4 + 2];
            byte a3 = array[i * 4 + 3];

            result[i * 4 + 0] = (byte) (mulE(a0) ^ mulB(a1) ^ mulD(a2) ^ mul9(a3));
            result[i * 4 + 1] = (byte) (mul9(a0) ^ mulE(a1) ^ mulB(a2) ^ mulD(a3));
            result[i * 4 + 2] = (byte) (mulD(a0) ^ mul9(a1) ^ mulE(a2) ^ mulB(a3));
            result[i * 4 + 3] = (byte) (mulB(a0) ^ mulD(a1) ^ mul9(a2) ^ mulE(a3));
        }
        return new DataBlock(result);
    }


    private byte mul1(final byte b) {
        return b;
    }

    private byte mul2(final byte b) {
        byte a = (byte) ((b << 1) & 0xff);
        if ((b & 0x80) == 0) {
            return a;
        } else {
            return (byte) (a ^ 0x1b);
        }
    }

    private byte mul3(final byte b) {
        return (byte) ((mul2(b) ^ b) & 0xFF);
    }

    private byte mul9(final byte b) {
        return (byte) ((mul2(mul2(mul2(b))) ^ b) & 0xFF);
    }

    private byte mulB(final byte b) {
        return (byte) ((mul2(mul2(mul2(b))) ^ mul2(b) ^ b) & 0xFF);
    }

    private byte mulD(final byte b) {
        return (byte) ((mul2(mul2(mul2(b))) ^ mul2(mul2(b)) ^ b) & 0xFF);
    }

    private byte mulE(final byte b) {
        return (byte) ((mul2(mul2(mul2(b))) ^ mul2(mul2(b)) ^ mul2(b)) & 0xFF);
    }


}
