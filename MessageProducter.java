package com.lan.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2017/6/21.
 */
public class MessageProducter {

    public static void main(String[] args) throws JMSException {

        //连接工厂，JMS用它创建连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://192.168.1.201:61616");
        //JMS客户端到JMS Provider 的连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //Session: 一个发送或接收消息的线程
        Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
        //Destination :消息目的地，消息发送给谁
        //获取session注意参数值my-queue是Queue的名字
        Destination destination = session.createQueue("my-queue");
        //MessageProducer:消息生产者
        MessageProducer producer = session.createProducer(destination);
        //设置不持久化
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //发送一条消息
        sendMsg(session,producer);
        session.commit();
        connection.close();

    }

    private static void sendMsg(Session session, MessageProducer producer) throws JMSException {
        //创建一条文本消息
        TextMessage message = session.createTextMessage("Hello ActiveMQ!");
        //通过消息生产者发出消息
        producer.send(message);
    }
}
