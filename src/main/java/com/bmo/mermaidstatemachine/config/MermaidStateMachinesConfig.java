package com.bmo.mermaidstatemachine.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@Component
@ConfigurationProperties(prefix = "mermaid-statemachine")
public class MermaidStateMachinesConfig {

  Map<String, StateMachineConfig> settings = new HashMap<>();

  @Data
  public static class StateMachineConfig {
    private StateConfigurationConfig stateConfigurationConfig = new StateConfigurationConfig();
    private TransitionConfigurationConfig transitionConfigurationConfig = new TransitionConfigurationConfig();
  }

  @Data
  public static class StateConfigurationConfig {
    private ValidationLevel validateUnusedActionBeans = ValidationLevel.EXCEPTION;
    private ValidationLevel validateStatesWithoutAction = ValidationLevel.WARNING;
  }

  @Data
  public static class TransitionConfigurationConfig {

  }

  public enum ValidationLevel {
    NONE,
    INFO,
    WARNING,
    ERROR,
    EXCEPTION
  }
}

