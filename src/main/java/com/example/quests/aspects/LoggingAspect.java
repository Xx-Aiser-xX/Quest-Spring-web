package com.example.quests.aspects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Controller;


@Aspect
public class LoggingAspect {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Before("execution(* com.example.quests.controllers.*.*(..)) || execution(* com.example.quests.controllers.admin.*.*(..))")
    public void log(JoinPoint joinPoint) throws Throwable{
        String methodName = joinPoint.getSignature().getName();
        LOG.log(Level.INFO, "method called " + methodName);
    }
}
