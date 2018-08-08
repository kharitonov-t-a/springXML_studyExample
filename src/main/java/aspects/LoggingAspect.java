package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.logging.Logger;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//
//@Configuration
//@EnableAspectJAutoProxy

@Aspect
public class LoggingAspect {

//    private static Logger LOG = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {
    }

    @Pointcut("allLogEventMethods() && within(*.*File*Logger)")
    public void logEventInsideFileLoggers() {
    }

    //    @Before("execution(* *.logEvent(..))")
    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getTarget().getClass().getSimpleName() +
                " " +
                joinPoint.getSignature().getName()
        );
    }

    @AfterReturning(pointcut = "allLogEventMethods()", returning = "retVal")
    public void logAfter(Object retVal) {
        System.out.println("Returned value: " + retVal);
    }


    @AfterThrowing(pointcut = "allLogEventMethods()", throwing = "ex")
    public void logAfterThrow(Throwable ex) {
        System.out.println("Thrown: " + ex);
    }

}
