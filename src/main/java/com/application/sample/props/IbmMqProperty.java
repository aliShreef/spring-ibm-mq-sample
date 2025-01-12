package com.application.sample.props;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ibm.mq")
@Getter
@Setter
public class IbmMqProperty {

    private String hostName;
    private int port;
    private String queueManager;
    private int transportType;
    private String channel;
    private String user;
    private String password;
    private String requestQueue;
    private String responseQueue;

}
