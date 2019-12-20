package com.sb.util.https;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;


public class HttpsCertifiedClientV45 extends HttpsClientV45{
    public HttpsCertifiedClientV45(){}

    @Override 
    public void prepareCertificate() throws Exception{
        // 获取密钥库
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream inputStream = new FileInputStream(new File("classpath:**.keystore"));
        try{
            trustStore.load(inputStream, "password".toCharArray());
        }finally{
            inputStream.close();
        }
        SSLContext sslContext = SSLContexts.custom()
                                .loadTrustMaterial(trustStore, TrustSelfSignedStrategy.INSTANCE)
                                .build();
        this.connectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
    }
}