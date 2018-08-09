package aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableAspectJAutoProxy
@Aspect
public class StatisticsAspect {

    private Map<Class<?>, Integer> counter = new HashMap<Class<?>, Integer>();

    @Pointcut("execution(* *.logEvent(..))")
    public void allLogEventMethods() {
    }

    @AfterReturning("allLogEventMethods()")
    public void count(JoinPoint jp) {
        Class<?> clazz = jp.getTarget().getClass();
        if (!counter.containsKey(clazz)) {
            counter.put(clazz, 0);
        }
        counter.put(clazz, counter.get(clazz) + 1);
    }

    public Map<Class<?>, Integer> getCounter() {
        return Collections.unmodifiableMap(counter);
    }

    @AfterReturning("execution(* App.logEvents(..))")
    public void outputLoggingCounter() {
        System.out.println("Loggers statistics. Number of calls: ");
        for (Map.Entry<Class<?>, Integer> entry : counter.entrySet()) {
            System.out.println("    " + entry.getKey().getSimpleName() + ": " + entry.getValue());
        }
    }
}
