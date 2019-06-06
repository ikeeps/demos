package library;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bugsnag.Bugsnag;

public class BugsnagNotStop {
    private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        log.info("application start");
        Bugsnag bugsnag = new Bugsnag("");
        bugsnag.notify(new Exception("test"));
        log.info("application end");

        try {
            Field field = Bugsnag.class.getDeclaredField("sessionExecutorService");
            field.setAccessible(true);
            ScheduledThreadPoolExecutor object = (ScheduledThreadPoolExecutor) field.get(bugsnag);
            object.shutdown();
            if (!object.awaitTermination(2, TimeUnit.DAYS)) {
                object.shutdownNow();
            }
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
            log.error("access error", e1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("interrupted");
        }
    }
}
