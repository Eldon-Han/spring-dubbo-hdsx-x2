package com.hdsx.dubbo.product.config;

import com.hdsx.dubbo.common.framework.util.ServiceExceptionUtil;
import com.hdsx.dubbo.product.api.constant.ProductErrorCodeEnum;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class ServiceExceptionConfiguration {

    @EventListener(ApplicationReadyEvent.class) // 可参考 https://www.cnblogs.com/ssslinppp/p/7607509.html
    public void initMessages() {
//        从 service_exception_message.properties 加载错误码的方案
//        Properties properties;
//        try {
//            properties = PropertiesLoaderUtils.loadAllProperties("classpath:service_exception_message.properties");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        for (ProductErrorCodeEnum item : ProductErrorCodeEnum.values()) {
            ServiceExceptionUtil.put(item.getCode(), item.getMessage());
        }
    }

}