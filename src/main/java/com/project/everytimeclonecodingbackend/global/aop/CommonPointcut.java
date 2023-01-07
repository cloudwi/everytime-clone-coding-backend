package com.project.everytimeclonecodingbackend.global.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {
    @Pointcut("execution(public * com.project.everytimeclonecodingbackend..*.*(..))")
    public void packagePublicMethodPointcut() {}
}
