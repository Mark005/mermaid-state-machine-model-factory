package com.bmo.mermaidstatemachine.actions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class Three implements Action<String, String> {

  @Override
  public void execute(StateContext<String, String> context) {
    context.getStateMachine().sendEvent(
            Mono.just(MessageBuilder
                .withPayload("event0")
                .setHeader("var3", "33333")
                .build()))
        .subscribe();
    log.error("Three");
  }
}
