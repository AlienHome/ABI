// package com.sb.util;

// import java.security.cert.CertificateException;
// import java.security.cert.X509Certificate;
// import javax.net.ssl.SSLContext;
// import javax.net.ssl.TrustManager;
// import javax.net.ssl.X509TrustManager;
// import org.apache.http.conn.HttpClientConnectionManager;
// import org.apache.http.conn.SchemePortResolver;
// import org.apache.http.config.Registry;
// import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
// import org.apache.http.impl.client.HttpClientBuilder;




// // Scheme https = new Scheme("https", 443, new MySecureSocketFactory());
// //  SchemeRegistry registry = new SchemeRegistry();
// //  registry.register(https);

// import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
// public class SSLClient extends HTTPSClient{
//     public SSLClient() throws Exception{
//         super();
//         SSLContext ctx = SSLContext.getInstance("TLS");
//         X509TrustManager tm = new X509TrustManager(){
        
//             @Override
//             public X509Certificate[] getAcceptedIssuers() {
//                 // TODO Auto-generated method stub
//                 return null;
//             }
        
//             @Override
//             public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                 // TODO Auto-generated method stub
                
//             }
        
//             @Override
//             public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                 // TODO Auto-generated method stub
                
//             }
//         };
//         ctx.init(null, new TrustManager[] {tm}, null);
//         // SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//         this.connectionSocketFactory = new SSLConnectionSocketFactory(ctx);
//     }
// }

