package com.jymf.dfs;

import com.jymf.dfs.bean.DownloadDownJson;
import com.jymf.dfs.bean.DownloadUpJson;
import com.jymf.dfs.bean.UploadUpJson;
import com.jymf.dfs.tool.ByteArrayTool;
import com.jymf.dfs.tool.JsonTool;
import com.jymf.dfs.tool.PacketTool;

import org.zeromq.ZMQ;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Properties;

/**
 * 客户端进行存取操作的客户端类
 * @author Zhang
 * @version 0.1
 */
public class Client {

    private static String URL = "218.245.0.113:5555";           //服务器URL
    private static int sendTimeout = 2;                         //传输数据链接超时时间,从配置文件中读取
    private static int recvTimeout = 2;                         //接收数据链接超时时间,从配置文件中读取

    private ZMQ.Context context = null;
    private ZMQ.Socket socket = null;

    /**
     * 读取配置文件
     */
    static{
        try {
            Properties properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("/client.properties");
            properties.load(inputStream);
            URL = properties.getProperty("server");
            sendTimeout = Integer.parseInt(properties.getProperty("send_timeout"));
            recvTimeout = Integer.parseInt(properties.getProperty("recv_timeout"));
        } catch (IOException e) {
            System.out.println("配置文件加载失败");
            e.printStackTrace();
        }
    }


    /**
     * 用于客户端向服务器上传文件
     * @param copCode int 操作码:1-上传公司信息,2-上传产品信息,3-下载公司信息,4-下载产品信息
     * @param itemId String 公司ID或产品ID
     * @param fileType String 文件类型:目前是spec,over,intr
     * @param fileOrder int 文件序:当前上传文件在相应文件类型中的序号
     * @param ext String 文件扩展名
     * @param fileContent data[] 文件数据流
     * @return String 文件的DfsID,与文件存储服务器地址拼接后可以直接通过http协议取文件
     *
     */
    public String upload(int copCode, String itemId, String fileType, int fileOrder, String ext, byte[] fileContent){
        UploadUpJson json = new UploadUpJson();
        json.setBodyLength(fileContent.length);
        json.setExtension(ext);
        json.setFileOrder(fileOrder);
        json.setFileType(fileType);
        json.setItemId(itemId);
        String jsonString = JsonTool.toJson(json);
        byte[] send = PacketTool.pack(copCode,jsonString,fileContent);
        System.out.println(Arrays.toString(send));
        boolean success = socket.send(send);
        if (!success){
            return "上传失败";
        }
        byte[] recv = socket.recv();
        System.out.println(Arrays.toString(recv));
        try {
            int jsonSize = ByteArrayTool.bytesToShort(recv, 4);
            String resultJson = new String(recv, 6, jsonSize, "utf-8");
            return resultJson;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "接收到的数据编码错误";
        }
    }

    /**
     * 用于客户端从服务器下载文件信息
     * @param copCode int 操作码:1-上传公司信息,2-上传产品信息,3-下载公司信息,4-下载产品信息
     * @param itemId String 公司ID或产品ID
     * @return String[] String[1]存储包头中的json字符串,String[2]中储存包体中的json字符串
     *
     */
    public String[] download(int copCode,String itemId){
        String[] result = new String[2];
        DownloadUpJson json = new DownloadUpJson();
        json.setItemId(itemId);
        String jsonString = JsonTool.toJson(json);

        byte[] send = PacketTool.pack(copCode,jsonString,null);
        System.out.println(Arrays.toString(send));
        boolean success = socket.send(send);
        if (!success){
            return new String[]{"下载失败",null};
        }
        byte[] recv = socket.recv();
        System.out.println(Arrays.toString(recv));
        try {
            int jsonSize = ByteArrayTool.bytesToShort(recv,4);
            result[0]  = new String(recv,6,jsonSize,"utf-8");
            DownloadDownJson downloadDownJson= (DownloadDownJson)JsonTool.toObject(result[0],DownloadDownJson.class);
            int bodyLength = downloadDownJson.getBodyLength();
            result[1] = new String(recv,6+jsonSize,bodyLength,"utf-8");
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String[]{"接收到的数据编码错误",null};
        }
    }

    /**
     * 用于客户端和服务器建立连接
     */
    public void connect(){
        context = ZMQ.context(1);
        socket = context.socket(ZMQ.REQ);
        socket.setSendTimeOut(sendTimeout);
        socket.setReceiveTimeOut(recvTimeout);
        socket.connect(URL);
    }

    /**
     * 用于关闭客户端与服务器的链接
     */
    public void close(){
        socket.close();
        context = null;
        socket = null;
    }


}