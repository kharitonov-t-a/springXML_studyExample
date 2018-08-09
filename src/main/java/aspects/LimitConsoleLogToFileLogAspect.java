package aspects;

import beans.Event;
import loggers.EventLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

//@Aspect
public class LimitConsoleLogToFileLogAspect {

    private final Integer maxCount;

    private final EventLogger eventLogger;

    private Integer count = 0;

    LimitConsoleLogToFileLogAspect(Integer maxCount, EventLogger eventLogger) {
        this.maxCount = maxCount;
        this.eventLogger = eventLogger;
    }

//    @Pointcut("execution(* loggers.ConsoleEventLogger.logEvent(..))")
//    @Pointcut("execution(* *.logEvent(beans.Event)) &amp;&amp; within(loggers.ConsoleEventLogger) &amp;&amp; args(evnt)")
    public void consoleLoggerEvent() {
    }

//    @Around("consoleLoggerEvent() && args(evnt)")
    public void aroundLogEvent(ProceedingJoinPoint jp, Event evnt) throws Throwable {

        if (count < maxCount) {
            System.out.println("OLD EVENT!!!");
            count++;
//          продолжает выполняться метод бина
            jp.proceed(new Object[] {evnt});
        } else {
            System.out.println("NEW EVENT!!!");
            eventLogger.logEvent((Event) evnt);
        }

    }

}
