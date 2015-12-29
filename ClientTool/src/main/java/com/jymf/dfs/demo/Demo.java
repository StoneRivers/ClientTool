package com.jymf.dfs.demo;

import com.jymf.dfs.Client;

import java.io.*;
import java.util.Arrays;

public class Demo {

    //上传产品json    args:/home/jymf/jymfweb/json
    /*public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        String base = args[0];
        String[] companyIds = new File(base).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.startsWith("c")) {
                    return false;
                }
                return true;
            }
        });
        try {
            for (String companyId : companyIds) {
                File file = new File(base + "/" + companyId);
                for (File file1 : file.listFiles()) {
                    String fileName = file1.getName();
                    String proId = fileName.substring(0,fileName.lastIndexOf("."));
                    client.upload("JYMF",2,companyId+"_"+proId,"spec",1,"json",getBytes(file1));
                    System.out.println(companyId+"_"+proId);
                    //System.out.println(new String(getBytes(file1),"utf-8"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }*/

    //上传公司json  args:/home/jymf/jymfweb/json/company
    /*public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        String base = args[0];

        try {
            for (File file : new File(base).listFiles()) {
                String fileName = file.getName();
                String companyId = fileName.substring(0,fileName.lastIndexOf("."));
                client.upload("JYMF",1,companyId,"spec",1,"json",getBytes(file));
                System.out.println(companyId);
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
        client.close();
    }*/

    //上传公司和产品img   args:/home/jymf/jymfweb/img
    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
        String base = args[0];
        String[] companyIds = new File(base).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return  !(name.startsWith("a")||name.startsWith("n"));
            }
        });

        for (String companyId : companyIds) {
            String companyBase = base+"/"+companyId+"/company";
            String productBase = base+"/"+companyId+"/product";

            dealMinDirectory(companyBase,client,1,companyId,"intr");
            dealProduct(companyId,productBase,client);
        }
        client.close();

    }

    public static void dealMinDirectory(String URL,Client client,int copCode,String itemId,String fileType){
        File directory = new File(URL);
        if (!directory.exists()) {
            return;
        }
        String[] fileNames = directory.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("jpg");
            }
        });
        Arrays.sort(fileNames);
        int index = 0;
        for (String fileName : fileNames) {
            try {
                //client.upload("JYMF",copCode,itemId,fileType,++index,"jpg",getBytes(new File(URL+"/"+fileName)));
                System.out.println(copCode+"  "+itemId+"  "+fileType+"   "+(++index)+"  "+URL+"/"+fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static void dealProduct(String companyId,String productBase,Client client) {
        File fileBase = new File(productBase);
        if (!fileBase.exists()){
            return;
        }
        String[] pro1Id = fileBase.list();
        for (String s : pro1Id) {
            String oneProInfoBase = productBase+"/"+s+"/info";
            dealMinDirectory(oneProInfoBase,client,2,companyId+"_"+s,"intr");
            String oneProSummBase = productBase+"/"+s+"/summ";
            dealMinDirectory(oneProSummBase,client,2,companyId+"_"+s,"spec");
        }


    }


    public static byte[] getBytes(File file){
        byte[] buffer = null;
        try {
            //File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
