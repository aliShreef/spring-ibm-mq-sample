package com.application.sample.service;


import com.application.sample.model.dto.EmployeeData;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
@AllArgsConstructor
public class MqService {

    private final JmsTemplate jmsTemplate;


    // Send and receive message at the same time (sync)
    public void sendMessage(String message,String queueName) throws JMSException {
        jmsTemplate.send(queueName,session -> session.createTextMessage(message) );
        System.out.println("Message sent: " + message);

        Message receive = jmsTemplate.receive(queueName);
        System.out.println("Message Received: "+ receive.getBody(String.class));
    }

    public void sendEmployeeData(EmployeeData employeeData, String queueName) throws JMSException, JAXBException {

        String xmlMessage = this.convertObjectToXml(employeeData);
        jmsTemplate.send(queueName,session -> session.createTextMessage(xmlMessage) );
        System.out.println("Message sent: " + xmlMessage);

        Message receive = jmsTemplate.receive(queueName);
        System.out.println("Message Received: "+ receive.getBody(String.class));
    }

    // receive message (Async)
//    @JmsListener(destination = "DEV.QUEUE.3")
//    public void receiveMessage(String message) throws JMSException {
//        System.out.println("Message received: " + message);
//    }

    /**
     * Converts an object to an XML string using JAXB.
     *
     * @param object the object to convert
     * @return the XML representation of the object
     * @throws JAXBException if the conversion fails
     */
    private String convertObjectToXml(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);

        return writer.toString();
    }

}
