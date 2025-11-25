# Allied Selenium TestNG Framework

This is a Java Selenium TestNG automation framework, converted from the Python pytest version. The framework uses a Page Object Model architecture with TestNG for test management and Maven for build management.

## Project Overview

- **Language**: Java 17
- **Build Tool**: Maven
- **Testing Framework**: TestNG
- **WebDriver**: Selenium 4.26.0
- **Logging**: SLF4J + Logback
- **Configuration**: dotenv-java (for .env file support)

## Prerequisites

- Java JDK 17 or higher
- Maven 3.6+ installed
- Chrome, Firefox, or Edge browser installed

## Project Structure

```
src/
├── main/java/com/allied/framework/
│   ├── core/
│   │   ├── DriverConfig.java         # WebDriver configuration
│   │   ├── DriverFactory.java        # WebDriver factory (creates browsers)
│   │   ├── Env.java                  # Environment variable loader
│   │   └── Logger.java               # Logging utilities
│   ├── utils/
│   │   └── SeleniumUtils.java        # Selenium utility functions
│   └── pages/
│       ├── BasePage.java             # Base page with common locators
│       ├── loginpagedemo/
│       │   ├── OrangeHrmLogin.java   # Login page object
│       │   └── LoginSetup01.java     # Login helper
│       └── dashboardpage/
│           └── OrangeHrmDashboard.java  # Dashboard page object
├── main/resources/
│   └── logback.xml                   # Logging configuration
└── test/java/com/allied/tests/
    ├── BaseTest.java                 # Base test class (driver setup/teardown)
    ├── LoggedInBaseTest.java         # Pre-logged-in base test
    ├── e2e/
    │   ├── login/
    │   │   └── LoginValidTest.java   # Login tests
    │   └── dashboard/
    │       └── DashboardWidgetsTest.java  # Dashboard tests
    └── smoke/
        └── SmokeHomepageTest.java    # Smoke tests
```

## Setup

### 1. Clone and Configure

```bash
# Copy the example .env file
cp .env.example .env

# Edit .env with your configuration (if needed)
# The defaults work with OrangeHRM demo site
nano .env
```

### 2. Install Dependencies

```bash
# Download all Maven dependencies
mvn clean install -DskipTests
```

## Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Groups

```bash
# Run smoke tests only
mvn test -Dgroups=smoke

# Run e2e tests only
mvn test -Dgroups=e2e

# Run login tests only
mvn test -Dgroups=login

# Run dashboard tests only
mvn test -Dgroups=dashboard
```

### Run Specific Test Class

```bash
mvn test -Dtest=LoginValidTest

mvn test -Dtest=DashboardWidgetsTest

mvn test -Dtest=SmokeHomepageTest
```

### Run Specific Test Method

```bash
mvn test -Dtest=LoginValidTest#testOrangeHrmLoginHappyPath
```

### Run Tests in Parallel

Edit `testng.xml` and add parallel execution:

```xml
<suite name="Allied Selenium TestNG Suite" parallel="tests" thread-count="4">
```

Then run:

```bash
mvn test
```

## Configuration

### Environment Variables

Configuration is controlled via `.env` file (or system environment variables):

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

### Logging

Logs are written to:
- **Console** - Colored output with timestamps
- **File** - `logs/testng.log` (rotated daily, max 10MB)

To change log level, set `LOG_LEVEL` environment variable or edit `src/main/resources/logback.xml`.

## Architecture

### Core Components

#### DriverFactory (`com.allied.framework.core.DriverFactory`)
- Creates WebDriver instances (Chrome, Firefox, Edge)
- Supports both local and remote (Selenium Grid) execution
- Uses Selenium Manager for automatic driver management

#### Configuration
- **DriverConfig.java** - WebDriver configuration object
- **Env.java** - Environment variable loading
- **Logger.java** - SLF4J logging wrapper

#### SeleniumUtils (`com.allied.framework.utils.SeleniumUtils`)
Utility functions for common Selenium operations:
- `waitForVisible()` - Wait for element visibility
- `waitForClickable()` - Wait for element to be clickable
- `clickElement()` - Click with auto-scroll on interception
- `sendKeysToElement()` - Type text with optional clear
- `assertElementVisible()` - Assert element visibility
- `safeGetText()` - Safely extract text with timeout handling
- `reloadPage()` - Reload via UI button or browser refresh

### Base Test Classes

#### BaseTest
- Provides basic WebDriver setup/teardown
- Use for tests that need a fresh browser session
- Driver is created in `@BeforeMethod`, quit in `@AfterMethod`

#### LoggedInBaseTest
- Extends BaseTest
- Automatically logs in before each test
- Use for tests that require authentication
- Driver is already on dashboard when test starts

