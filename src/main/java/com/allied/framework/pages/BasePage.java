package com.allied.framework.pages;

import org.openqa.selenium.By;

/**
 * Base page class for all page objects.
 *
 * <p>This class contains common locators that are shared across multiple pages
 * in the application, such as OK buttons, Close buttons, etc.</p>
 *
 * <p><b>Design Pattern:</b></p>
 * <ul>
 *   <li>Contains only shared locators (no driver, no actions)</li>
 *   <li>Page objects extend this class to inherit common locators</li>
 *   <li>Page-specific locators are defined in individual page object classes</li>
 *   <li>Actions are performed using SeleniumUtils, not in page objects</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * import static com.allied.framework.utils.SeleniumUtils.*;
 *
 * public class LoginPage extends BasePage {
 *     // Page-specific locators
 *     public static final By USERNAME_INPUT = By.id("username");
 *     public static final By PASSWORD_INPUT = By.id("password");
 *     public static final By LOGIN_BUTTON = By.id("login-btn");
 *
 *     // Page URL
 *     public static final String URL = "https://example.com/login";
 *
 *     // Can use inherited locators from BasePage
 *     public void closeDialog(WebDriver driver) {
 *         clickElement(driver, CLOSE_BUTTON, 10);
 *     }
 * }
 * }</pre>
 *
 * <p><b>Locator Format:</b></p>
 * <p>All locators are defined as static final By objects:</p>
 * <pre>{@code
 * public static final By ELEMENT_NAME = By.cssSelector(".class");
 * public static final By ELEMENT_NAME = By.id("element-id");
 * public static final By ELEMENT_NAME = By.xpath("//button[text()='Click']");
 * }</pre>
 *
 * <p>This is the Java equivalent of Python's BasePage class.</p>
 *
 * @see org.openqa.selenium.By
 * @see com.allied.framework.utils.SeleniumUtils
 */
public class BasePage {

    /**
     * Common OK button locator.
     * <p>Matches buttons with text "OK" or "Ok"</p>
     */
    public static final By OK_BUTTON = By.xpath(
        "//button[normalize-space()='OK' or normalize-space()='Ok']"
    );

    /**
     * Common Close button locator.
     * <p>Matches buttons with text "Close" or aria-label "Close"</p>
     */
    public static final By CLOSE_BUTTON = By.xpath(
        "//button[normalize-space()='Close' or @aria-label='Close']"
    );

    // Additional common locators can be added here as needed
}
