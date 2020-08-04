package com.sofb.crwaler.framework.common.net.httpclient.manager;

import com.sofb.crwaler.framework.common.net.httpclient.config.DownloadConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * @author liuxuejun
 * @date 2019-10-09 11:13
 */
@Slf4j
public class ConnectionManager {
    private static int maxTotal =
            DownloadConfig.poolingHttpClientConnectionManagerConfig.getMaxTotal();

    private static int maxPerRoute =
            DownloadConfig.poolingHttpClientConnectionManagerConfig.getMaxPerRoute();

    /**
     * 返回clientConnectionManager
     */
    public PoolingHttpClientConnectionManager getDefaultConnectionManager() {

        Registry<ConnectionSocketFactory> reg =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.INSTANCE)
                        .register("https", buildSslConnectionSocketFactory())
                        .build();
        PoolingHttpClientConnectionManager httpClientConnectionManager =
                new PoolingHttpClientConnectionManager(reg);
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
        return httpClientConnectionManager;
    }

    /**
     * 兼容https
     */
    private SSLConnectionSocketFactory buildSslConnectionSocketFactory() {
        try {
            // 优先绕过安全证书
            return new SSLConnectionSocketFactory(createIgnoreVerifySsl());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("ssl connection fail", e);
        }
        return SSLConnectionSocketFactory.getSocketFactory();
    }

    private SSLContext createIgnoreVerifySsl()
            throws NoSuchAlgorithmException, KeyManagementException {
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager =
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };

        SSLContext sc = SSLContext.getInstance("SSLv3");
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }
}
