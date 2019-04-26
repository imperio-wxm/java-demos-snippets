package com.wxmimperio.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wxmimperio.rabbitmq.connection.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqProducer {

    private static String baseExchange = "wxm_default_exchange";

    public static void main(String[] args) {
        RabbitMqConnection rabbitMqConnection = new RabbitMqConnection("producer");
        try {
            Connection connection = rabbitMqConnection.getConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(baseExchange, BuiltinExchangeType.DIRECT, false);
            String routingKey = "test";
            String queueName = "wxm_queue";
            sendMsg(channel, queueName, routingKey);
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            rabbitMqConnection.closeAllConnection();
        }
    }

    private static void sendMsg(Channel channel, String queueName, String routingKey) throws IOException, InterruptedException {
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, baseExchange, routingKey);

        int i = 0;
        while (true) {
            String msg = "wxm rabbit! " + i;
            channel.basicPublish(baseExchange, routingKey, null, msg.getBytes("UTF-8"));
            System.out.println(String.format("Send %s", msg));
            i++;
            Thread.sleep(2000);
        }
    }
}
