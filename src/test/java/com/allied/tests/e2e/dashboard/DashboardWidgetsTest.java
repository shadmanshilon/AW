package com.allied.tests.e2e.dashboard;

import com.allied.framework.pages.AssignLeavePage;
import com.allied.framework.pages.LeavePage;
import com.allied.framework.pages.PIM;
import com.allied.framework.pages.dashboardpage.OrangeHrmDashboard;
import com.allied.framework.pages.loginpagedemo.LoginSetup01;
import com.allied.tests.BaseTest;
import com.allied.tests.LoggedInBaseTest;

import ch.qos.logback.core.util.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.annotations.Test;

import static com.allied.framework.utils.SeleniumUtils.clickElement;
import static com.allied.framework.utils.SeleniumUtils.sendKeysToElement;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
// import java.time.Duration;


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




    @Test(groups = {"e2e", "leave"})
   public void testAssignLeaveToEmployee() throws InterruptedException {

    // Login
    LoginSetup01.loginToOrangeHrm(driver);
    Thread.sleep(3000);

    WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));



    // Navigate to Leave → Assign Leave
    Thread.sleep(2000);
    clickElement(driver, AssignLeavePage.LEAVE_MENU, 10);
    clickElement(driver, AssignLeavePage.ASSIGN_LEAVE, 10);
    Thread.sleep(3000);

    // ----------------- EMPLOYEE NAME -----------------
  // after typing employee name
    sendKeysToElement(driver, AssignLeavePage.EMPLOYEE_NAME_INPUT, "shilon AW", 10, true);

    // Wait for suggestion to appear
    Thread.sleep(1500); 

    // Use Actions to press ARROW_DOWN and ENTER
    Actions actions = new Actions(driver);
    actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
    // ----------------- LEAVE TYPE -----------------
    clickElement(driver, AssignLeavePage.LEAVE_TYPE_SELECT, 10);

    // Select leave type from dropdown
    String leaveType = "CAN - Vacation";
    By leaveOption = By.xpath("//div[@role='option' and contains(.,'" + leaveType + "')]");
    wait.until(ExpectedConditions.visibilityOfElementLocated(leaveOption));
    driver.findElement(leaveOption).click();

    Thread.sleep(5000);

    // ----------------- FROM DATE -----------------
    sendKeysToElement(driver, AssignLeavePage.FROM_DATE_INPUT, "2025-26-11", 10, true);
    clickElement(driver, AssignLeavePage.CLICK, 0);
    Thread.sleep(2000);

    // sendKeysToElement(driver, AssignLeavePage.TO_DATE_INPUT, "2025-27-11", 10, true);

    //     // Click on the From Date input to open calendar
    // clickElement(driver, AssignLeavePage.FROM_DATE_INPUT, 10);
    // Thread.sleep(5000);

    // // Select today's date dynamically
    // WebElement todayDate = driver.findElement(By.cssSelector("div"));
    // todayDate.click();


    //     // Click on the To Date input to open calendar
    // clickElement(driver, AssignLeavePage.TO_DATE_INPUT, 10);

    // // Calculate tomorrow's date
    // LocalDate tomorrow = LocalDate.now().plusDays(1);
    // String dayString = String.valueOf(tomorrow.getDayOfMonth());

    // // Select next day in calendar
    // WebElement nextDay = driver.findElement(By.xpath("//div[@class='oxd-calendar-date' and text()='" + dayString + "']"));
    // nextDay.click();



    // ----------------- DURATION -----------------
    // clickElement(driver, AssignLeavePage.DURATION_SELECT, 10);

    // String duration = "Full Day";  // You can change: Full Day / Morning / Afternoon
    // By durationOption = By.xpath("//div[@role='option' and contains(.,'" + duration + "')]");
    
    // wait.until(ExpectedConditions.visibilityOfElementLocated(durationOption));
    // driver.findElement(durationOption).click();

    // ----------------- COMMENT -----------------
    sendKeysToElement(driver, AssignLeavePage.COMMENT_TEXTAREA, "Automated test for mr", 10, true);

    // ----------------- ASSIGN BUTTON -----------------
    clickElement(driver, AssignLeavePage.ASSIGN_BUTTON, 10);

    // ----------------- SUCCESS MESSAGE -----------------
    wait.until(ExpectedConditions.visibilityOfElementLocated(AssignLeavePage.ASSIGN_SUCCESS_MSG));

    WebElement msg = driver.findElement(AssignLeavePage.ASSIGN_SUCCESS_MSG);
    assert msg.isDisplayed() : "Assign leave was not successful!";
}




