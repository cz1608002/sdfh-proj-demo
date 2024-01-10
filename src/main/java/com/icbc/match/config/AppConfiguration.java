package com.icbc.match.config;


/*import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;*/

//@Configuration
public class AppConfiguration {

    /**
     * 全局DEBUG开关，可通过Apollo配置中心调整
     */
    public static boolean DEBUG = false;

    /**
     * 初始化配置，从apollo加载配置参数，并设置监听
     */
 /*   @PostConstruct
    public void init() {
        Config config = ConfigService.getAppConfig();
        DEBUG = config.getBooleanProperty("debug", false);

        config.addChangeListener(event -> {
            onConfigChanged(event);
        });
    }

    private static void onConfigChanged(ConfigChangeEvent event) {
        Set<String> changedKeys = event.changedKeys();
        for (String key : changedKeys) {
            if ("debug".equals(key)) {
                String newValue = event.getChange("debug").getNewValue();
                DEBUG = "true".equals(newValue) ? true : false;
            }
        }
    }*/
}
