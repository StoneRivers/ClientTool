package com.jymf.dfs.bean;

/**
 * 下载上行数据包包头中Json POJO类
 * @author Zhang
 * @version 0.1
 */
public class DownloadUpJson {

    private String username = null;

    private String itemId = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
