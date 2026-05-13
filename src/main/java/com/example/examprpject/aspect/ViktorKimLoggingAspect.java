package com.example.examprpject.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class ViktorKimLoggingAspect {

   @Around("execution(* com.example.examprpject.controller.*.*(..))")
   public Object logControllerRequest(ProceedingJoinPoint joinPoint) throws Throwable {
      ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

      if (attributes != null) {
         HttpServletRequest request = attributes.getRequest();
         log.info(">>> REQUEST: {} {} | Method: {}.{}",
               request.getMethod(),
               request.getRequestURI(),
               joinPoint.getSignature().getDeclaringTypeName(),
               joinPoint.getSignature().getName()
         );
      }

      long startTime = System.currentTimeMillis();
      Object result;

      try {
         result = joinPoint.proceed();
      } catch (Throwable ex) {
         log.error("<<< ERROR in {}.{}: {}",
               joinPoint.getSignature().getDeclaringTypeName(),
               joinPoint.getSignature().getName(),
               ex.getMessage()
         );
         throw ex;
      }

      long duration = System.currentTimeMillis() - startTime;
      log.info("<<< RESPONSE: {}.{} completed in {} ms",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            duration
      );

      return result;
   }

   @Before("execution(* com.example.examprpject.service.*.*(..))")
   public void logServiceMethodStart(JoinPoint joinPoint) {
      log.debug("SERVICE CALL: {}.{}()",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName()
      );
   }

   @AfterThrowing(
         pointcut = "execution(* com.example.examprpject.service.*.*(..))",
         throwing = "ex"
   )
   public void logServiceException(JoinPoint joinPoint, Throwable ex) {
      log.error("EXCEPTION in {}.{}: {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            ex.getMessage()
      );
   }

   @Before("execution(* com.example.examprpject.security.*.*(..))")
   public void logSecurityAction(JoinPoint joinPoint) {
      log.info("SECURITY ACTION: {}.{}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName()
      );
   }
}