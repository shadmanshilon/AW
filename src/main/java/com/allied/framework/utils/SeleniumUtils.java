package com.allied.framework.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.allied.framework.core.Logger;

/**
 * Selenium utility functions for common WebDriver operations.
 *
 * <p>
 * This class provides wrapper methods for common Selenium operations with
 * built-in
 * waiting, error handling, and logging. Use these utilities instead of raw
 * Selenium
 * // * commands for more reliable test automation.
 * </p>
 *
 * <p>
 * <b>Available Utilities:</b>
 * </p>
 * <ul>
 * <li>{@link #waitForPresent(WebDriver, By, int)} - Wait for element presence
 * in DOM</li>
 * <li>{@link #waitForVisible(WebDriver, By, int)} - Wait for element
 * visibility</li>
 * <li>{@link #waitForClickable(WebDriver, By, int)} - Wait for element to be
 * clickable</li>
 * <li>{@link #clickElement(WebDriver, By, int)} - Click element with
 * auto-scroll on interception</li>
 * <li>{@link #sendKeysToElement(WebDriver, By, String, int, boolean)} - Type
 * text into element</li>
 * <li>{@link #assertElementVisible(WebDriver, By, int, String)} - Assert
 * element is visible</li>
 * <li>{@link #safeGetText(WebDriver, By, int, boolean)} - Safely extract text
 * from element</li>
 * <li>{@link #reloadPage(WebDriver, By)} - Reload page via UI button or browser
 * refresh</li>
 * </ul>
 *
 * <p>
 * <b>Usage Example:</b>
 * </p>
 * 
 * <pre>{@code
 * import static com.allied.framework.utils.SeleniumUtils.*;
 *
 * public class LoginTest {
 *     @Test
 *     public void testLogin() {
 *         driver.get("https://example.com/login");
 *
 *         // Wait and type into username field
 *         sendKeysToElement(driver, By.id("username"), "admin", 10, true);
 *
 *         // Wait and type into password field
 *         sendKeysToElement(driver, By.id("password"), "password123", 10, true);
 *
 *         // Click login button (with auto-scroll if needed)
 *         clickElement(driver, By.id("login-btn"), 10);
 *
 *         // Assert welcome message is visible
 *         assertElementVisible(driver, By.xpath("//h1[text()='Welcome']"), 10, null);
 *     }
 * }
 * }</pre>
 *
 * <p>
 * This is the Java equivalent of Python's selenium_utils.py module.
 * </p>
 *
 * @see org.openqa.selenium.support.ui.WebDriverWait
 * @see org.openqa.selenium.support.ui.ExpectedConditions
 */
public class SeleniumUtils {
    private static final org.slf4j.Logger logger = Logger.getLogger(SeleniumUtils.class);

