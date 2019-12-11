/**
 * 
 */
package library;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hujianfeng
 *
 */
public class BugsnagAppenderTest {
    private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
	public static void main(String[] args) {
		log.error("test log4j", new IllegalAccessException("test self made log4j appender"));
	}
}
