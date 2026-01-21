package com.tms.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
public class TimerAspect {

    //1. Виды Pointcut
    //@Pointcut("execution(public  *op())")
    //@Pointcut("@com.tms.annotation(com.poviraev.com.tms.annotation.TimerAop)")
    //@Pointcut("within(com.poviraev.Car)")
    //public void timerPointcut() {}

    //2. Advice
    @Before("execution(public Boolean *())")
    public void timerBefore(JoinPoint joinPoint) {
        System.out.println(LocalDateTime.now() + " Before: " + joinPoint.getSignature());
    }

    @After("execution(public Boolean *())")
    public void timerAfter(JoinPoint joinPoint) {
        System.out.println(LocalDateTime.now() + " After: " + joinPoint.getSignature());
    }

    @AfterThrowing(value = "within(com.poviraev.Car)", throwing = "err")
    public void timerAfterThrowing(JoinPoint joinPoint, Throwable err) {
        System.out.println(LocalDateTime.now() + " Error: " + joinPoint.getSignature() + " Err: " + err);
    }

    @AfterReturning(value = "within(com.poviraev.Car)", returning = "returnValue")
    public void timerAfterReturning(JoinPoint joinPoint, Object returnValue) {
        System.out.println(LocalDateTime.now() + " AfterReturning: " + joinPoint.getSignature() + " ReturnValue: " + returnValue);
    }

    @Around("@annotation(com.poviraev.annotation.TimerAop)")
    public void timerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(LocalDateTime.now() + " Around IN: " + joinPoint.getSignature());
        long startTime = System.currentTimeMillis();
        joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        System.out.println(LocalDateTime.now() + " Around: " + joinPoint.getSignature() + " ExecutionTime: " + executionTime);
        System.out.println(LocalDateTime.now() + " Around OUT: " + joinPoint.getSignature());
    }
}
