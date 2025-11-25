# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Selenium-based test automation framework built with Java 17 and TestNG. The framework uses a Page Object Model architecture with TestNG for test management and Maven for build management.

## Development Commands

### Package Management
```bash
# Install/update dependencies
mvn clean install -DskipTests

# Clean build
mvn clean

# Compile only
mvn compile
```

### Testing
```bash
# Run all tests
mvn test

# Run specific test groups
mvn test -Dgroups=smoke          # Smoke tests
mvn test -Dgroups=e2e            # End-to-end tests
mvn test -Dgroups=login          # Login tests
mvn test -Dgroups=dashboard      # Dashboard tests

# Run a single test class
mvn test -Dtest=LoginValidTest

# Run a specific test method
mvn test -Dtest=LoginValidTest#testOrangeHrmLoginHappyPath

# Run tests in parallel (configure in testng.xml)
mvn test
```

Test reports are generated in:
- `target/surefire-reports/index.html` - HTML report
- `target/surefire-reports/*.xml` - XML reports
- `logs/testng.log` - Test execution logs

### Code Quality
```bash
# Compile and check for errors
mvn compile

# Run all checks
mvn verify
```

## Architecture

### Core Components

**Driver Management** (`com.allied.framework.core.DriverFactory`)
- `createDriver()`: Creates WebDriver instances (Chrome, Firefox, Edge)
- Supports both local and remote (Selenium Grid) execution
- Configuration via environment variables (see `.env.example`)
- Uses Selenium Manager for automatic driver management

**Configuration** (`com.allied.framework.core/`)
- `DriverConfig.java`: WebDriver configuration object
- `Env.java`: Environment variable loading
- `Logger.java`: SLF4J logging wrapper

### Base Test Classes (`com.allied.tests/`)

The framework provides two main base test classes:

1. **`BaseTest`**: Basic WebDriver setup/teardown
   - Driver created in `@BeforeMethod`
   - Driver quit in `@AfterMethod`
   - Use for tests that need a fresh browser session

2. **`LoggedInBaseTest`**: Pre-authenticated WebDriver
   - Extends BaseTest
   - Automatically logs in before each test
   - Use for tests that require authentication

### Page Object Model

**Base Structure** (`com.allied.framework.pages.BasePage`)
- Contains common locators shared across pages (OK_BUTTON, CLOSE_BUTTON)
- Page objects extend this class
- Pages are organized in domain-specific packages (loginpagedemo, dashboardpage, etc.)

**Utility Functions** (`com.allied.framework.utils.SeleniumUtils`)
- `waitForVisible()`: Wait for element visibility
- `waitForClickable()`: Wait for element to be clickable
- `clickElement()`: Click with auto-scroll on interception
- `sendKeysToElement()`: Type text with optional clear
- `assertElementVisible()`: Assert element visibility
- `safeGetText()`: Safely extract text with timeout handling
- `reloadPage()`: Reload via UI button or browser refresh

These utilities are used throughout page objects and tests instead of raw Selenium calls.

### Test Organization

```
src/test/java/com/allied/tests/
├── BaseTest.java                 # Base test class
├── LoggedInBaseTest.java         # Pre-logged-in base test
├── e2e/                          # End-to-end scenarios
│   ├── login/
│   └── dashboard/
└── smoke/                        # Quick smoke tests
```

## Environment Variables

Browser and test configuration is controlled via environment variables (copy `.env.example` to `.env`):

```properties
# Application credentials
ORANGEHRM_BASE_URL=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
ORANGEHRM_USERNAME=Admin
ORANGEHRM_PASSWORD=admin123

# Browser configuration
BROWSER=chrome              # chrome | firefox | edge
HEADLESS=false              # true | false
WINDOW_WIDTH=1920
WINDOW_HEIGHT=1080
PAGE_LOAD_TIMEOUT=60
IMPLICIT_WAIT=0

# Remote execution (optional)
SELENIUM_REMOTE_URL=        # Set to Selenium Grid URL for remote execution

# Logging (optional)
LOG_LEVEL=INFO              # TRACE | DEBUG | INFO | WARN | ERROR
```

## Key Patterns

**Writing New Tests**
1. Extend the appropriate base class (`BaseTest` or `LoggedInBaseTest`)
2. Import page objects from `com.allied.framework.pages.*`
3. Import static utilities: `import static com.allied.framework.utils.SeleniumUtils.*;`
4. Use locators defined in page objects as `By` objects: `By.cssSelector(".class")`
5. Tag tests with groups using `@Test(groups = {"e2e", "login"})`

**Creating New Page Objects**
1. Create a package under `com.allied.framework.pages/` for your feature
2. Extend `BasePage`
3. Define locators as `public static final By` constants
4. Keep page objects focused on locators and URLs
5. Use `SeleniumUtils` functions for interactions, not raw Selenium commands
6. Use `WebDriver` and `By` types from Selenium

**Logger Usage**
- Import: `import com.allied.framework.core.Logger;`
- Use static methods: `Logger.info("message");`, `Logger.error("message");`
- Logging is configured in `src/main/resources/logback.xml`
- Logs go to console and `logs/testng.log`

**Example Test Structure**
```java
package com.allied.tests.e2e.login;

import com.allied.tests.BaseTest;
import org.testng.annotations.Test;
import static com.allied.framework.utils.SeleniumUtils.*;
import static com.allied.framework.pages.loginpagedemo.OrangeHrmLogin.*;

public class LoginTest extends BaseTest {

    @Test(groups = {"e2e", "login"})
    public void testValidLogin() {
        driver.get(URL);
        sendKeysToElement(driver, USERNAME_INPUT, Env.ORANGEHRM_USERNAME, 10, true);
        sendKeysToElement(driver, PASSWORD_INPUT, Env.ORANGEHRM_PASSWORD, 10, true);
        clickElement(driver, LOGIN_BUTTON, 10);
        assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_HEADING, 10, null);
    }
}
```
