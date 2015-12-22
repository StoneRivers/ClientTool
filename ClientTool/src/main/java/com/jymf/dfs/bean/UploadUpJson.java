package com.jymf.dfs.bean;

/**
 * 上传上行数据包包头Json POJO类
 * @author Zhang
 * @version 0.1
 */
public class UploadUpJson {

    private String itemId = null;
    private String fileType = null;
    private int fileOrder = 0;
    private String extension = null;
    private int bodyLength = 0;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileOrder() {
        return fileOrder;
    }

    public void setFileOrder(int fileOrder) {
        this.fileOrder = fileOrder;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }
}
