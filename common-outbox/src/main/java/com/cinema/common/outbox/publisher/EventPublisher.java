
public interface EventPublisher {

    void publish(
            AggregateType aggregateType,
            Long aggregateId,
            Object event
    );

}