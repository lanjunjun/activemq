package com.lan.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Administrator on 2017/6/21.
 */
public class JmsReceiver {

    public static void main(String[] args) throws JMSException {
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,"tcp://192.168.1.201:61616");
        //JMS客户端到JMS Provider的连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //Session:一个发送或接收消息的线程
        Session session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
        //Destination :消息目的地；消息发送给谁
        //获取session注意参数my-queue是一个服务器的queue,需在activeMq的console配置
        Destination destination = session.createQueue("my-queue");
        //消费者，消息接收者
        MessageConsumer consumer = session.createConsumer(destination);
        while (true){
            TextMessage message = (TextMessage) consumer.receive(1000);
            if(null != message){
                System.out.println("收到消息："+message.getText());
            }else
                break;
        }
        session.close();
        connection.close();
    }
}
