package com.bmo.mermaidstatemachine.config;

import com.bmo.mermaidstatemachine.state.MermaidStateConfigurerImpl;
import com.bmo.mermaidstatemachine.transition.MermaidTransitionConfigurerImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
    MermaidStateConfigurerImpl.class,
    MermaidTransitionConfigurerImpl.class
})
@EnableConfigurationProperties(MermaidStateMachinesConfig.class)
public class MermaidStateMachineAutoconfiguration {

}
