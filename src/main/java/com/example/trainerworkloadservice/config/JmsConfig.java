package com.example.trainerworkloadservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    /** ActiveMQConnectionFactory. */
    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory =
            new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
        return factory;
    }

    /** MessageConverter. */
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        ObjectMapper mapper = ObjectMapperConfig.objectMapper();
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter(mapper);

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        //TODO
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("org.example.dto.requestdto.UpdateTrainerWorkloadRequestDto",
            com.example.trainerworkloadservice.dto.requestdto.UpdateTrainerWorkloadRequestDto.class);

        converter.setTypeIdMappings(typeIdMappings);

        return converter;
    }


    /** DefaultJmsListenerContainerFactory. */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jacksonJmsMessageConverter());
        return factory;
    }
}
