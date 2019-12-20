package com.sb.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageBase64Util{
    static Base64.Encoder b64Encoder = Base64.getEncoder();
    static Base64.Decoder b64Decoder = Base64.getDecoder();
    public static String image2Base64(String path){
        String imgFile = path;
        InputStream in = null;
        byte[] imgBase64Byte = null;
        try{
            in = new FileInputStream(imgFile);
            imgBase64Byte = new byte[in.available()];
            in.read(imgBase64Byte);
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return b64Encoder.encodeToString(imgBase64Byte);
    }

    public static boolean base64ToImage(String imageBase64, String imageSavePath) throws Exception{
        if(imageBase64 == null){
            return false;
        }else{
            OutputStream out = null;
            out = new FileOutputStream(imageSavePath);
            byte[] imgBytes = b64Decoder.decode(imageBase64);
            for(int i=0;i<imgBytes.length;++i){
                if(imgBytes[i]<0){
                    imgBytes[i] += 256;
                }
            }
            out.write(imgBytes);
            return true;
        }
    }

    public static String string2Base64(String data) throws Exception{
        byte[] dataBytes = data.getBytes("UTF-8");
        String dataEncoder = b64Encoder.encodeToString(dataBytes);
        return dataEncoder;
    }

    public static String bytes2Base64(byte[] data) throws Exception{
        return b64Encoder.encodeToString(data);
    }
}