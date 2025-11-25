package com.allied.framework.core;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Environment variable loader and manager.
 *
 * <p>This class loads environment variables from a .env file (if present) and provides
 * convenient access to required application configuration values.</p>
 *
 * <p><b>Required Environment Variables:</b></p>
 * <ul>
 *   <li>ORANGEHRM_BASE_URL - Base URL for OrangeHRM application</li>
 *   <li>ORANGEHRM_USERNAME - Username for OrangeHRM login</li>
 *   <li>ORANGEHRM_PASSWORD - Password for OrangeHRM login</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * // Access environment variables
 * String baseUrl = Env.ORANGEHRM_BASE_URL;
 * String username = Env.ORANGEHRM_USERNAME;
 * String password = Env.ORANGEHRM_PASSWORD;
 *
 * // Use in tests
 * driver.get(Env.ORANGEHRM_BASE_URL);
 * loginPage.login(Env.ORANGEHRM_USERNAME, Env.ORANGEHRM_PASSWORD);
 * }</pre>
 *
 * <p><b>Setup:</b></p>
 * <p>Create a .env file in the project root with the following content:</p>
 * <pre>
 * ORANGEHRM_BASE_URL=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
 * ORANGEHRM_USERNAME=Admin
 * ORANGEHRM_PASSWORD=admin123
 * </pre>
 *
 * <p>This is the Java equivalent of Python's env.py module.</p>
 *
 * @see io.github.cdimascio.dotenv.Dotenv
 */
public class Env {
    private static final Dotenv dotenv;

    // Static initializer - loads .env file once when class is first loaded
    static {
        // Try to load .env file, but don't fail if it doesn't exist
        // (environment variables can also be set via system properties or shell)
        dotenv = Dotenv.configure()
                .ignoreIfMissing()  // Don't fail if .env file is missing
                .load();
    }

    /**
     * Base URL for the OrangeHRM application.
     * <p>Example: https://opensource-demo.orangehrmlive.com/web/index.php/auth/login</p>
     */
    public static final String ORANGEHRM_BASE_URL = getRequiredEnv("ORANGEHRM_BASE_URL");

    /**
     * Username for OrangeHRM login.
     * <p>Example: Admin</p>
     */
    public static final String ORANGEHRM_USERNAME = getRequiredEnv("ORANGEHRM_USERNAME");

    /**
     * Password for OrangeHRM login.
     * <p>Example: admin123</p>
     */
    public static final String ORANGEHRM_PASSWORD = getRequiredEnv("ORANGEHRM_PASSWORD");


    /**
     * FIRST_NAME for PIM.
     * <p>Example: SHSHS</p>
     */


    // String firstName = Env.getOptionalEnv(FIRST_NAME)
 

    // public static final String FIRST_NAME = getRequiredEnv("FIRST_NAME");

    /**
     * Gets a required environment variable.
     *
     * <p>This method first checks the .env file, then falls back to system environment variables.
     * If the variable is not found in either location, it throws a RuntimeException.</p>
     *
     * <p><b>Usage Example:</b></p>
     * <pre>{@code
     * String apiKey = Env.getRequiredEnv("API_KEY");
     * }</pre>
     *
     * @param name The name of the environment variable
     * @return The value of the environment variable
     * @throws RuntimeException if the environment variable is not set or is empty
     */
    public static String getRequiredEnv(String name) {
        String value = dotenv.get(name);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(
                String.format(
                    "Missing required environment variable '%s'. " +
                    "Set it in your .env file or system environment.",
                    name
                )
            );
        }
        return value;
    }

    /**
     * Gets an optional environment variable with a default value.
     *
     * <p>This method first checks the .env file, then falls back to system environment variables.
     * If the variable is not found, it returns the provided default value.</p>
     *
     * <p><b>Usage Example:</b></p>
     * <pre>{@code
     * String logLevel = Env.getOptionalEnv("LOG_LEVEL", "INFO");
     * boolean debugMode = Boolean.parseBoolean(Env.getOptionalEnv("DEBUG_MODE", "false"));
     * }</pre>
     *
     * @param name The name of the environment variable
     * @param defaultValue The default value to return if the variable is not set
     * @return The value of the environment variable, or defaultValue if not found
     */
    public static String getOptionalEnv(String name, String defaultValue) {
        String value = dotenv.get(name);
        return (value != null && !value.trim().isEmpty()) ? value : defaultValue;
    }

    // Private constructor to prevent instantiation
    private Env() {
        throw new AssertionError("Env class should not be instantiated");
    }
}
