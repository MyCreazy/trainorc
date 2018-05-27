package com.ztx.trainorc.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 参数管理类
 */
@Component
@PropertySource({"classpath:apiconfig.properties"})
public class ParamManager {
    /**
     * API KEY
     */
    @Value("${api_key}")
    private String apiKey;

    /**
     * 秘钥key
     */
    @Value("${secret_key}")
    private String secretKey;

    /**
     * token_requesturl
     */
    @Value("${token_requesturl}")
    private String tokenRequesturl;

    /**
     * api URL
     */
    @Value("${api_url}")
    private String apiUrl;

    /**
     * request_config
     */
    @Value("${request_config}")
    private String requestConfig;

    /**
     * 过期天数
     */
    @Value("${tokenexpires_day}")
    private  int tokenexpiresDay;

    public int getTokenexpiresDay() {
        return tokenexpiresDay;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTokenRequesturl() {
        return tokenRequesturl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getRequestConfig() {
        return requestConfig;
    }
}
