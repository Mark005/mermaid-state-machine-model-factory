package com.bmo.mermaidstatemachine.state;

import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig.StateConfigurationConfig;
import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig.StateMachineConfig;
import com.bmo.mermaidstatemachine.config.MermaidStateMachinesConfig.ValidationLevel;
import com.bmo.mermaidstatemachine.exception.MermaidStatemachineConfigurationException;
import com.bmo.parsers.marmaiddiagram.model.DiagramElement;
import com.bmo.parsers.marmaiddiagram.model.MermaidDiagram;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.model.StateData;
import org.springframework.statemachine.config.model.StatesData;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MermaidStateConfigurerImpl implements MermaidStateConfigurer {

  private final Map<String, Action<String, String>> actionMap;

  @Override
  public void validate(MermaidDiagram mermaidDiagram, StateMachineConfig mermaidConfig) {
    validateInitialStates(mermaidDiagram);
  }

  @Override
  public StatesData<String, String> configure(
      MermaidDiagram mermaidDiagram,
      StateMachineConfig mermaidConfig) {
    List<DiagramElement> notGraphStates = mermaidDiagram.getNotGraphStates();
    verify(notGraphStates, mermaidConfig);

    List<StateData<String, String>> collect = notGraphStates
        .stream()
        .map(diagramElement -> {
              StateData<String, String> stateData =
                  new StateData<>(diagramElement.getName());
              stateData.setStateActions(getAction(diagramElement.getName()));
              stateData.setInitial(diagramElement.isInitial());
              stateData.setEnd(diagramElement.isEnd());
              return stateData;
            })
        .collect(Collectors.toList());

    return new StatesData<>(collect);
  }

  private void verify(List<DiagramElement> diagramElements, StateMachineConfig mermaidConfig) {
    Set<String> beanNames = actionMap.keySet();
    Set<String> stateNames = diagramElements.stream().map(DiagramElement::getName).collect(Collectors.toSet());

    Set<String> notUsedBeans = new HashSet<>(beanNames);
    Set<String> notUsedStates = new HashSet<>(stateNames);
    notUsedBeans.removeAll(stateNames);
    notUsedStates.removeAll(beanNames);

    StateConfigurationConfig config = mermaidConfig.getStateConfigurationConfig();
    if (!notUsedBeans.isEmpty()) {
      String text = String.format("Beans with names %s weren't used, notUsedBeans", notUsedBeans);
      logOrThrowException(text, config.getValidateUnusedActionBeans());
    }

    if (!notUsedStates.isEmpty()) {
      String text = String.format("States with names %s weren't used", notUsedStates);
      logOrThrowException(text, config.getValidateStatesWithoutAction());
    }
  }

  private Collection<Function<StateContext<String, String>, Mono<Void>>> getAction(String beanName) {
    return Optional.ofNullable(actionMap.get(beanName))
        .map(action -> (Function<StateContext<String, String>, Mono<Void>>)
            stringStringStateContext ->
                Mono.fromRunnable(() -> action.execute(stringStringStateContext)))
        .map(Collections::singletonList)
        .orElse(null);
  }

  private void validateInitialStates(MermaidDiagram mermaidDiagram) {
    long countOfInitialStates = mermaidDiagram.getNotGraphStates()
        .stream()
        .filter(diagramElement -> diagramElement.getParent() == null)
        .filter(DiagramElement::isInitial)
        .count();
    if (countOfInitialStates != 1) {
      throw new MermaidStatemachineConfigurationException(
          "Diagram has {} initial states", countOfInitialStates);
    }
  }

  private void logOrThrowException(String text, ValidationLevel validationLevel) {
    switch (validationLevel) {
      case INFO:
        log.info(text);
        break;
      case WARNING:
        log.warn(text);
        break;
      case ERROR:
        log.error(text);
        break;
      case EXCEPTION:
        log.error(text);
        throw new MermaidStatemachineConfigurationException(text);
    }
  }
}
