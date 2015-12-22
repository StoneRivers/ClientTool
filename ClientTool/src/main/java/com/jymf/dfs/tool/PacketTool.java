package com.jymf.dfs.tool;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 依据分布式文件系统通信协议的数据打包工具类
 * @author Zhang
 * @version 0.1
 */
public class PacketTool {

    /**
     * 将必要数据打包成通信数据包
     * @param copeCode int 操作码
     * @param jsonString String 数据包包头中的Json字符串
     * @param content byte[] 数据包包体中的字节流
     * @return byte[] 打包完成后的数据字节流
     */
    public static byte[] pack(int copeCode,String jsonString,byte[] content){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream ops = new DataOutputStream(baos);
            ops.write(new String("JY").getBytes("utf-8"));
            ops.writeShort(copeCode);
            byte[] jsonBytes = jsonString.getBytes("utf-8");
            ops.writeShort((short)jsonBytes.length);
            ops.write(jsonBytes);
            if (content != null) {
                ops.write(content);
            }
            ops.writeInt(CrcTool.crc32(baos.toByteArray()));
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
