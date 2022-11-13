package com.bmo.mermaidstatemachine.actions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class One implements Action<String, String> {

  @Override
  public void execute(StateContext<String, String> context) {
    context.getExtendedState().getVariables().put("var1", "dfdfdf");
    log.error("One id:{}", context.getStateMachine().getId());

    context.getStateMachine().sendEvent(
            Mono.just(MessageBuilder
                .withPayload("event2")
                .setHeader("var2", "212121211")
                .build()))
        .subscribe();
  }
}
