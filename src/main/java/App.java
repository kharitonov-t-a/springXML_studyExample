import aspects.StatisticsAspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {
    public Client client;
    public EventLogger eventLogger;
    public static ConfigurableApplicationContext ctx;
    public Map<EventType, EventLogger> loggers;

    public App() {
    }

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        super();
        this.client = client;
        this.eventLogger = eventLogger;
        this.loggers = loggers;
    }

    public void logEvent(EventType type, String msg) {
        EventLogger logger = loggers.get(type);
        //default eventLogger
        if (logger == null) {
            logger = eventLogger;
        }

        String messege = msg.replaceAll(client.getId(), client.getFullName());
        Event event = ctx.getBean("event", Event.class);
        event.setMsg(client.getGreeting() + " " + messege);
        logger.logEvent(event);
    }

    public static void main(String[] args) {
//        App app = new App();

//        app.client = new Client("1", "John Smith");
//        app.eventLogger = new ConsoleEventLogger();

//        app.logEvent("Some event for user 1");

        ctx = new ClassPathXmlApplicationContext("spring.xml", "aspects.xml");
        ctx.registerShutdownHook();

        logEvents(ctx);
//        app.logEvent(null, "Some event for user 1");

        StatisticsAspect statisticsAspect = ctx.getBean("statisticsAspect", StatisticsAspect.class);

        for (Map.Entry entry : statisticsAspect.getCounter().entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

//        app.logEvent(EventType.ERROR, "Some event for user 2");
//        app.logEvent(EventType.INFO, "Some event for user 3");
//        ctx.close();
    }


    public static void logEvents(ApplicationContext ctx) {
        App app = ctx.getBean("app", App.class);

        Event event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO, "Some event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO, "One more event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO, "And one more event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(EventType.ERROR, "Some event for 2");

        event = ctx.getBean(Event.class);
        app.logEvent(null, "Some event for 3");
    }
}
