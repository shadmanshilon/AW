package com.allied.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.allied.framework.core.DriverFactory;

/**
 * Base test class for all TestNG tests.
 *
 * <p>
 * This class provides WebDriver setup and teardown for tests. It is the Java
 * equivalent of pytest fixtures in the Python framework.
 * </p>
 *
 * <p>
 * <b>Features:</b>
 * </p>
 * <ul>
 * <li>Automatic WebDriver creation before each test method</li>
 * <li>Automatic WebDriver cleanup after each test method</li>
 * <li>Protected driver field accessible to all subclass tests</li>
 * <li>Logging for test lifecycle events</li>
 * </ul>
 *
 * <p>
 * <b>Usage Example:</b>
 * </p>
 *
 * <pre>{@code
 * public class LoginTest extends BaseTest {
 *
 *     @Test
 *     public void testValidLogin() {
 *         // driver is already initialized by @BeforeMethod
 *         driver.get(OrangeHrmLogin.URL);
 *
 *         sendKeysToElement(driver, OrangeHrmLogin.USERNAME_INPUT, "Admin", 10, true);
 *         sendKeysToElement(driver, OrangeHrmLogin.PASSWORD_INPUT, "admin123", 10, true);
 *         clickElement(driver, OrangeHrmLogin.LOGIN_BUTTON, 10);
 *
 *         assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_HEADING, 10, null);
 *         // driver is automatically quit by @AfterMethod
 *     }
 * }
 * }</pre>
 *
 * <p>
 * <b>Configuration:</b>
 * </p>
 * <p>
 * WebDriver is configured via environment variables (loaded from .env file):
 * </p>
 * <ul>
 * <li>BROWSER - "chrome", "firefox", or "edge"</li>
 * <li>HEADLESS - "true" or "false"</li>
 * <li>WINDOW_WIDTH, WINDOW_HEIGHT - Browser window size</li>
 * <li>PAGE_LOAD_TIMEOUT, IMPLICIT_WAIT - Timeout settings</li>
 * </ul>
 *
 * <p>
 * This is the Java equivalent of Python's browser_fixture.py.
 * </p>
 *
 * @see DriverFactory
 * @see org.testng.annotations.BeforeMethod
 * @see org.testng.annotations.AfterMethod
 */
public class BaseTest {

    protected static final Logger logger = com.allied.framework.core.Logger.getLogger(BaseTest.class);

    /**
     * WebDriver instance for the current test.
     * <p>
     * Initialized in setUp() before each test method.
     * </p>
     * <p>
     * Accessible to all test subclasses as a protected field.
     * </p>
     */
    protected WebDriver driver;

    /**
     * Sets up WebDriver before each test method.
     *
     * <p>
     * This method is automatically called by TestNG before each @Test method.
     * It creates a new WebDriver instance using DriverFactory with configuration
     * from environment variables.
     * </p>
     *
     * <p>
     * <b>Note:</b> If you need to override this method in a subclass, make sure
     * to call super.setUp() to ensure proper driver initialization.
     * </p>
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Setting up WebDriver for test");
        // WebDriverManager.chromedriver().setup();
        driver = DriverFactory.createDriver();
        logger.info("WebDriver setup complete");
    }


//         @BeforeMethod(alwaysRun = true)
//     public void setUp() {
//         driver = DriverFactory.createDriver();  // updated DriverFactory handles ThreadLocal
//     }

//     @AfterMethod(alwaysRun = true)
//     public void tearDown() {
//         DriverFactory.quitDriver();
//         driver = null;
// }

    /**
     * Tears down WebDriver after each test method.
     *
     * <p>
     * This method is automatically called by TestNG after each @Test method.
     * It ensures the WebDriver is properly quit, even if the test fails.
     * </p>
     *
     * <p>
     * <b>Note:</b> If you need to override this method in a subclass, make sure
     * to call super.tearDown() to ensure proper driver cleanup.
     * </p>
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("========================================");
        System.out.println("*** TEARDOWN CALLED ***");
        System.out.println("Driver is null: " + (driver == null));
        System.out.println("========================================");

        if (driver == null) {
            System.out.println("*** Driver was null, nothing to tear down ***");
            return;
        }

        try {
            // logger.info("Tearing down WebDriver");
            // Optional: close just the current window first
            // try {
            // driver.close();
            // } catch (Exception e) {
            // logger.debug("Error closing window", e);
            // }

            driver.quit();
            System.out.println("driver.quit() completed successfully");
            logger.info("WebDriver teardown complete");

            // Optional: give it a moment to clean up
            // Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("ERROR in tearDown: " + e.getMessage());
            e.printStackTrace();
            logger.error("Error during WebDriver teardown", e);
            // Optionally attempt additional cleanup here
        } finally {
            // Force kill browser processes on Windows as a last resort (if you implement
            // this)
            // String browser = Env.getOptionalEnv("BROWSER", "chrome");
            // SeleniumUtils.forceKillBrowser(browser);

            driver = null;
            System.out.println("*** TEARDOWN FINISHED ***");
        }
    }
}
