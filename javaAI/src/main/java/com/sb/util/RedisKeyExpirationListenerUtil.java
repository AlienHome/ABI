package com.sb.util;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component 
public class RedisKeyExpirationListenerUtil extends KeyExpirationEventMessageListener{
    static Logger logger = LoggerFactory.getLogger(RedisKeyExpirationListenerUtil.class);
    public RedisKeyExpirationListenerUtil(RedisMessageListenerContainer listenerContainer){
        super(listenerContainer);
    }

    @Override 
    public void onMessage(Message message, byte[] pattern){
        String expiredKey = message.toString();
        logger.info("data expired:{}", expiredKey);
        // if(expiredKey.startsWith("test:")){
        //     logger.info("test data expired");
        // }
    }
}