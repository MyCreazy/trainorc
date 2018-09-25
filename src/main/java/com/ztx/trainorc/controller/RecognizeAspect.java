package com.ztx.trainorc.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created with tjh.
 * Date: 2018/9/25
 * Time: 上午10:56
 **/
@Aspect
@Component
public class RecognizeAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.ztx.trainorc.controller..*.*(..))")
    public void executePackage(){
    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("executePackage()")
    public  void beforeAdvice(JoinPoint joinPoint)
    {
        String printStr="";
        Signature signature=joinPoint.getSignature();
        printStr+=signature.getName();
        Object[] obj=joinPoint.getArgs();
        System.out.println("前置信息:"+printStr+"----"+ Arrays.asList(obj));
    }

    /**
     * 后置通知
     */
    @After("executePackage()")
    public void afterAdvice()
    {
        String logstr="";

        LOGGER.info("");
    }

    /**
     * 后置返回通知
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(value="execution(* com.ztx.trainorc.controller..*.*(..))",returning = "keys")
    public void afterReturnAdvice(JoinPoint joinPoint,String keys)
    {
        String logstr="";
        System.out.println("返回值11111:"+keys);
        LOGGER.info(keys);
    }
}
