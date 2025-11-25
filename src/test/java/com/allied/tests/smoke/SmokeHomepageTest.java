package com.allied.tests.smoke;

import com.allied.framework.pages.loginpagedemo.OrangeHrmLogin;
import com.allied.tests.BaseTest;
import org.testng.annotations.Test;

/**
 * Smoke tests for OrangeHRM homepage.
 *
 * <p>Quick smoke tests to verify the application is accessible and basic
 * functionality works.</p>
 *
 * <p>This is the Java equivalent of Python's test_smoke_homepage.py.</p>
 *
 * @see BaseTest
 */
public class SmokeHomepageTest extends BaseTest {

    /**
     * Tests that OrangeHRM homepage is accessible.
     *
     * <p><b>Test Steps:</b></p>
     * <ol>
     *   <li>Navigate to OrangeHRM homepage</li>
     *   <li>Verify page title contains "OrangeHRM"</li>
     * </ol>
     *
     * <p><b>Expected Result:</b></p>
     * <ul>
     *   <li>Page loads successfully</li>
     *   <li>Page title is correct</li>
     * </ul>
     */
    @Test(groups = {"smoke"})
    public void testHomepageAccessible() {
        driver.get(OrangeHrmLogin.URL);
        assert driver.getTitle().contains("OrangeHRM") :
            "Homepage title should contain 'OrangeHRM'";
    }
}
