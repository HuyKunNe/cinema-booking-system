package com.cinema.common.outbox.topic;
public interface KafkaTopicResolver {

    String resolve(String eventType);

}