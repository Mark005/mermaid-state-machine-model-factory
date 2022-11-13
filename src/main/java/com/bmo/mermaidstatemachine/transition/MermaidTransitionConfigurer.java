package com.bmo.mermaidstatemachine.transition;


import com.bmo.parsers.model.MermaidDiagram;
import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig.StateMachineConfig;
import org.springframework.statemachine.config.model.TransitionsData;

public interface MermaidTransitionConfigurer {

  TransitionsData<String, String> configure(MermaidDiagram mermaidDiagram,
      StateMachineConfig mermaidConfig);
}