    /**
     * Waits until element is present in the DOM and returns it.
     *
     * <p>
     * Element does not need to be visible, just present in the DOM.
     * </p>
     *
     * <p>
     * <b>Usage Example:</b>
     * </p>
     * 
     * <pre>{@code
     * WebElement element = waitForPresent(driver, By.id("hidden-element"), 10);
     * }</pre>
     *
     * @param driver  The WebDriver instance
     * @param locator The By locator for the element
     * @param timeout Timeout in seconds
     * @return The WebElement once it's present in DOM
     * @throws org.openqa.selenium.TimeoutException if element not found within
     *                                              timeout
     */
    public static WebElement waitForPresent(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until element is visible and returns it.
     *
     * <p>
     * Element must be both present in DOM and visible (display != none, opacity !=
     * 0).
     * </p>
     *
     * <p>
     * <b>Usage Example:</b>
     * </p>
     * 
     * <pre>{@code
     * WebElement element = waitForVisible(driver, By.cssSelector(".modal"), 10);
     * }</pre>
     *
     * @param driver  The WebDriver instance
     * @param locator The By locator for the element
     * @param timeout Timeout in seconds
     * @return The WebElement once it's visible
     * @throws org.openqa.selenium.TimeoutException if element not visible within
     *                                              timeout
     */
    public static WebElement waitForVisible(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until element is clickable and returns it.
     *
     * <p>
     * Element must be both visible and enabled (not disabled).
     * </p>
     *
     * <p>
     * <b>Usage Example:</b>
     * </p>
     * 
     * <pre>{@code
     * WebElement button = waitForClickable(driver, By.id("submit-btn"), 10);
     * }</pre>
     *
     * @param driver  The WebDriver instance
     * @param locator The By locator for the element
     * @param timeout Timeout in seconds
     * @return The WebElement once it's clickable
     * @throws org.openqa.selenium.TimeoutException if element not clickable within
     *                                              timeout
     */
    public static WebElement waitForClickable(WebDriver driver, By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits for element to be clickable and clicks it.
     *
     * <p>
     * If the first click is intercepted (e.g., by an overlay or modal), this method
     * automatically scrolls the element into view and retries the click.
     * </p>
     *
     * <p>
     * <b>Usage Example:</b>
     * </p>
     * 
     * <pre>{@code
     * // Click with default 10 second timeout
     * clickElement(driver, By.id("submit-btn"), 10);
     *
     * // Click with custom timeout
     * clickElement(driver, By.xpath("//button[text()='Save']"), 15);
     * }</pre>
     *
     * @param driver  The WebDriver instance
     * @param locator The By locator for the element to click
     * @param timeout Timeout in seconds to wait for element to be clickable
     * @throws org.openqa.selenium.TimeoutException if element not clickable within
     *                                              timeout
     */
    public static void clickElement(WebDriver driver, By locator, int timeout) {
        WebElement element = waitForClickable(driver, locator, timeout);

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Try to bring element into view and click again
            logger.debug("Click intercepted for {}, scrolling into view and retrying", locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            element.click();
        }
    }

    /**
     * Waits for element to be visible, optionally clears it, and sends keys.
     *
     * <p>
     * <b>Usage Examples:</b>
     * </p>
     * 
     * <pre>{@code
     * // Clear field and type text (default behavior)
     * sendKeysToElement(driver, By.id("username"), "admin", 10, true);
     *
     * // Append text without clearing (clearFirst = false)
     * sendKeysToElement(driver, By.id("search"), "selenium", 10, false);
     *
     * // Type with custom timeout
     * sendKeysToElement(driver, By.name("email"), "test@example.com", 15, true);
     * }</pre>
     *
     * @param driver     The WebDriver instance
     * @param locator    The By locator for the element
     * @param text       The text to type
     * @param timeout    Timeout in seconds to wait for element to be visible
     * @param clearFirst If true, clears the field before typing; if false, appends
     *                   text
     * @throws org.openqa.selenium.TimeoutException if element not visible within
     *                                              timeout
     */
    public static void sendKeysToElement(WebDriver driver, By locator, String text, int timeout, boolean clearFirst) {
        WebElement element = waitForVisible(driver, locator, timeout);

        if (clearFirst) {
            element.clear();
        }

        element.sendKeys(text);
    }

    /**
     * Waits for element to be visible and asserts it is displayed.
     *
     * <p>
     * This method combines waiting and assertion. Use it when you need to verify
     * that an element appears on the page.
     * </p>
     *
     * <p>
     * <b>Usage Examples:</b>
     * </p>
     * 
     * <pre>{@code
     * // Assert element is visible (default message)
     * assertElementVisible(driver, By.xpath("//h1[text()='Dashboard']"), 10, null);
     *
     * // Assert with custom error message
     * assertElementVisible(driver, By.id("success-msg"), 10, "Success message not displayed");
     * }</pre>
     *
     * @param driver  The WebDriver instance
     * @param locator The By locator for the element
     * @param timeout Timeout in seconds to wait for element to be visible
     * @param message Custom assertion message (optional, can be null)
     * @return The WebElement if visible, so callers can continue to use it
     * @throws AssertionError                       if element is not displayed
     * @throws org.openqa.selenium.TimeoutException if element not visible within
     *                                              timeout
     */
    public static WebElement assertElementVisible(WebDriver driver, By locator, int timeout, String message) {
        WebElement element = waitForVisible(driver, locator, timeout);

        if (!element.isDisplayed()) {
            String errorMessage = message != null ? message
                    : String.format("Expected element to be visible: %s", locator);
            throw new AssertionError(errorMessage);
        }

        return element;
    }

    /**
     * Safely gets text from an element with timeout handling (defaults to required
     * element).
     *
     * <p>
     * This overload assumes the element is expected to be present (elementPresent =
     * true).
     * If the element is not found, a TimeoutException will be thrown.
     * </p>
     *
     * <p>
     * <b>Usage Example:</b>
     * </p>
     * 
     * <pre>{@code
     * // Get text from required element (throws exception if not found)
     * String heading = safeGetText(driver, By.tagName("h1"), 10);
     * }</pre>
     *
     * @param driver  The WebDriver instance
     * @param locator The By locator for the element
     * @param timeout Timeout in seconds to wait for element to be visible
     * @return The trimmed text content of the element
     * @throws org.openqa.selenium.TimeoutException if element not found
     */
    public static String safeGetText(WebDriver driver, By locator, int timeout) {
        return safeGetText(driver, locator, timeout, true);
    }

    /**
     * Safely gets text from an element with timeout handling.
     *
     * <p>
     * This method waits for the element to be visible and returns its text.
     * If the element is not found within the timeout:
     * </p>
     * <ul>
     * <li>If elementPresent = true, throws TimeoutException (element is
     * required)</li>
     * <li>If elementPresent = false, returns empty string "" (element is
     * optional)</li>
     * </ul>
     *
     * <p>
     * <b>Usage Examples:</b>
     * </p>
     * 
     * <pre>{@code
     * // Get text from required element (throws exception if not found)
     * String heading = safeGetText(driver, By.tagName("h1"), 10, true);
     *
     * // Get text from optional element (returns "" if not found)
     * String errorMsg = safeGetText(driver, By.cssSelector(".error-message"), 5, false);
     *
     * // Check if optional element has text
     * String notice = safeGetText(driver, By.className("notice"), 3, false);
     * if (!notice.isEmpty()) {
     *     System.out.println("Notice: " + notice);
     * }
     * }</pre>
     *
     * @param driver         The WebDriver instance
     * @param locator        The By locator for the element
     * @param timeout        Timeout in seconds to wait for element to be visible
     * @param elementPresent If true, throws exception if not found; if false,
     *                       returns "" if not found
     * @return The trimmed text content of the element, or "" if
     *         elementPresent=false and not found
     * @throws org.openqa.selenium.TimeoutException if element not found and
     *                                              elementPresent=true
     */
    public static String safeGetText(WebDriver driver, By locator, int timeout, boolean elementPresent) {
        try {
            WebElement element = waitForVisible(driver, locator, timeout);
            return element.getText().trim();
        } catch (TimeoutException e) {
            if (!elementPresent) {
                return "";
            }
            throw e;
        }
    }

    /**
     * Reloads the page, preferring an in-UI refresh button when provided.
     *
     * <p>
     * This method provides two ways to reload a page:
     * </p>
     * <ul>
     * <li>If refreshLocator is provided, tries to click the refresh button in the
     * UI</li>
     * <li>If refreshLocator is null or click fails, falls back to browser
     * refresh</li>
     * </ul>
     *
     * <p>
     * <b>Usage Examples:</b>
     * </p>
     * 
     * <pre>{@code
     * // Reload via browser refresh
     * reloadPage(driver, null);
     *
     * // Try UI refresh button first, fallback to browser refresh on failure
     * reloadPage(driver, By.id("refresh-btn"));
     *
     * // Try UI refresh button with specific locator
     * reloadPage(driver, By.cssSelector("button[aria-label='Refresh']"));
     * }</pre>
     *
     * @param driver         The WebDriver instance
     * @param refreshLocator Optional By locator for in-UI refresh button (null for
     *                       direct browser refresh)
     */
    public static void reloadPage(WebDriver driver, By refreshLocator) {
        if (refreshLocator != null) {
            try {
                clickElement(driver, refreshLocator, 10);
                logger.debug("Page reloaded via UI refresh button");
            } catch (Exception e) {
                logger.warn(
                        "Failed to click refresh element {}; falling back to driver.navigate().refresh(). Error: {}",
                        refreshLocator, e.getMessage());
                driver.navigate().refresh();
            }
        } else {
            driver.navigate().refresh();
        }
    }

    /**
     * Force kills browser processes (Windows-specific workaround).
     *
     * <p>
     * This is a last-resort cleanup method for when driver.quit() doesn't
     * properly
     * close browser processes on Windows. Use this in tearDown if browsers remain
     * open.
     * </p>
     *
     * <p>
     * <b>Warning:</b> This kills ALL Chrome/Firefox processes, not just test
     * browsers!
     * </p>
     *
     * @param browserType The browser type ("chrome", "firefox", "edge")
     */
    public static void forceKillBrowser(String browserType) {
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            logger.debug("forceKillBrowser only works on Windows, skipping");
            return;
        }

        String processName;
        switch (browserType.toLowerCase()) {
            case "chrome":
                processName = "chrome.exe";
                break;
            case "firefox":
                processName = "firefox.exe";
                break;
            case "edge":
                processName = "msedge.exe";
                break;
            default:
                logger.warn("Unknown browser type: {}", browserType);
                return;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM",
                    processName, "/T");
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            logger.debug("Force killed {} processes", processName);
        } catch (Exception e) {
            logger.debug("Could not force kill {}: {}", processName,
                    e.getMessage());
        }
    }

    // Private constructor to prevent instantiation
    private SeleniumUtils() {
        throw new AssertionError("SeleniumUtils should not be instantiated");
    }
}
