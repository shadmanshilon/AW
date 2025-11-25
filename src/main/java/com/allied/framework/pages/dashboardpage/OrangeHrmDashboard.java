package com.allied.framework.pages.dashboardpage;

import com.allied.framework.pages.BasePage;
import org.openqa.selenium.By;

/**
 * OrangeHRM dashboard page object.
 *
 * <p>This page object contains locators for the OrangeHRM demo application dashboard page,
 * which is displayed after successful login.</p>
 *
 * <p><b>Design Pattern:</b></p>
 * <ul>
 *   <li>Extends BasePage to inherit common locators</li>
 *   <li>Contains page-specific locators as static final By objects</li>
 *   <li>No driver instance, no action methods - use SeleniumUtils for interactions</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * import static com.allied.framework.utils.SeleniumUtils.*;
 *
 * @Test
 * public void testDashboard() {
 *     // After login, verify dashboard elements are visible
 *     assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_HEADING, 10, null);
 *     assertElementVisible(driver, OrangeHrmDashboard.FOOTER, 10, null);
 *
 *     // Get text from dashboard widget
 *     String widgetText = safeGetText(driver, OrangeHrmDashboard.DASHBOARD_WIDGET, 10, true);
 *     System.out.println("Dashboard widget: " + widgetText);
 * }
 * }</pre>
 *
 * <p>This is the Java equivalent of Python's OrangeHrmDashboard class.</p>
 *
 * @see BasePage
 * @see com.allied.framework.utils.SeleniumUtils
 */
public class OrangeHrmDashboard extends BasePage {

    /**
     * Dashboard heading locator.
     * <p>HTML: {@code <h6>Dashboard</h6>}</p>
     * <p>This element appears at the top of the dashboard page after successful login.</p>
     */
    public static final By DASHBOARD_HEADING = By.xpath("//h6[normalize-space()='Dashboard']");

    /**
     * Footer locator.
     * <p>HTML: {@code <div class="oxd-layout-footer">...</div>}</p>
     * <p>The footer is displayed at the bottom of the page.</p>
     */
    public static final By FOOTER = By.cssSelector(".oxd-layout-footer");

    /**
     * Dashboard widget name locator.
     * <p>HTML: {@code <div class="orangehrm-dashboard-widget-name">...</div>}</p>
     * <p>Locates dashboard widget names/titles on the dashboard page.</p>
     */
    public static final By DASHBOARD_WIDGET = By.className("orangehrm-dashboard-widget-name");
}
