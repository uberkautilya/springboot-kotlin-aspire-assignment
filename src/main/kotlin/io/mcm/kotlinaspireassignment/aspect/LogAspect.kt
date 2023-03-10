package io.mcm.kotlinaspireassignment.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAspect {
    @Pointcut("execution(* io.mcm.kotlinaspireassignment.controller.*.*(..))")
    fun aroundController() {
    }

    @Around("aroundController()")
    fun logAroundController(proceedPoint: ProceedingJoinPoint): Any? {
        val controllerClass = proceedPoint.signature.declaringType
        val controllerName = proceedPoint.signature.declaringType.simpleName
        val methodName = proceedPoint.signature.name
        val methodArgs = proceedPoint.args
        val logger = LoggerFactory.getLogger(controllerClass)
        logger.info("$controllerName.$methodName invoked with arguments: \n${methodArgs.contentToString()}")

        val result = proceedPoint.proceed()
        logger.info("$controllerName.$methodName result: $result")
        return result
    }
}