package com.bmo.mermaidstatemachine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootTest
class StateMachineTestApplicationTests {

  @Autowired
  private StateMachineFactory<String, String> stateMachineFactory;

  @Test
  void contextLoads() {
    StateMachine<String, String> stateMachine1 = stateMachineFactory.getStateMachine("id1");
    StateMachine<String, String> stateMachine2 = stateMachineFactory.getStateMachine("id2");
    StateMachine<String, String> stateMachine3 = stateMachineFactory.getStateMachine("id2");

    stateMachine1.getExtendedState().getVariables().put("idd", "idd");
    stateMachine1.startReactively().subscribe();
    stateMachine2.startReactively().subscribe();
    stateMachine3.startReactively().subscribe();
    stateMachine1.stopReactively().subscribe();
  }

}
