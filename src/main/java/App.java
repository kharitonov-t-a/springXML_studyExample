import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {
    public Client client;
    public EventLogger eventLogger;
    public static ConfigurableApplicationContext ctx;
    public Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.eventLogger = eventLogger;
        this.loggers = loggers;
    }

    public void logEvent(EventType type, String msg) {
        EventLogger logger = loggers.get(type);
        //default eventLogger
        if (logger == null){
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

        ctx = new ClassPathXmlApplicationContext("Spring.xml");
        ctx.registerShutdownHook();

        App app = ctx.getBean("app", App.class);

        app.logEvent(null, "Some event for user 1");
//        app.logEvent(EventType.ERROR, "Some event for user 2");
//        app.logEvent(EventType.INFO, "Some event for user 3");
//        ctx.close();
    }
}
