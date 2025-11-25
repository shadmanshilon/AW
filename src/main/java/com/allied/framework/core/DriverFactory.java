package com.allied.framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Driver factory for Selenium tests.
 *
 * <p>This factory creates WebDriver instances for Chrome, Firefox, and Edge browsers.
 * It supports both local and remote (Selenium Grid) execution.</p>
 *
 * <p><b>Configuration via Environment Variables:</b></p>
 * <ul>
 *   <li>BROWSER = "chrome" | "firefox" | "edge" (default: "chrome")</li>
 *   <li>HEADLESS = "true" | "false" (default: "false")</li>
 *   <li>SELENIUM_REMOTE_URL = "http://grid-url:4444/wd/hub" (if set → use Remote WebDriver)</li>
 *   <li>WINDOW_WIDTH = integer (default: 1920)</li>
 *   <li>WINDOW_HEIGHT = integer (default: 1080)</li>
 *   <li>PAGE_LOAD_TIMEOUT = integer seconds (default: 60)</li>
 *   <li>IMPLICIT_WAIT = integer seconds (default: 0)</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * // Create driver with environment variable configuration
 * WebDriver driver = DriverFactory.createDriver();
 *
 * // Create driver with custom configuration
 * DriverConfig config = new DriverConfig();
 * config.setBrowser("firefox");
 * config.setHeadless(false);
 * WebDriver driver = DriverFactory.createDriver(config);
 *
 * // Use in TestNG @BeforeMethod
 * @BeforeMethod
 * public void setUp() {
 *     driver = DriverFactory.createDriver();
 * }
 *
 * @AfterMethod
 * public void tearDown() {
 *     if (driver != null) {
 *         driver.quit();
 *     }
 * }
 * }</pre>
 *
 * <p><b>Note:</b> This uses Selenium Manager (built into Selenium 4+) for automatic
 * driver management. No need for external driver manager tools.</p>
 *
 * <p>This is the Java equivalent of Python's driver_factory.py module.</p>
 *
 * @see DriverConfig
 */
public class DriverFactory {
    private static final org.slf4j.Logger logger = Logger.getLogger(DriverFactory.class);

    /**
     * Creates a WebDriver instance using environment variable configuration.
     *
     * <p>This method reads configuration from environment variables (or .env file)
     * and creates the appropriate WebDriver instance.</p>
     *
     * @return A configured WebDriver instance
     */
    public static WebDriver createDriver() {
        DriverConfig config = getDriverConfigFromEnv();
        return createDriver(config);
    }

    /**
     * Creates a WebDriver instance using the provided configuration.
     *
     * <p>This method creates either a local or remote WebDriver based on the config.</p>
     *
     * @param config The driver configuration to use
     * @return A configured WebDriver instance
     */
    public static WebDriver createDriver(DriverConfig config) {
        logger.info("Creating WebDriver with config: {}", config);

        WebDriver driver;
        if (config.getRemoteUrl() != null && !config.getRemoteUrl().trim().isEmpty()) {
            driver = createRemoteDriver(config);
        } else {
            driver = createLocalDriver(config);
        }

        // Set window size
        try {
            driver.manage().window().setSize(
                new org.openqa.selenium.Dimension(config.getWindowWidth(), config.getWindowHeight())
            );
        } catch (Exception e) {
            logger.debug("Could not set window size; continuing anyway.", e);
        }

        // Set timeouts
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        } catch (Exception e) {
            logger.debug("Could not set page load timeout; continuing anyway.", e);
        }