//    @Test(groups = {"e2e", "leave"})
//     public void testAssignLeaveToEmployee() throws InterruptedException {

//         // Login
//          LoginSetup01.loginToOrangeHrm(driver);

//          Thread.sleep(3000);

//         // Navigate to Leave → Assign Leave
//         clickElement(driver, AssignLeavePage.LEAVE_MENU, 10);
//         clickElement(driver, AssignLeavePage.ASSIGN_LEAVE, 10);
//         Thread.sleep(5000);

//         // Fill employee
//         sendKeysToElement(driver, AssignLeavePage.EMPLOYEE_NAME_INPUT, "shilon AW", 10, true);

//         clickElement(driver, AssignLeavePage.EMPOLYEE_CLICK, 0);

//         // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//         // wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion));

//         clickElement(driver, AssignLeavePage.LEAVE_TYPE_SELECT, 10);
//         Thread.sleep(3000);
//         List<WebElement> leaveOptions = driver.findElements(AssignLeavePage.LEAVE_TYPE_SELECT);
//         for (WebElement option : leaveOptions) {
//             if (option.getText().equals("CAN - Personal")) {
//                 option.click();
//                 break;
//         }
//     }

//         // Choose leave type (just click dropdown and pick first option)
//         clickElement(driver, AssignLeavePage.LEAVE_TYPE_SELECT, 10);
//         // List<WebElement> options = driver.findElements(AssignLeavePage.LEAVE_TYPE_OPTION);
//         // e.g., pick the first option
//         // if (!options.isEmpty()) {
//         //     options.get(0).click();
//         // }

//         // Set date range
//         sendKeysToElement(driver, AssignLeavePage.FROM_DATE_INPUT, "2025-12-01", 10, true);
//         // sendKeysToElement(driver, AssignLeavePage.TO_DATE_INPUT,   "2025-12-02", 10, true);

//         // Select duration
//         clickElement(driver, AssignLeavePage.DURATION_SELECT, 10);
//         // List<WebElement> durOptions = driver.findElements(AssignLeavePage.DURATION_OPTION);
//         // if (!durOptions.isEmpty()) {
//         //     durOptions.get(0).click();
//         // }

//         // Optional comment
//         sendKeysToElement(driver, AssignLeavePage.COMMENT_TEXTAREA, "Automated assignment test", 10, true);

//         // Click Assign
//         clickElement(driver, AssignLeavePage.ASSIGN_BUTTON, 10);

//         // Wait for success message
//         // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//         // wait.until(ExpectedConditions.visibilityOfElementLocated(AssignLeavePage.ASSIGN_SUCCESS_MSG));

//         // Assertion: ensuring success message found
//         WebElement msg = driver.findElement(AssignLeavePage.ASSIGN_SUCCESS_MSG);
//         assert msg.isDisplayed() : "Assign leave was not successful!";
//     }






    @Test(groups = {"e2e", "leave"})
    public void testSearchLeaveForEmployee() throws InterruptedException {

        // Step 1: Login
        LoginSetup01.loginToOrangeHrm(driver);

        // Step 2: Navigate to Leave -> Leave List
        clickElement(driver, LeavePage.LEAVE_MENU, 10);
        clickElement(driver, LeavePage.LEAVE_LIST_SUBMENU, 10);

        // Step 3: Enter Employee Name
        sendKeysToElement(driver, LeavePage.EMPLOYEE_NAME_INPUT, "shilon", 10, true);

        // Step 4: Enter From and To dates
        sendKeysToElement(driver, LeavePage.FROM_DATE_INPUT, "2025-11-01", 10, true);
        sendKeysToElement(driver, LeavePage.TO_DATE_INPUT, "2025-11-25", 10, true);

        // Step 5: Click Search
        clickElement(driver, LeavePage.SEARCH_BUTTON, 10);

        // Step 6: Wait for results table to appear
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // wait.until(ExpectedConditions.visibilityOfElementLocated(LeavePage.RESULT_TABLE));

        // Optional: Wait and print confirmation
        Thread.sleep(10000);
        System.out.println("Leave search completed successfully!");
    }
}




