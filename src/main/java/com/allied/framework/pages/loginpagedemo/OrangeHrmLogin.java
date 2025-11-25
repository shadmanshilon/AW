package com.allied.framework.pages.loginpagedemo;

import com.allied.framework.core.Env;
import com.allied.framework.pages.BasePage;
import org.openqa.selenium.By;

/**
 * OrangeHRM login page object.
 *
 * <p>This page object contains the URL and locators for the OrangeHRM demo application login page.</p>
 *
 * <p><b>Design Pattern:</b></p>
 * <ul>
 *   <li>Extends BasePage to inherit common locators</li>
 *   <li>Contains page URL as a constant</li>
 *   <li>Contains page-specific locators as static final By objects</li>
 *   <li>No driver instance, no action methods - use SeleniumUtils for interactions</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 * import static com.allied.framework.utils.SeleniumUtils.*;
 *
 * @Test
 * public void testLogin() {
 *     // Navigate to login page
 *     driver.get(OrangeHrmLogin.URL);
 *
 *     // Perform login actions using SeleniumUtils
 *     sendKeysToElement(driver, OrangeHrmLogin.USERNAME_INPUT, "Admin", 10, true);
 *     sendKeysToElement(driver, OrangeHrmLogin.PASSWORD_INPUT, "admin123", 10, true);
 *     clickElement(driver, OrangeHrmLogin.LOGIN_BUTTON, 10);
 * }
 * }</pre>
 *
 * <p>This is the Java equivalent of Python's OrangeHrmLogin class.</p>
 *
 * @see BasePage
 * @see com.allied.framework.utils.SeleniumUtils
 */
public class OrangeHrmLogin extends BasePage {

    /**
     * URL for the OrangeHRM login page.
     * <p>Value is loaded from ORANGEHRM_BASE_URL environment variable.</p>
     */
    public static final String URL = Env.ORANGEHRM_BASE_URL;

    /**
     * Username input field locator.
     * <p>HTML: {@code <input name="username" />}</p>
     */
    public static final By USERNAME_INPUT = By.name("username");

    /**
     * Password input field locator.
     * <p>HTML: {@code <input name="password" type="password" />}</p>
     */
    public static final By PASSWORD_INPUT = By.name("password");

    /**
     * Login button locator.
     * <p>HTML: {@code <button class="orangehrm-login-button">Login</button>}</p>
     */
    public static final By LOGIN_BUTTON = By.cssSelector("button.orangehrm-login-button");
}
