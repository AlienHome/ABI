package com.sb.util.https;

import java.util.ArrayList; 
import java.util.List; 
import java.util.Map; 
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;  
import java.util.Arrays;
import java.io.InputStream;
import java.io.FileInputStream;

import org.apache.http.HttpEntity; 
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.Header;
import org.apache.http.client.HttpClient; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpGet; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair; 
import org.apache.http.util.EntityUtils;  
import org.apache.http.entity.StringEntity;

import com.sb.util.ImageBase64Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsClientV45Util {  
  static Logger logger = LoggerFactory.getLogger(HttpsClientV45Util.class);
  public static String doPost(HttpClient httpClient, String url, Map<String, String> paramHeader, 
      Map<String, String> paramBody) throws Exception { 
    String result = null; 
    HttpPost httpPost = new HttpPost(url); 
    setHeader(httpPost, paramHeader); 
    setBody(httpPost, paramBody, "UTF-8");  
    HttpResponse response = httpClient.execute(httpPost); 
    if (response != null) { 
      HttpEntity resEntity = response.getEntity(); 
      if (resEntity != null) { 
        result = EntityUtils.toString(resEntity, "UTF-8"); 
      } 
    } 
    return result; 
  } 
  
  public static String doPostGetHeader(HttpClient httpClient, String url, Map<String, String> paramHeader, 
      String paramBody) throws Exception { 
    String result = null; 
    HttpPost httpPost = new HttpPost(url); 
    setHeader(httpPost, paramHeader); 
    StringEntity entity = new StringEntity(paramBody, ContentType.APPLICATION_JSON);
    System.out.format("entity:"+entity+"\n");
    httpPost.setEntity(entity);
    HttpResponse response = httpClient.execute(httpPost);
    // System.out.format("all headers"+response.getAllHeaders()+"\n"); 
    // System.out.format("all headers"+response.getAllHeaders()+"\n");
    Header[] headersAll = response.getAllHeaders();
    // for(Header header:headersAll){
    //   System.out.format("header all value:"+header.getValue()+"\n");
    // }
    Header[] headersPart = response.getHeaders("X-Subject-Token");
    String resultHeader = headersPart[0].getValue();
    return resultHeader;
    // for(Header header:headersPart){
    //   System.out.format("header part:"+header.getValue()+"\n");
    // }
    // if (response != null) { 
    //   HttpEntity resEntity = response.getEntity(); 
    //   if (resEntity != null) { 
    //     result = EntityUtils.toString(resEntity, "UTF-8"); 
    //   } 
    // } 
  
    // return result; 
  } 
    
  public static String doGet(HttpClient httpClient, String url, Map<String, String> paramHeader, 
      Map<String, String> paramBody) throws Exception { 
  
    String result = null; 
    HttpGet httpGet = new HttpGet(url); 
    if(paramHeader != null){
      setHeader(httpGet, paramHeader);
    }
     
    HttpResponse response = httpClient.execute(httpGet); 
    if (response != null) { 
      HttpEntity resEntity = response.getEntity(); 
      if (resEntity != null) { 
        result = EntityUtils.toString(resEntity, "UTF-8"); 
      } 
    } 
    // logger.info("image:{}", result);
    return result; 
  } 

  public static String doGetImage(HttpClient httpClient, String url, Map<String, String> paramHeader, 
      Map<String, String> paramBody) throws Exception { 
  
    String result = null;
    String imageBase64 = null; 
    String readContent = null;
    HttpGet httpGet = new HttpGet(url); 
    if(paramHeader != null){
      setHeader(httpGet, paramHeader);
    }
     
    HttpResponse response = httpClient.execute(httpGet); 
    if (response != null) { 
      HttpEntity resEntity = response.getEntity(); 
      logger.info("image entity:{}", resEntity);
      InputStream imageContent = resEntity.getContent();
      int dataLength = (int)resEntity.getContentLength();
      int dataL = imageContent.available();
      logger.info("image bytes length:{}, available:{}", dataLength, dataL);
      logger.info("image stream:{}", imageContent);

      byte[] imageBytes = new byte[dataLength];
      // logger.info("image bytes:{}", imageBytes);
      int readCount = 0;
      while(readCount<=dataLength){
        if(readCount == dataLength) break;
        readCount += imageContent.read(imageBytes, readCount, dataLength-readCount);
      }
      // readContent = new String(imageBytes, 0, readCount, "UTF-8");
      // imageBase64 = ImageBase64Util.string2Base64(readContent);
      // logger.info("image base64:{}", imageBase64);
      // imageContent.read(imageBytes);
      // imageContent.close();
      imageBase64 = ImageBase64Util.bytes2Base64(imageBytes);
      // logger.info("image base64:{}", imageBase64);
      if (resEntity != null) { 
        result = EntityUtils.toString(resEntity, "UTF-8"); 
      } 
    } 
    // logger.info("image:{}", result);
    // return result; 
    // return readContent;
    return imageBase64;
  } 
  
  private static void setHeader(HttpRequestBase request, Map<String, String> paramHeader) { 
    // 设置Header 
    if (paramHeader != null) { 
      Set<String> keySet = paramHeader.keySet(); 
      for (String key : keySet) { 
        request.addHeader(key, paramHeader.get(key)); 
      } 
    } 
  } 
  
  private static void setBody(HttpPost httpPost, Map<String, String> paramBody, String charset) throws Exception { 
    // 设置参数 
    if (paramBody != null) { 
      System.out.format("param body: "+paramBody+"\n");
      List<NameValuePair> list = new ArrayList<NameValuePair>();    
      Set<String> keySet = paramBody.keySet(); 
      for (String key : keySet) { 
        list.add(new BasicNameValuePair(key, paramBody.get(key))); 
      } 
      if (list.size() > 0) { 
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset); 
        httpPost.setEntity(entity); 
      } 
    } 
  } 
} 