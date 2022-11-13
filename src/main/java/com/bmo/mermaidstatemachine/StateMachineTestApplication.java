package com.bmo.mermaidstatemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

@SpringBootApplication
public class StateMachineTestApplication implements CommandLineRunner {

  @Autowired
  private StateMachineFactory<String, String> stateMachineFactory;

  public static void main(String... args) {
    SpringApplication.run(StateMachineTestApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
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
