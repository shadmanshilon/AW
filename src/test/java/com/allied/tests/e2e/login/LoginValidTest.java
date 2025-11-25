package com.allied.tests.e2e.login;

import static com.allied.framework.utils.SeleniumUtils.assertElementVisible;
import static com.allied.framework.utils.SeleniumUtils.clickElement;
import static com.allied.framework.utils.SeleniumUtils.sendKeysToElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.allied.framework.core.Env;
import com.allied.framework.pages.PIM;
import com.allied.framework.pages.dashboardpage.OrangeHrmDashboard;
import com.allied.framework.pages.loginpagedemo.OrangeHrmLogin;
import com.allied.tests.BaseTest;

import ch.qos.logback.core.util.Duration;
import rx.exceptions.Exceptions;

/**
 * Valid login tests for OrangeHRM application.
 *
 * <p>
 * This test class verifies successful login scenarios for the OrangeHRM demo
 * application.
 * </p>
 *
 * <p>
 * <b>Test Coverage:</b>
 * </p>
 * <ul>
 * <li>Valid username and password login</li>
 * <li>Dashboard display after successful login</li>
 * <li>Footer visibility after login</li>
 * </ul>
 *
 * <p>
 * This is the Java equivalent of Python's test_login_valid.py.
 * </p>
 *
 * @see BaseTest
 * @see OrangeHrmLogin
 * @see OrangeHrmDashboard
 */
public class LoginValidTest extends BaseTest {

    /**
     * Tests the happy path login flow.
     *
     * <p>
     * <b>Test Steps:</b>
     * </p>
     * <ol>
     * <li>Navigate to OrangeHRM login page</li>
     * <li>Enter valid username</li>
     * <li>Enter valid password</li>
     * <li>Click login button</li>
     * <li>Verify dashboard heading is visible</li>
     * <li>Verify footer is visible</li>
     * </ol>
     *
     * <p>
     * <b>Expected Result:</b>
     * </p>
     * <ul>
     * <li>Login succeeds</li>
     * <li>User is redirected to dashboard</li>
     * <li>Dashboard elements are displayed</li>
     * </ul>
     */
    @Test(groups = { "e2e", "login", "smoke" })
    public void testOrangeHrmLoginHappyPath() {
        // Navigate to login page
        driver.get(OrangeHrmLogin.URL);

        // Enter credentials and login
        sendKeysToElement(driver, OrangeHrmLogin.USERNAME_INPUT, Env.ORANGEHRM_USERNAME, 10, true);
        sendKeysToElement(driver, OrangeHrmLogin.PASSWORD_INPUT, Env.ORANGEHRM_PASSWORD, 10, true);
        clickElement(driver, OrangeHrmLogin.LOGIN_BUTTON, 10);

        // Verify dashboard is displayed
        assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_HEADING, 10,
                "Dashboard heading not visible after login");
        assertElementVisible(driver, OrangeHrmDashboard.FOOTER, 10, "Footer not visible after login");
    }




    @Test(groups = { "e2e", "login", "smoke" })
    public void PIM_ADD ()throws InterruptedException {
        // Navigate to login page
        driver.get(OrangeHrmLogin.URL);

        // WebDriverWait wait = new WebDriverWait(driver, Duration.buildBySeconds(20));
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));


        // Enter credentials and login
        sendKeysToElement(driver, OrangeHrmLogin.USERNAME_INPUT, Env.ORANGEHRM_USERNAME, 10, true);
        sendKeysToElement(driver, OrangeHrmLogin.PASSWORD_INPUT, Env.ORANGEHRM_PASSWORD, 10, true);
        clickElement(driver, OrangeHrmLogin.LOGIN_BUTTON, 10);

        // Verify dashboard is displayed
        assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_HEADING, 10,
                "Dashboard heading not visible after login");
        assertElementVisible(driver, OrangeHrmDashboard.FOOTER, 10, "Footer not visible after login");

        Thread.sleep(5000);

        clickElement(driver, PIM.PIM_MENU, 10);

        Thread.sleep(3000);

        clickElement(driver, PIM.ADD_BUTTON, 10);

        Thread.sleep(3000);

        // sendKeysToElement(driver, PIM.FIRST_NAME_INPUT, Env.FIRST_NAME, 10, true);

        sendKeysToElement(driver, PIM.FIRST_NAME_INPUT, "SHSHSH", 10, true);

        sendKeysToElement(driver, PIM.LAST_NAME_INPUT, "KKKK", 10, true);

        clickElement(driver, PIM.SAVEBUTTON, 10);

        Thread.sleep(5000);

        clickElement(driver, PIM.PIM_MENU, 10);

        Thread.sleep(5000);

        sendKeysToElement(driver, PIM.FILTER, "SHSHSH", 10, true);

         Thread.sleep(3000);

        clickElement(driver, PIM.SEARCH, 10);



        Thread.sleep(10000);



    }


    /**
     * Simple example test to verify page title.
     *
     * <p>
     * This is a basic smoke test to ensure the application is accessible.
     * </p>
     */
    @Test(groups = { "smoke" })
    public void testExample() {
        driver.get("https://opensource-demo.orangehrmlive.com/");
        assert driver.getTitle().contains("OrangeHRM") : "Page title should contain 'OrangeHRM'";
    }
}
