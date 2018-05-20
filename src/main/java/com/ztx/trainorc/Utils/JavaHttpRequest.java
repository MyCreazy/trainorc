package com.ztx.trainorc.Utils;

import org.springframework.stereotype.Component;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Map;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * java的http请求
 */
@Component
public class JavaHttpRequest {

    public String postRequestOne(String urlStr,boolean isUseProxy) throws NoSuchProviderException, NoSuchAlgorithmException, KeyManagementException, IOException {
        URL url = null;
        HttpsURLConnection conn = null;
        try {
            url = new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
            if (isUseProxy) {
                //创建本地代理用于抓数据监测
                InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 80);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
                conn = (HttpsURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpsURLConnection) url.openConnection();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //// 使用HTTPS请求，那么则需要证书等相关信息
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        TrustManager[] tm = {new HttpX509Manager()};
        // 初始化
        sslContext.init(null, tm, new java.security.SecureRandom());
        ;
        // 获取SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        conn.setSSLSocketFactory(sslContext.getSocketFactory());
        conn.setHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        conn.setSSLSocketFactory(ssf);
//自己生一个boundary
        String boundary = UUID.randomUUID().toString().replace("-", "");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
//指定Content-Type为multipart/form-data，并且指定一下boundary
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);

        File file = new File("E:\\vv.jpg");
        InputStream is = new FileInputStream(file);
        OutputStream os = conn.getOutputStream();

//注意注意：这里先发两个横杠哦！
        os.write(("–" + boundary + "\r\n").getBytes());
        os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n").getBytes());
        os.write("Content-Type: image/jpeg\r\n\r\n".getBytes());

        byte[] b = new byte[200];
        int len = 0;
        while (-1 != (len = is.read(b))) {
            os.write(b, 0, len);
        }

        os.write("\r\n".getBytes());

//注意注意：格式是：–boundary–。两个横杠加boundary，然后再有两个横杠
        os.write(("–" + boundary + "–\r\n").getBytes());
        os.flush();
        os.close();
        is.close();

        StringBuilder result=new StringBuilder();
        InputStream is2 = conn.getInputStream();
        BufferedReader buf = new BufferedReader(new InputStreamReader(is2));
        for (String line = buf.readLine(); null != line; line = buf.readLine()) {
            System.out.println(line);
            result.append(line);
        }

        is2.close();

        return result.toString();
    }


    /**
     * post请求
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @param contentType
     * @return
     */
    public  String postRequest(String urlStr, Map<String, String> textMap,
                                    Map<String, String> fileMap,String contentType,boolean isUseProxy) {
        String res = "";
        HttpsURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "----WebKitFormBoundarysbdgOQADp5JoWUnx";
        try {
            URL url = new URL(null,urlStr,new sun.net.www.protocol.https.Handler());
            if(isUseProxy) {
                //创建本地代理用于抓数据监测
                InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 80);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
                conn = (HttpsURLConnection) url.openConnection(proxy);
            }
            else
            {
                conn = (HttpsURLConnection) url.openConnection();
            }

            //// 使用HTTPS请求，那么则需要证书等相关信息
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            TrustManager[] tm = { new HttpX509Manager() };
            // 初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            ;
            // 获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            conn.setSSLSocketFactory(sslContext.getSocketFactory());
            conn.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            conn.setSSLSocketFactory(ssf);
            //conn.setConnectTimeout(5000);
            //conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                    System.out.println(inputName+","+inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    contentType = new MimetypesFileTypeMap().getContentType(file);
                    //contentType非空采用filename匹配默认的图片类型
                    if(!"".equals(contentType)){
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        }else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        }else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        }else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    System.out.println(inputName+","+filename);

                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(
                            new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }
}
