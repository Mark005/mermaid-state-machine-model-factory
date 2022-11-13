package com.bmo.mermaidstatemachine.exception;

public class MermaidStatemachineConfigurationException extends RuntimeException {

  public MermaidStatemachineConfigurationException() {
    super();
  }

  public MermaidStatemachineConfigurationException(String message) {
    super(message);
  }

  public MermaidStatemachineConfigurationException(String message, Object ... objects) {
    super(prepare(message, objects));
  }

  public MermaidStatemachineConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }


  public MermaidStatemachineConfigurationException(Throwable cause) {
    super(cause);
  }

  protected MermaidStatemachineConfigurationException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  private static String prepare(String string, Object ... objects) {
    if (string == null) {
      throw new IllegalStateException("String is null");
    }

    String replace = string.replace("{}", "%s");
    return String.format(replace, objects);
  }
}
