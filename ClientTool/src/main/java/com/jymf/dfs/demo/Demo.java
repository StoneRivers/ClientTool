package com.jymf.dfs.demo;

import com.jymf.dfs.Client;
import com.jymf.dfs.exception.MyException;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        try {
            String uploadResult = client.upload(1, "1024_12345678", "spec", 1, "txt", new byte[]{97,97,97});
            System.out.println(uploadResult);
            String downloadResult[] = client.download(3,"1024_12345678");
            System.out.println(Arrays.toString(downloadResult));
        } catch (MyException e) {
            e.printStackTrace();
        }
        client.close();
    }
}
