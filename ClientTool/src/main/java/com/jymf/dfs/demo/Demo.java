package com.jymf.dfs.demo;

import com.jymf.dfs.Client;

public class Demo {
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        String uploadResult = client.upload(1, "1024_12345678", "spec", 1, "txt", new byte[]{97,97,97});
        String downloadResult[] = client.download(3,"1024_12345678");
        client.close();
    }
}
