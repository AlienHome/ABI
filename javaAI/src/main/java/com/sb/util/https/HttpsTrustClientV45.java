package com.sb.util.https;  
import java.security.cert.CertificateException; 
import java.security.cert.X509Certificate; 
import javax.net.ssl.SSLContext; 
import javax.net.ssl.TrustManager; 
import javax.net.ssl.X509TrustManager; 
import org.apache.http.conn.ssl.SSLConnectionSocketFactory; 
public class HttpsTrustClientV45 extends HttpsClientV45 { 
  public HttpsTrustClientV45() {  
  } 
  
  @Override
  public void prepareCertificate() throws Exception { 
    // 跳过证书验证 
    SSLContext ctx = SSLContext.getInstance("TLS"); 
    X509TrustManager tm = new X509TrustManager() { 
      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { 
      } 
  
      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { 
      } 
  
      @Override
      public X509Certificate[] getAcceptedIssuers() { 
        return null; 
      } 
    }; 
    // 设置成已信任的证书 
    ctx.init(null, new TrustManager[] { tm }, null); 
    this.connectionSocketFactory = new SSLConnectionSocketFactory(ctx); 
  } 
}