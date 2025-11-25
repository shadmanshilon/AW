package com.allied.framework.core;

import org.slf4j.LoggerFactory;

/**
 * Logger utility for the test framework.
 *
 * <p>This class provides a simple wrapper around SLF4J logging. It uses Logback
 * as the implementation, which is configured via logback.xml in src/main/resources.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * // In your test or page object class
 * import org.slf4j.Logger;
 * import com.allied.framework.core.Logger;
 *
 * public class LoginPage {
 *     private static final Logger logger = Logger.getLogger(LoginPage.class);
 *
 *     public void login(String username, String password) {
 *         logger.info("Attempting to login with username: {}", username);
 *         // ... login logic ...
 *         logger.info("Login successful");
 *     }
 * }
 * }</pre>
 *
 * <p><b>Log Levels:</b></p>
 * <ul>
 *   <li><b>TRACE</b> - Very detailed information, typically only useful for debugging</li>
 *   <li><b>DEBUG</b> - Detailed information for debugging purposes</li>
 *   <li><b>INFO</b> - Informational messages about normal application flow</li>
 *   <li><b>WARN</b> - Warning messages about potential issues</li>
 *   <li><b>ERROR</b> - Error messages about failures</li>
 * </ul>
 *
 * <p><b>Configuration:</b></p>
 * <p>Logging is configured via logback.xml. The default configuration writes logs to:</p>
 * <ul>
 *   <li>Console (STDOUT) - with colored output</li>
 *   <li>File - logs/testng.log</li>
 * </ul>
 *
 * <p>This is the Java equivalent of Python's logger.py module.</p>
 *
 * @see org.slf4j.Logger
 * @see ch.qos.logback.classic.Logger
 */
public class Logger {

    /**
     * Gets a logger for the specified class.
     *
     * <p>This is the recommended way to get a logger. The logger name will be
     * set to the fully qualified class name.</p>
     *
     * <p><b>Usage Example:</b></p>
     * <pre>{@code
     * private static final org.slf4j.Logger logger = Logger.getLogger(MyClass.class);
     * }</pre>
     *
     * @param clazz The class for which to get the logger
     * @return A logger instance for the specified class
     */
    public static org.slf4j.Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Gets a logger with the specified name.
     *
     * <p>Use this method if you need a custom logger name instead of a class name.</p>
     *
     * <p><b>Usage Example:</b></p>
     * <pre>{@code
     * private static final org.slf4j.Logger logger = Logger.getLogger("my.custom.logger");
     * }</pre>
     *
     * @param name The name for the logger
     * @return A logger instance with the specified name
     */
    public static org.slf4j.Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    // Private constructor to prevent instantiation
    private Logger() {
        throw new AssertionError("Logger class should not be instantiated");
    }
}
