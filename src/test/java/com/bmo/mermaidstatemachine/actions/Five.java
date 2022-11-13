package com.bmo.mermaidstatemachine.actions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component("fivee")
public class Five implements Action<String, String> {

  @Override
  public void execute(StateContext<String, String> context) {
    log.error("Five");
  }
}
