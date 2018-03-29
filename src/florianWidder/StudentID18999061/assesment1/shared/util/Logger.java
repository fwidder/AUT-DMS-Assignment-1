/**
 *
 */
package florianWidder.StudentID18999061.assesment1.shared.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class Logger {

    /**
     * Log debug Messages
     *
     * @param o
     */
    public static void debug(final Object o) {
	Logger.now();
	System.out.print("[DEBUG] | ");
	Logger.print(o);
	Logger.end();
    }

    /**
     *
     */
    private static void end() {
	System.out.print(System.lineSeparator());

    }

    /**
     * Log Errors
     *
     * @param o
     */
    public static void error(final Object o) {
	Logger.now();
	System.out.print("[ERROR] | ");
	Logger.print(o);
	Logger.end();
    }

    /**
     * Log Infos
     *
     * @param o
     */
    public static void info(final Object o) {
	Logger.now();
	System.out.print("[INFO ] | ");
	Logger.print(o);
	Logger.end();
    }

    private static void now() {
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS");
	final String uhrzeit = sdf.format(new Date());
	System.out.print(uhrzeit + " | ");
    }

    private static void print(final Object o) {
	System.out.print(o.toString() + " ");
    }

    /**
     * Log Warnings
     *
     * @param o
     */
    public static void warn(final Object o) {
	Logger.now();
	System.out.print("[WARN ] | ");
	Logger.print(o);
	Logger.end();
    }

}
