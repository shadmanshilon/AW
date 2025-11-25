package com.allied.framework.core;

/**
 * Configuration for creating a WebDriver instance.
 *
 * <p>This class holds all the configuration settings needed to create a Selenium WebDriver.
 * It supports both local and remote (Selenium Grid) execution.</p>
 *
 * <p><b>Configuration options:</b></p>
 * <ul>
 *   <li>browser: "chrome", "firefox", or "edge" (default: "chrome")</li>
 *   <li>headless: Run browser in headless mode (default: true)</li>
 *   <li>remoteUrl: Selenium Grid URL for remote execution (default: null for local)</li>
 *   <li>windowWidth: Browser window width in pixels (default: 1920)</li>
 *   <li>windowHeight: Browser window height in pixels (default: 1080)</li>
 *   <li>pageLoadTimeout: Page load timeout in seconds (default: 60)</li>
 *   <li>implicitWait: Implicit wait timeout in seconds (default: 0)</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * // Use default configuration
 * DriverConfig config = new DriverConfig();
 *
 * // Customize configuration
 * DriverConfig config = new DriverConfig();
 * config.setBrowser("firefox");
 * config.setHeadless(false);
 * config.setWindowWidth(1366);
 * config.setWindowHeight(768);
 *
 * // Use with DriverFactory
 * WebDriver driver = DriverFactory.createDriver(config);
 * }</pre>
 *
 * <p>This is the Java equivalent of Python's DriverConfig dataclass.</p>
 *
 * @see DriverFactory
 */
public class DriverConfig {
    private String browser;
    private boolean headless;
    private String remoteUrl;
    private int windowWidth;
    private int windowHeight;
    private int pageLoadTimeout;
    private int implicitWait;

    /**
     * Creates a new DriverConfig with default values.
     *
     * <p><b>Defaults:</b></p>
     * <ul>
     *   <li>browser = "chrome"</li>
     *   <li>headless = true</li>
     *   <li>remoteUrl = null (local execution)</li>
     *   <li>windowWidth = 1920</li>
     *   <li>windowHeight = 1080</li>
     *   <li>pageLoadTimeout = 60 seconds</li>
     *   <li>implicitWait = 0 seconds</li>
     * </ul>
     */
    public DriverConfig() {
        this.browser = "chrome";
        this.headless = true;
        this.remoteUrl = null;
        this.windowWidth = 1920;
        this.windowHeight = 1080;
        this.pageLoadTimeout = 60;
        this.implicitWait = 0;
    }

    /**
     * Gets the browser name.
     *
     * @return Browser name ("chrome", "firefox", or "edge")
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * Sets the browser name.
     *
     * @param browser Browser name ("chrome", "firefox", or "edge")
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    /**
     * Checks if headless mode is enabled.
     *
     * @return true if browser should run in headless mode, false otherwise
     */
    public boolean isHeadless() {
        return headless;
    }

    /**
     * Sets headless mode.
     *
     * @param headless true to run browser in headless mode, false otherwise
     */
    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    /**
     * Gets the remote Selenium Grid URL.
     *
     * @return Selenium Grid URL, or null for local execution
     */
    public String getRemoteUrl() {
        return remoteUrl;
    }

    /**
     * Sets the remote Selenium Grid URL.
     *
     * @param remoteUrl Selenium Grid URL (e.g., "http://localhost:4444/wd/hub"),
     *                  or null for local execution
     */
    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    /**
     * Gets the browser window width.
     *
     * @return Window width in pixels
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Sets the browser window width.
     *
     * @param windowWidth Window width in pixels
     */
    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    /**
     * Gets the browser window height.
     *
     * @return Window height in pixels
     */
    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Sets the browser window height.
     *
     * @param windowHeight Window height in pixels
     */
    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    /**
     * Gets the page load timeout.
     *
     * @return Page load timeout in seconds
     */
    public int getPageLoadTimeout() {
        return pageLoadTimeout;
    }

    /**
     * Sets the page load timeout.
     *
     * @param pageLoadTimeout Page load timeout in seconds
     */
    public void setPageLoadTimeout(int pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
    }

    /**
     * Gets the implicit wait timeout.
     *
     * @return Implicit wait timeout in seconds
     */
    public int getImplicitWait() {
        return implicitWait;
    }

    /**
     * Sets the implicit wait timeout.
     *
     * @param implicitWait Implicit wait timeout in seconds
     */
    public void setImplicitWait(int implicitWait) {
        this.implicitWait = implicitWait;
    }

    /**
     * Returns a string representation of this configuration.
     *
     * @return String representation showing all configuration values
     */
    @Override
    public String toString() {
        return "DriverConfig{" +
                "browser='" + browser + '\'' +
                ", headless=" + headless +
                ", remoteUrl='" + remoteUrl + '\'' +
                ", windowWidth=" + windowWidth +
                ", windowHeight=" + windowHeight +
                ", pageLoadTimeout=" + pageLoadTimeout +
                ", implicitWait=" + implicitWait +
                '}';
    }
}
