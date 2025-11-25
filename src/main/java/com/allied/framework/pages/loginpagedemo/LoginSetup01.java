package com.allied.framework.pages.loginpagedemo;

import com.allied.framework.core.Env;
import com.allied.framework.pages.dashboardpage.OrangeHrmDashboard;
import org.openqa.selenium.WebDriver;

import static com.allied.framework.utils.SeleniumUtils.*;

/**
 * Login setup utility for OrangeHRM application - Setup 01.
 *
 * <p>This class provides reusable login methods to help keep tests focused on
 * their main assertions rather than login steps. Multiple setup classes can exist
 * (LoginSetup01, LoginSetup02, etc.) for different login scenarios.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * @Test
 * public void testDashboardWidgets() {
 *     // Login using setup
 *     LoginSetup01.loginToOrangeHrm(driver);
 *
 *     // Now test can focus on dashboard functionality
 *     WebElement widget = waitForVisible(driver, OrangeHrmDashboard.DASHBOARD_WIDGET, 10);
 *     // ... test assertions ...
 * }
 * }</pre>
 *
 * <p>Alternatively, extend LoggedInBaseTest to get automatic login in @BeforeMethod.</p>
 *
 * <p>This is the Java equivalent of Python's login_setup_01.py module.</p>
 *
 * @see OrangeHrmLogin
 * @see com.allied.tests.LoggedInBaseTest
 */
public class LoginSetup01 {

    /**
     * Performs login to OrangeHRM application.
     *
     * <p>This method:</p>
     * <ol>
     *   <li>Navigates to the login page</li>
     *   <li>Enters username from ORANGEHRM_USERNAME environment variable</li>
     *   <li>Enters password from ORANGEHRM_PASSWORD environment variable</li>
     *   <li>Clicks the login button</li>
     *   <li>Waits for dashboard to load (verifies login success)</li>
     * </ol>
     *
     * <p><b>Usage Example:</b></p>
     * <pre>{@code
     * @Test
     * public void testSomethingAfterLogin() {
     *     LoginSetup01.loginToOrangeHrm(driver);
     *     // Now on dashboard, ready to test
     * }
     * }</pre>
     *
     * @param driver The WebDriver instance to use for login
     * @throws org.openqa.selenium.TimeoutException if login fails or dashboard doesn't load
     */
    public static void loginToOrangeHrm(WebDriver driver) {
        // Navigate to login page
        driver.get(OrangeHrmLogin.URL);

        // Enter credentials
        sendKeysToElement(driver, OrangeHrmLogin.USERNAME_INPUT, Env.ORANGEHRM_USERNAME, 10, true);
        sendKeysToElement(driver, OrangeHrmLogin.PASSWORD_INPUT, Env.ORANGEHRM_PASSWORD, 10, true);

        // Click login button
        clickElement(driver, OrangeHrmLogin.LOGIN_BUTTON, 10);

        // Wait for dashboard to load (confirms successful login)
        assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_HEADING, 10, "Login failed - dashboard not displayed");
    }

    // Private constructor to prevent instantiation
    private LoginSetup01() {
        throw new AssertionError("LoginSetup01 should not be instantiated");
    }
}
