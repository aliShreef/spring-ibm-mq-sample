package com.application.sample.config;

import com.application.sample.props.IbmMqProperty;
import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.jms.JmsConnectionFactory;
import com.ibm.msg.client.jakarta.jms.JmsFactoryFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;


@Configuration
@EnableConfigurationProperties(IbmMqProperty.class)
@AllArgsConstructor
@EnableJms
public class MqConfiguration {

    private final IbmMqProperty ibmMqProperty;

    @Bean
    public ConnectionFactory connectionFactory() throws Exception {
        MQConnectionFactory mqConnectionFactory = new MQConnectionFactory();

        mqConnectionFactory.setHostName(ibmMqProperty.getHostName());
        mqConnectionFactory.setPort(ibmMqProperty.getPort());
        mqConnectionFactory.setQueueManager(ibmMqProperty.getQueueManager());
        mqConnectionFactory.setChannel(ibmMqProperty.getChannel());
        mqConnectionFactory.setTransportType(1);
        mqConnectionFactory.setStringProperty(WMQConstants.USERID, ibmMqProperty.getUser());
        mqConnectionFactory.setStringProperty(WMQConstants.PASSWORD, ibmMqProperty.getPassword());
        return mqConnectionFactory ;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }
}


