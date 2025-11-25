package com.allied.tests;

import org.testng.annotations.BeforeMethod;

import com.allied.framework.pages.loginpagedemo.LoginSetup01;

/**
 * Base test class that provides a pre-logged-in WebDriver.
 *
 * <p>
 * This class extends BaseTest and automatically performs login before each
 * test.
 * Use this for tests that require an authenticated session.
 * </p>
 *
 * <p>
 * <b>Features:</b>
 * </p>
 * <ul>
 * <li>Inherits WebDriver setup/teardown from BaseTest</li>
 * <li>Automatically logs in to OrangeHRM before each test</li>
 * <li>Driver is already on the dashboard when test starts</li>
 * </ul>
 *
 * <p>
 * <b>Usage Example:</b>
 * </p>
 * 
 * <pre>{@code
 * public class DashboardTest extends LoggedInBaseTest {
 *
 *     @Test
 *     public void testDashboardWidgets() {
 *         // Driver is already logged in and on dashboard
 *         // No need to call login or navigate
 *
 *         assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_WIDGET, 10, null);
 *         String widgetText = safeGetText(driver, OrangeHrmDashboard.DASHBOARD_WIDGET, 10, false);
 *         System.out.println("Widget: " + widgetText);
 *     }
 * }
 * }</pre>
 *
 * <p>
 * <b>When to Use:</b>
 * </p>
 * <ul>
 * <li>Tests that require authentication (dashboard, profile, admin pages)</li>
 * <li>Tests that don't need to verify login flow itself</li>
 * </ul>
 *
 * <p>
 * <b>When NOT to Use:</b>
 * </p>
 * <ul>
 * <li>Login tests - use BaseTest instead</li>
 * <li>Tests that need to start from logged-out state</li>
 * </ul>
 *
 * <p>
 * This is the Java equivalent of Python's logged_in_driver fixture.
 * </p>
 *
 * @see BaseTest
 * @see LoginSetup01
 */
public class LoggedInBaseTest extends BaseTest {

    /**
     * Sets up WebDriver and performs login before each test method.
     *
     * <p>
     * This method:
     * </p>
     * <ol>
     * <li>Calls super.setUp() to create WebDriver</li>
     * <li>Performs login using LoginSetup01</li>
     * <li>Waits for dashboard to load</li>
     * </ol>
     *
     * <p>
     * After this method completes, the driver is on the dashboard and ready for
     * testing.
     * </p>
     *
     * <p>
     * <b>Note:</b> If you need custom setup in a subclass, you can override this
     * method
     * but make sure to call super.setUp() to ensure driver initialization and
     * login.
     * </p>
     */
    @Override
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Create driver (from BaseTest)
        super.setUp();

        // Perform login
        logger.info("Performing login for pre-authenticated test");
        LoginSetup01.loginToOrangeHrm(driver);
        logger.info("Login complete, driver is on dashboard");
        super.tearDown();
    }
}
