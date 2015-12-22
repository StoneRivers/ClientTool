package com.jymf.dfs.tool;

import java.util.zip.CRC32;

/**
 * Crc校验工具类
 * @author Zhang
 * @version 0.1
 */
public class CrcTool {

    /**
     * 检测字节数组最后4位是不是前部分的Crc32校验码
     * @param data 待检测全部数组数据,含Crc32校验码
     * @return booleaan 检测结果
     */
    public static boolean isCorrectCrc32(byte[] data){
        CRC32 crc32 = new CRC32();
        crc32.update(data,0,data.length-4);
        return (int)crc32.getValue()==ByteArrayTool.bytesToInt(data,data.length-4);
    }

    /**
     * 获取字节数组的的Crc32校验码
     * @param data byte[] 待生成Crc32校验码的字节数组
     * @return int 用int表示的Crc32校验码,主意Crc32校验码是无符号的,所以这里int可能死负数
     */
    public static int crc32(byte[] data){
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return (int)crc32.getValue();
    }

}

