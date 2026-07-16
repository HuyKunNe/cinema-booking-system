public interface KafkaTopicResolver {

    String resolve(String eventType);

}