### Page Object Model

#### BasePage
- Contains common locators shared across pages
- No driver instance, no action methods
- Page objects extend this class

#### Page Objects
- Contain URL constants and locators only
- No driver instance, no action methods
- Locators defined as `public static final By`
- Use SeleniumUtils for interactions

Example:
```java
public class OrangeHrmLogin extends BasePage {
    public static final String URL = Env.ORANGEHRM_BASE_URL;
    public static final By USERNAME_INPUT = By.name("username");
    public static final By PASSWORD_INPUT = By.name("password");
    public static final By LOGIN_BUTTON = By.cssSelector("button.orangehrm-login-button");
}
```

### Writing Tests

#### Basic Test Example

```java
import static com.allied.framework.utils.SeleniumUtils.*;

public class LoginTest extends BaseTest {

    @Test(groups = {"e2e", "login"})
    public void testValidLogin() {
        // Navigate to login page
        driver.get(OrangeHrmLogin.URL);

        // Perform login
        sendKeysToElement(driver, OrangeHrmLogin.USERNAME_INPUT, Env.ORANGEHRM_USERNAME, 10, true);
        sendKeysToElement(driver, OrangeHrmLogin.PASSWORD_INPUT, Env.ORANGEHRM_PASSWORD, 10, true);
        clickElement(driver, OrangeHrmLogin.LOGIN_BUTTON, 10);

        // Verify dashboard
        assertElementVisible(driver, OrangeHrmDashboard.DASHBOARD_HEADING, 10, null);
    }
}
```

#### Pre-Logged-In Test Example

```java
public class DashboardTest extends LoggedInBaseTest {

    @Test(groups = {"e2e", "dashboard"})
    public void testDashboardWidgets() {
        // Driver is already logged in and on dashboard
        List<WebElement> widgets = driver.findElements(OrangeHrmDashboard.DASHBOARD_WIDGET);
        assert widgets.size() == 7 : "Expected 7 dashboard widgets";
    }
}
```

### Test Groups

Tests can be tagged with groups using `@Test(groups = {...})`:

- **smoke** - Quick smoke tests for basic functionality
- **e2e** - End-to-end tests for complete user flows
- **login** - Login-related tests
- **dashboard** - Dashboard-related tests

Run specific groups:
```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=e2e
```

## Test Reports

TestNG generates test reports in:
- `target/surefire-reports/index.html` - HTML report
- `target/surefire-reports/*.xml` - XML reports

## IDE Setup

### IntelliJ IDEA

1. Open project: `File > Open` → Select `pom.xml`
2. Maven will auto-import dependencies
3. Right-click test class → `Run 'TestName'`
4. Or use Maven tool window → `Lifecycle > test`

### VS Code

1. Install extensions:
   - Java Extension Pack
   - Maven for Java
   - TestNG for Java
2. Open project folder
3. Run tests via Testing sidebar or Maven commands

## Troubleshooting

### Tests fail with "Missing required environment variable"
- Ensure `.env` file exists in project root
- Copy from `.env.example`: `cp .env.example .env`

### WebDriver not found / Browser not launching
- Selenium Manager handles driver download automatically
- Ensure browser (Chrome/Firefox/Edge) is installed
- Check `BROWSER` setting in `.env`

### Tests timeout
- Increase `PAGE_LOAD_TIMEOUT` in `.env`
- Check network connection to test site
- Try running with `HEADLESS=false` to see what's happening

### Import errors in IDE
- Run `mvn clean install` to download dependencies
- In IntelliJ: `File > Invalidate Caches > Invalidate and Restart`
- In VS Code: Reload window or reinstall Java extensions

## Comparison with Python Version

| Python (pytest) | Java (TestNG) |
|----------------|---------------|
| `conftest.py` fixtures | `@BeforeMethod` / `@AfterMethod` |
| `@pytest.mark.e2e` | `@Test(groups = {"e2e"})` |
| pytest CLI args | Maven `-D` parameters |
| `python -m pytest` | `mvn test` |
| tuple locators `(By.ID, "id")` | By objects `By.id("id")` |
| Python functions | Static utility methods |

## Next Steps

1. Add more page objects for your application pages
2. Add more test classes for different features
3. Configure CI/CD pipeline (GitHub Actions, Jenkins, etc.)
4. Add screenshot capture on test failure
5. Integrate with test reporting tools (Allure, ExtentReports)

## Contributing

When adding new tests or page objects:
1. Follow the existing structure and naming conventions
2. Use SeleniumUtils for Selenium operations
3. Add comprehensive Javadoc comments
4. Tag tests with appropriate groups
5. Ensure tests clean up resources (driver.quit())

## License

See LICENSE file for details.
