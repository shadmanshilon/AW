package com.allied.framework.pages;

// import com.allied.framework.core.Env;
// import com.allied.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

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
public class PIM extends BasePage {

    /**
     * URL for the OrangeHRM login page.
     * <p>Value is loaded from ORANGEHRM_BASE_URL environment variable.</p>
     */
    // public static final String PIM_MENU = Env.ORANGEHRM_BASE_URL;

    /**
     * Username input field locator.
     * <p>HTML: {@code <input name="username" />}</p>
     */
    public static final By PIM_MENU = By.xpath("//span[text()='PIM']");
    
    
    public static final By ADD_BUTTON = By.xpath("//button[normalize-space()='Add']"); 
    
   
    public static final By FIRST_NAME_INPUT = By.xpath("//input[@name='firstName']"); 

    public static final By LAST_NAME_INPUT = By.xpath("//input[@name='lastName']"); 


    public static final By SAVEBUTTON = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div/form/div[2]/button[2]"); 
    
    public static final By FILTER = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/div/div/input"); 

    public static final By SEARCH = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]");




   
}

