package com.allied.tests.e2e.dashboard;

import com.allied.framework.pages.PIM;
import com.allied.framework.pages.dashboardpage.OrangeHrmDashboard;
import com.allied.framework.pages.loginpagedemo.LoginSetup01;
import com.allied.tests.BaseTest;
import com.allied.tests.LoggedInBaseTest;

import ch.qos.logback.core.util.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.annotations.Test;

import static com.allied.framework.utils.SeleniumUtils.clickElement;
import static com.allied.framework.utils.SeleniumUtils.sendKeysToElement;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Dashboard widget tests for OrangeHRM application.
 *
 * <p>This test class verifies the dashboard widgets are displayed correctly
 * after successful login.</p>
 *
 * <p><b>Test Coverage:</b></p>
 * <ul>
 *   <li>Correct number of widgets displayed</li>
 *   <li>Widget titles match expected values</li>
 * </ul>
 *
 * <p>This is the Java equivalent of Python's test_dashboard_widgets.py.</p>
 *
 * @see OrangeHrmDashboard
 * @see LoggedInBaseTest
 */
public class DashboardWidgetsTest extends BaseTest {
    private static final Logger logger = com.allied.framework.core.Logger.getLogger(DashboardWidgetsTest.class);

    /**
     * Tests that dashboard displays exactly 7 widgets.
     *
     * <p><b>Test Steps:</b></p>
     * <ol>
     *   <li>Login to OrangeHRM</li>
     *   <li>Find all dashboard widgets</li>
     *   <li>Verify count is 7</li>
     * </ol>
     *
     * <p><b>Expected Result:</b></p>
     * <ul>
     *   <li>Dashboard displays exactly 7 widgets</li>
     * </ul>
     */
    @Test(groups = {"e2e", "dashboard"})
    public void testDashboardHas7Widgets() {
        // Login
        LoginSetup01.loginToOrangeHrm(driver);

        // Find all widgets
        List<WebElement> widgets = driver.findElements(OrangeHrmDashboard.DASHBOARD_WIDGET);

        // Log widget texts for debugging
        List<String> widgetTexts = widgets.stream()
                .map(w -> w.getText().trim())
                .collect(Collectors.toList());
        logger.info("Found dashboard widgets:");
        logger.info(widgetTexts.toString());

        // Assert count
        assert widgets.size() == 7 :
            String.format("Expected 7 widgets, found %d: %s", widgets.size(), widgetTexts);
    }

    /**
     * Tests that dashboard widgets have expected titles.
     *
     * <p><b>Test Steps:</b></p>
     * <ol>
     *   <li>Start with logged-in driver (uses LoggedInBaseTest)</li>
     *   <li>Find all dashboard widgets</li>
     *   <li>Extract widget titles</li>
     *   <li>Verify titles match expected values</li>
     * </ol>
     *
     * <p><b>Expected Result:</b></p>
     * <ul>
     *   <li>All expected widget titles are present</li>
     *   <li>No unexpected widgets are displayed</li>
     * </ul>
     *
     * <p><b>Note:</b> This test extends BaseTest but could also extend LoggedInBaseTest
     * for automatic login. For demonstration, using LoginSetup01 here.</p>
     */
    @Test(groups = {"e2e", "dashboard"})
    public void testDashboardWidgetsHaveExpectedTitles() {
        // Login
        LoginSetup01.loginToOrangeHrm(driver);

        // Find all widgets and extract titles
        List<WebElement> widgets = driver.findElements(OrangeHrmDashboard.DASHBOARD_WIDGET);
        List<String> actualTitles = widgets.stream()
                .map(w -> w.getText().trim())
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());

        // Expected titles
        List<String> expectedTitles = Arrays.asList(
            "Time at Work",
            "My Actions",
            "Quick Launch",
            "Buzz Latest Posts",
            "Employees on Leave Today",
            "Employee Distribution by Sub Unit",
            "Employee Distribution by Location"
        );

        // Assert count first (gives clearer failure message)
        assert actualTitles.size() == expectedTitles.size() :
            String.format("Expected %d widgets, got %d: %s",
                expectedTitles.size(), actualTitles.size(), actualTitles);

        // Check that all expected titles are present (order-independent)
        Set<String> actualSet = actualTitles.stream().collect(Collectors.toSet());
        Set<String> expectedSet = expectedTitles.stream().collect(Collectors.toSet());

        assert actualSet.equals(expectedSet) :
            String.format("Dashboard widget titles did not match.\nExpected: %s\nActual:   %s",
                expectedTitles, actualTitles);
    }





    @Test(groups = {"e2e", "pim"})
    public void testAddEmployeeInPIM() throws InterruptedException {

        // Step 1: Login
        LoginSetup01.loginToOrangeHrm(driver);

        // Step 2: Click PIM Menu
        clickElement(driver, PIM.PIM_MENU, 10);

        // Step 3: Click Add button
        clickElement(driver, PIM.ADD_BUTTON, 10);

        // Step 4: Fill First Name
        sendKeysToElement(driver, PIM.FIRST_NAME_INPUT, "shilon", 10, true);

        // Step 5: Fill Last Name
        sendKeysToElement(driver, PIM.LAST_NAME_INPUT, "AW", 10, true);

        // Step 6: Click Save button
        clickElement(driver, PIM.SAVEBUTTON, 10);

        // Optional small wait
        Thread.sleep(1500);

        // Step 7: Go back to PIM list
        clickElement(driver, PIM.PIM_MENU, 10);

        // Step 8: Filter employee
        sendKeysToElement(driver, PIM.FILTER, "shilon", 10, true);
        clickElement(driver, PIM.SEARCH, 10);
        Thread.sleep(10000);
    }

}
