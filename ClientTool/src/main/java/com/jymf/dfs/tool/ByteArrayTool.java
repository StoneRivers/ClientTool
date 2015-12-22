package com.jymf.dfs.tool;

/**
 * 数组处理工具,负责数组和基本数据类型的互转
 * @author Zhang
 * @version 0.1
 */
public class ByteArrayTool {

    /**
     * short转byte[]
     * @param num short
     * @return byte[]
     */
    public static byte[] shortToBytes(short num) {
        int temp = num;
        byte[] bytes = new byte[2];
        for (int i = 1; i > -1; i--) {
            bytes[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最高位
            temp = temp >> 8; // 向右移8位
        }
        return bytes;
    }

    /**
     * byte[]前两位转short
     * @param bytes byte[]
     * @return short
     */
    public static short bytesToShort(byte[] bytes) {
        short num = 0;
        for (int i = 1; i > -1; i--) {
            num |= (bytes[i] & 0xff) << (8 * (1 - i));
        }
        return num;
    }

    /**
     * 将字节数组中指定的两个字节转化为short
     * @param bytes byte[]
     * @param offset int 转化起始字节的偏移量
     * @return short
     */
    public static short bytesToShort(byte[] bytes, int offset) {
        short num = 0;
        for (int i = 1 + offset; i > offset - 1; i--) {
            num |= (bytes[i] & 0xff) << (8 * (1 + offset - i));
        }
        return num;
    }

    /**
     * int转byte[]
     * @param num int
     * @return byte[]
     */
    public static byte[] intToBytes(int num) {
        int temp = num;
        byte[] bytes = new byte[4];
        for (int i = 3; i > -1; i--) {
            bytes[i] = new Integer(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return bytes;
    }

    /**
     * byte[]前四位转int
     * @param bytes byte[]
     * @return int
     */
    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0);
    }



    /**
     * 将字节数组中指定的四个字节转化为int
     * @param bytes byte[]
     * @param offset int 转化起始字节的偏移量
     * @return int
     */
    public static int bytesToInt(byte[] bytes, int offset) {
        int num = 0;
        for (int i = offset + 3; i > (offset - 1); i--) {
            num |= (bytes[i] & 0xff) << (8 * (3 + offset - i));
        }
        return num;
    }


    /**
     * long转byte[]
     * @param num long
     * @return byte[]
     */
    public static byte[] longToBytes(long num) {
        long temp = num;
        byte[] bytes = new byte[8];
        for (int i = 7; i > -1; i--) {
            bytes[i] = new Long(temp & 0xff).byteValue();
            temp = temp >> 8;
        }
        return bytes;
    }

    /**
     * byte[]前八位转long
     * @param bytes byte[]
     * @return long
     */
    public static long bytesToLong(byte[] bytes) {
        long num = 0;
        for (int i = 7; i > -1; i--) {
            num |= (bytes[i] & 0xff) << (8 * (7 - i));
        }
        return num;
    }

    /**
     * 将字节数组中指定的两个字节转化为long
     * @param bytes byte[]
     * @param offset int 转化起始字节的偏移量
     * @return longch
     */
    public static long bytesToLong(byte[] bytes,int offset) {
        long num = 0;
        for (int i = offset+7; i > offset-1; i--) {
            num |= (bytes[i] & 0xff) << (8 * (offset + 7 - i));
        }
        return num;
    }
}


