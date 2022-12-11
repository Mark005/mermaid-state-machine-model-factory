package com.bmo.mermaidstatemachine.transition;

import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig.StateMachineConfig;
import com.bmo.parsers.marmaiddiagram.model.MermaidDiagram;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.config.model.TransitionData;
import org.springframework.statemachine.config.model.TransitionsData;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MermaidTransitionConfigurerImpl implements MermaidTransitionConfigurer {

  @Override
  public TransitionsData<String, String> configure(MermaidDiagram mermaidDiagram,
      StateMachineConfig mermaidConfig) {
    List<TransitionData<String, String>> transitionData = mermaidDiagram.getTransitions()
        .stream()
        .map(transition -> new TransitionData<>(
            transition.getFrom().getName(),
            transition.getTo().getName(),
            transition.getEvent()))
        .collect(Collectors.toList());

    return new TransitionsData<>(transitionData);
  }
}
