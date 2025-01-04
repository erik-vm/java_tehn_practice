package aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    public void logBefore(JoinPoint joinPoint) {
        logger.info("method name: " + joinPoint.getSignature().getName());
        logger.debug("method arguments : " + Arrays.toString(joinPoint.getArgs()));
    }
}