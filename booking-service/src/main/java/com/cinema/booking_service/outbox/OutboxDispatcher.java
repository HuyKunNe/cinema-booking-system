package com.cinema.booking_service.outbox;

import com.cinema.booking_service.entity.OutboxEvent;
import com.cinema.booking_service.enums.OutboxEventType;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class OutboxDispatcher {

    private final Map<OutboxEventType, OutboxEventHandler> handlers;

    public OutboxDispatcher(List<OutboxEventHandler> handlerList) {

        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        OutboxEventHandler::getType,
                        Function.identity()));

    }

    public void dispatch(OutboxEvent event) throws Exception {

        OutboxEventHandler handler = handlers.get(event.getEventType());

        if (handler == null) {

            throw new IllegalArgumentException(
                    "No handler found for " + event.getEventType());

        }

        handler.handle(event);

    }

}