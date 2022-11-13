package com.bmo.mermaidstatemachine.state;

import com.bmo.parsers.model.MermaidDiagram;
import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig.StateMachineConfig;
import org.springframework.statemachine.config.model.StatesData;

public interface MermaidStateConfigurer {

  void validate(MermaidDiagram mermaidDiagram, StateMachineConfig mermaidConfig);

  StatesData<String, String> configure(MermaidDiagram mermaidDiagram,
      StateMachineConfig mermaidConfig);
}
