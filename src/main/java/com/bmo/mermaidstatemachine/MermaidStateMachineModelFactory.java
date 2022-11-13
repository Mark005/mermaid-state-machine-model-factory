package com.bmo.mermaidstatemachine;

import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig;
import com.bmo.parsers.FileDiagramParser;
import com.bmo.parsers.model.MermaidDiagram;
import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig.StateMachineConfig;
import com.bmo.mermaidstatemachine.state.MermaidStateConfigurer;
import com.bmo.mermaidstatemachine.transition.MermaidTransitionConfigurer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.model.ConfigurationData;
import org.springframework.statemachine.config.model.DefaultStateMachineModel;
import org.springframework.statemachine.config.model.StateMachineModel;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.config.model.StatesData;
import org.springframework.statemachine.config.model.TransitionsData;

@RequiredArgsConstructor
public class MermaidStateMachineModelFactory implements StateMachineModelFactory<String, String> {
  private final String diagramPath;
  private final ConfigurationData<String, String> statemachineConfig;

  @Autowired
  private MermaidStateMachinesConfig mermaidConfig;

  @Autowired
  private MermaidStateConfigurer mermaidStateConfigurer;

  @Autowired
  private MermaidTransitionConfigurer mermaidTransitionConfigurer;

  @Override
  public StateMachineModel<String, String> build() {
    FileDiagramParser fileDiagramParser = new FileDiagramParser(diagramPath);
    MermaidDiagram mermaidDiagram = fileDiagramParser.parse();

    StateMachineConfig stateMachineConfig = Optional.of(mermaidDiagram.getName())
        .map(name -> mermaidConfig.getSettings().get(name))
        .orElse(new StateMachineConfig());

    mermaidStateConfigurer.validate(mermaidDiagram, stateMachineConfig);
    StatesData<String, String> statesData =
        mermaidStateConfigurer.configure(mermaidDiagram, stateMachineConfig);

    TransitionsData<String, String> transitionData =
        mermaidTransitionConfigurer.configure(mermaidDiagram, stateMachineConfig);

    return new DefaultStateMachineModel<>(
        statemachineConfig,
        statesData,
        transitionData);
  }

  @Override
  public StateMachineModel<String, String> build(String machineId) {
    return build();
  }
}
