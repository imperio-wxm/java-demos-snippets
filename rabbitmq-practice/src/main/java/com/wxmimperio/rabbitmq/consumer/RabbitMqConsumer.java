package com.wxmimperio.rabbitmq.consumer;

import com.rabbitmq.client.*;
import com.wxmimperio.rabbitmq.connection.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqConsumer {

    private static String baseExchange = "wxm_default_exchange";

    public static void main(String[] args) {
        RabbitMqConnection rabbitMqConnection = new RabbitMqConnection("consumer");

        try {
            Connection connection = rabbitMqConnection.getConnection();
            try (Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(baseExchange, BuiltinExchangeType.DIRECT, false);
                String queueName = "wxm_queue";
                readMsg(channel, queueName);
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            rabbitMqConnection.closeAllConnection();
        }
    }

    private static void readMsg(Channel channel, String queueName) throws IOException {
        while (true) {
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String msg = new String(body, "UTF-8");
                    System.out.println(String.format("Receive %s", msg));
                }
            };
            channel.basicConsume(queueName, true, consumer);
        }
    }
}
