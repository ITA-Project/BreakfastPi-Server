package com.ita.domain.service.impl;

import com.ita.domain.config.MqttConfig;
import com.ita.domain.service.MessageService;
import com.ita.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttMessageServiceImpl implements MessageService {

    @Autowired
    private MqttConfig mqttConfig;

    @Value("${aes.secret}")
    private String secret;

    @Override
    public void send(String message) {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(mqttConfig.getBroker(), mqttConfig.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();

            connOpts.setCleanSession(false);
            String content = AESUtil.decrypt(mqttConfig.getConnStr(), secret);
            connOpts.setUserName(content.split("/")[0]);
            connOpts.setPassword(content.split("/")[1].toCharArray());
            sampleClient.connect(connOpts);

            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(1);

            sampleClient.publish(mqttConfig.getTopic(), mqttMessage);
            sampleClient.disconnect();
            sampleClient.close();
        } catch (Exception e) {
            log.error("Failed to send mqtt message", e);
            e.printStackTrace();
        }
    }
}
