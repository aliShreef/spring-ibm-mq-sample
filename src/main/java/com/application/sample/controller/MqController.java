package com.application.sample.controller;


import com.application.sample.model.dto.EmployeeData;
import com.application.sample.service.MqService;
import jakarta.jms.JMSException;
import jakarta.xml.bind.JAXBException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MqController {

    private final MqService mqService;


    @GetMapping("/send")
    public String sendMessage(@RequestParam String message, @RequestParam String queueName) throws JMSException {
        mqService.sendMessage(message,queueName);
        return "Message sent: " + message;
    }

    @PostMapping("/send-employee-data")
    public String sendObjectMessage(@RequestBody EmployeeData message, @RequestParam String queueName) throws JMSException, JAXBException {
        mqService.sendEmployeeData(message,queueName);
        return "Message sent: " + message;
    }


}
