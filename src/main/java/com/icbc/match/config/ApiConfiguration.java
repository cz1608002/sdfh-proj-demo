package com.icbc.match.config;

import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcConstants;
import com.icbc.api.UiIcbcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * API SDK配置类，初始化DefaultIcbcClient和UiIcbcClient，并注册到Spring容器
 */
@Configuration
@Slf4j
public class ApiConfiguration {

    /**
     * 当配置文件中配置icbc.api.type=RSA时执行以下注入
     */
    @Configuration
    @ConditionalOnProperty(prefix = "icbc.api", name = "type", havingValue = "RSA")
    static class RsaApiConfiguation {
        @Value("${icbc.api.appid}")
        private String appId; //应用ID

        @Value("${icbc.api.privKey}")
        private String privateKey; //应用私钥

        @Value("${icbc.api.gw.pubkey}")
        private String apigwPubKey; //网关公钥


        /**
         * DefaultIcbcClient，并注册到Spring容器
         *
         * @return DefaultIcbcClient
         */
        @Bean
        public DefaultIcbcClient defaultIcbcClient() {
            //初始化DefaultIcbcClient
            return new DefaultIcbcClient(appId, IcbcConstants.SIGN_TYPE_RSA2, privateKey, apigwPubKey);
        }

        /**
         * 初始化UiIcbcClient，并注册到Spring容器
         *
         * @return UiIcbcClient
         */
        @Bean
        public UiIcbcClient uiIcbcClient() {
            return new UiIcbcClient(appId, privateKey, IcbcConstants.CHARSET_UTF8);
        }
    }

    /**
     * 当配置文件中配置icbc.api.type=CA时执行以下注入
     */
    @Configuration
    @ConditionalOnProperty(prefix = "icbc.api", name = "type", havingValue = "CA")
    static class CaApiConfiguration {
        @Value("${icbc.api.appid}")
        private String appId; //应用ID

        @Value("${icbc.api.privKey}")
        private String privateKey; //应用私钥

        @Value("${icbc.api.ca}")
        private String ca; //ca证书

        @Value("${icbc.api.password}")
        private String password; //ca证书密码

        @Value("${icbc.api.gw.pubkey}")
        private String apigwPubKey; //网关公钥

        /**
         * DefaultIcbcClient，并注册到Spring容器
         *
         * @return DefaultIcbcClient
         */
        @Bean
        public DefaultIcbcClient defaultIcbcClient() {
            //初始化DefaultIcbcClient
            return new DefaultIcbcClient(appId, privateKey, apigwPubKey, ca, password);
        }

        /**
         * 初始化UiIcbcClient，并注册到Spring容器
         *
         * @return UiIcbcClient
         */
        @Bean
        public UiIcbcClient uiIcbcClient() {
            //初始化UiIcbcClient
            String charset = ApiConstants.CHAR_SET;
            return new UiIcbcClient(appId, privateKey, charset, ca, password);
        }

    }

}
