/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class Logger {

	public static void error(Object o) {
		now();
		System.out.print("[ERROR] | ");
		print(o);
		end();
	}

	public static void info(Object o) {
		now();
		System.out.print("[INFO ] | ");
		print(o);
		end();
	}

	public static void warn(Object o) {
		now();
		System.out.print("[WARN ] | ");
		print(o);
		end();
	}

	public static void debug(Object o) {
		now();
		System.out.print("[DEBUG] | ");
		print(o);
		end();
	}

	/**
	 * 
	 */
	private static void end() {
		System.out.print(System.lineSeparator());

	}

	private static void print(Object o) {
		System.out.print(o.toString() + " ");
	}

	private static void now() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS");
		String uhrzeit = sdf.format(new Date());
		System.out.print(uhrzeit + " | ");
	}

}
