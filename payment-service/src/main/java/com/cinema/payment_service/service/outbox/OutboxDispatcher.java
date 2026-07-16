package com.cinema.payment_service.service.outbox;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cinema.payment_service.entity.OutboxEvent;
import com.cinema.payment_service.enums.OutboxEventType;

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