        if (config.getImplicitWait() > 0) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        }

        logger.info("WebDriver created successfully: {}", driver);
        return driver;
    }

    /**
     * Builds a DriverConfig from environment variables.
     *
     * <p>Reads the following environment variables:</p>
     * <ul>
     *   <li>BROWSER (default: "chrome")</li>
     *   <li>HEADLESS (default: "false")</li>
     *   <li>SELENIUM_REMOTE_URL (optional)</li>
     *   <li>WINDOW_WIDTH (default: 1920)</li>
     *   <li>WINDOW_HEIGHT (default: 1080)</li>
     *   <li>PAGE_LOAD_TIMEOUT (default: 60)</li>
     *   <li>IMPLICIT_WAIT (default: 0)</li>
     * </ul>
     *
     * @return A DriverConfig populated from environment variables
     */
    public static DriverConfig getDriverConfigFromEnv() {
        DriverConfig config = new DriverConfig();

        // Browser
        String browser = Env.getOptionalEnv("BROWSER", "chrome").trim().toLowerCase();
        if (!browser.equals("chrome") && !browser.equals("firefox") && !browser.equals("edge")) {
            logger.warn("Unsupported BROWSER='{}'; falling back to 'chrome'.", browser);
            browser = "chrome";
        }
        config.setBrowser(browser);

        // Headless
        config.setHeadless(envBool("HEADLESS", false));

        // Remote URL
        config.setRemoteUrl(Env.getOptionalEnv("SELENIUM_REMOTE_URL", null));

        // Window size
        config.setWindowWidth(envInt("WINDOW_WIDTH", 1920));
        config.setWindowHeight(envInt("WINDOW_HEIGHT", 1080));

        // Timeouts
        config.setPageLoadTimeout(envInt("PAGE_LOAD_TIMEOUT", 60));
        config.setImplicitWait(envInt("IMPLICIT_WAIT", 0));

        logger.info("Using DriverConfig from environment: {}", config);
        return config;
    }

    /**
     * Creates a local WebDriver using Selenium Manager.
     *
     * <p>Selenium Manager (built into Selenium 4+) automatically downloads and manages
     * browser drivers, so no external driver manager is needed.</p>
     *
     * @param config The driver configuration
     * @return A local WebDriver instance
     */
    private static WebDriver createLocalDriver(DriverConfig config) {
        String browser = config.getBrowser();
        logger.info("Creating local WebDriver for browser={}", browser);

        switch (browser) {
            case "chrome":
                return new ChromeDriver(getChromeOptions(config));
            case "firefox":
                return new FirefoxDriver(getFirefoxOptions(config));
            case "edge":
                return new EdgeDriver(getEdgeOptions(config));
            default:
                throw new IllegalArgumentException("Unsupported browser for local driver: " + browser);
        }
    }

    /**
     * Creates a Remote WebDriver for Selenium Grid execution.
     *
     * <p>Uses the same browser and headless settings but connects via the remote URL.</p>
     *
     * @param config The driver configuration
     * @return A remote WebDriver instance
     * @throws IllegalArgumentException if remote URL is not set
     * @throws RuntimeException if the remote URL is malformed
     */
    private static WebDriver createRemoteDriver(DriverConfig config) {
        String remoteUrl = config.getRemoteUrl();
        if (remoteUrl == null || remoteUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("remoteUrl must be set on DriverConfig to use remote driver.");
        }

        String browser = config.getBrowser();
        logger.info("Creating remote WebDriver for browser={} at {}", browser, remoteUrl);

        try {
            URL url = new URL(remoteUrl);
            switch (browser) {
                case "chrome":
                    return new RemoteWebDriver(url, getChromeOptions(config));
                case "firefox":
                    return new RemoteWebDriver(url, getFirefoxOptions(config));
                case "edge":
                    return new RemoteWebDriver(url, getEdgeOptions(config));
                default:
                    throw new IllegalArgumentException("Unsupported browser for remote driver: " + browser);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
        }
    }

    /**
     * Builds ChromeOptions based on configuration.
     *
     * @param config The driver configuration
     * @return Configured ChromeOptions
     */
    private static ChromeOptions getChromeOptions(DriverConfig config) {
        ChromeOptions options = new ChromeOptions();
        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        // Essential flags for Linux/Docker/CI environments
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");  // Critical for limited shared memory
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-software-rasterizer");
        options.addArguments(
            String.format("--window-size=%d,%d", config.getWindowWidth(), config.getWindowHeight())
        );
        return options;
    }

    /**
     * Builds FirefoxOptions based on configuration.
     *
     * @param config The driver configuration
     * @return Configured FirefoxOptions
     */
    private static FirefoxOptions getFirefoxOptions(DriverConfig config) {
        FirefoxOptions options = new FirefoxOptions();
        if (config.isHeadless()) {
            options.addArguments("-headless");
        }
        return options;
    }

    /**
     * Builds EdgeOptions based on configuration.
     *
     * @param config The driver configuration
     * @return Configured EdgeOptions
     */
    private static EdgeOptions getEdgeOptions(DriverConfig config) {
        EdgeOptions options = new EdgeOptions();
        if (config.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-gpu");
        options.addArguments(
            String.format("--window-size=%d,%d", config.getWindowWidth(), config.getWindowHeight())
        );
        return options;
    }

    /**
     * Parses a boolean value from environment variable.
     *
     * @param name The environment variable name
     * @param defaultValue The default value if not set
     * @return The boolean value
     */
    private static boolean envBool(String name, boolean defaultValue) {
        String raw = Env.getOptionalEnv(name, null);
        if (raw == null) {
            return defaultValue;
        }
        String normalized = raw.trim().toLowerCase();
        return normalized.equals("1") || normalized.equals("true") ||
               normalized.equals("yes") || normalized.equals("y") || normalized.equals("on");
    }

    /**
     * Parses an integer value from environment variable.
     *
     * @param name The environment variable name
     * @param defaultValue The default value if not set or invalid
     * @return The integer value
     */
    private static int envInt(String name, int defaultValue) {
        String raw = Env.getOptionalEnv(name, null);
        if (raw == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer for {}='{}'; using default {}", name, raw, defaultValue);
            return defaultValue;
        }
    }

    // Private constructor to prevent instantiation
    private DriverFactory() {
        throw new AssertionError("DriverFactory should not be instantiated");
    }
}
