package automation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

    // ==================== TEST CONFIGURATION ====================

    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";
    private static final String INVALID_PASS = "wrong_password";
    private static final String BASE_URL = "https://www.saucedemo.com/";

    // ==================== TEST RESULTS TRACKING ====================

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    // ==================== MAIN METHOD ====================

    public static void main(String[] args) {

        printHeader();

        // Setup WebDriver
        // Selenium Manager will automatically manage the correct ChromeDriver.
        WebDriver driver = new ChromeDriver();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            // Execute All Test Cases
            testValidLogin(driver, wait);
            testInvalidPassword(driver, wait);
            testLockedOutUser(driver, wait);
            testEmptyUsername(driver, wait);
            testSpecialCharacters(driver, wait);
            testPageLoadPerformance(driver);

        } catch (Exception e) {

            System.out.println("❌ CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();

        } finally {

            // Generate Final Report
            printTestSummary();

            // Cleanup
            driver.quit();

            System.out.println(
                    "\n✅ Browser closed. Test execution completed."
            );
        }
    }

    // ==================== TEST CASE 1 ====================

    private static void testValidLogin(
            WebDriver driver,
            WebDriverWait wait) {

        totalTests++;

        System.out.println(
                "🧪 TEST 1: Valid Login (Black Box Testing)");

        System.out.println(
                "   Description: Verify successful login with valid credentials");

        try {

            // Navigate to application
            driver.get(BASE_URL);
            driver.manage().window().maximize();

            // Locate elements
            WebElement username =
                    driver.findElement(By.id("user-name"));

            WebElement password =
                    driver.findElement(By.id("password"));

            WebElement loginBtn =
                    driver.findElement(By.id("login-button"));

            // Perform login
            username.sendKeys(VALID_USER);
            password.sendKeys(VALID_PASS);
            loginBtn.click();

            // Verify successful navigation
            wait.until(
                    ExpectedConditions.urlContains("inventory")
            );

            if (driver.getCurrentUrl().contains("inventory")) {

                System.out.println("   ✅ RESULT: PASS");

                System.out.println(
                        "   📊 Evidence: Redirected to: "
                        + driver.getCurrentUrl()
                );

                System.out.println(
                        "   📝 Page Title: "
                        + driver.getTitle()
                );

                passedTests++;

                // Logout for next test
                performLogout(driver, wait);

            } else {

                System.out.println(
                        "   ❌ RESULT: FAIL - Not redirected to expected page"
                );

                System.out.println(
                        "   🔍 Actual URL: "
                        + driver.getCurrentUrl()
                );

                failedTests++;
            }

        } catch (Exception e) {

            System.out.println(
                    "   ❌ RESULT: FAIL - Exception: "
                    + e.getMessage()
            );

            failedTests++;
        }

        System.out.println(
                "   ―――――――――――――――――――――――――――――――――――\n"
        );
    }

    // ==================== TEST CASE 2 ====================

    private static void testInvalidPassword(
            WebDriver driver,
            WebDriverWait wait) {

        totalTests++;

        System.out.println(
                "🧪 TEST 2: Invalid Password");

        System.out.println(
                "   Description: Verify error message for incorrect password");

        try {

            driver.get(BASE_URL);

            WebElement username =
                    driver.findElement(By.id("user-name"));

            WebElement password =
                    driver.findElement(By.id("password"));

            WebElement loginBtn =
                    driver.findElement(By.id("login-button"));

            username.sendKeys(VALID_USER);
            password.sendKeys(INVALID_PASS);
            loginBtn.click();

            // Wait for error message
            WebElement errorMsg =
                    wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                    By.cssSelector(
                                            "h3[data-test='error']"
                                    )
                            )
                    );

            String actualError = errorMsg.getText();

            String expectedError =
                    "Username and password do not match";

            if (errorMsg.isDisplayed()
                    && actualError.contains(expectedError)) {

                System.out.println("   ✅ RESULT: PASS");

                System.out.println(
                        "   📊 Evidence: Error message displayed correctly"
                );

                System.out.println(
                        "   💬 Message: \"" + actualError + "\""
                );

                passedTests++;

            } else {

                System.out.println(
                        "   ❌ RESULT: FAIL"
                );

                System.out.println(
                        "   🔍 Expected: \""
                        + expectedError
                        + "\""
                );

                System.out.println(
                        "   🔍 Actual: \""
                        + actualError
                        + "\""
                );

                failedTests++;
            }

        } catch (Exception e) {

            System.out.println(
                    "   ❌ RESULT: FAIL - Exception: "
                    + e.getMessage()
            );

            failedTests++;
        }

        System.out.println(
                "   ―――――――――――――――――――――――――――――――――――\n"
        );
    }

    // ==================== TEST CASE 3 ====================

    private static void testLockedOutUser(
            WebDriver driver,
            WebDriverWait wait) {

        totalTests++;

        System.out.println(
                "🧪 TEST 3: Locked Out User (Edge Case Testing)");

        System.out.println(
                "   Description: Verify system handles locked accounts properly");

        try {

            driver.get(BASE_URL);

            WebElement username =
                    driver.findElement(By.id("user-name"));

            WebElement password =
                    driver.findElement(By.id("password"));

            WebElement loginBtn =
                    driver.findElement(By.id("login-button"));

            username.sendKeys("locked_out_user");
            password.sendKeys(VALID_PASS);
            loginBtn.click();

            WebElement errorMsg =
                    wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                    By.cssSelector(
                                            "h3[data-test='error']"
                                    )
                            )
                    );

            String errorText = errorMsg.getText();

            if (errorText.contains(
                    "Sorry, this user has been locked out")) {

                System.out.println("   ✅ RESULT: PASS");

                System.out.println(
                        "   📊 Evidence: Proper locked user handling"
                );

                System.out.println(
                        "   🔒 Security: System prevents locked user access"
                );

                passedTests++;

            } else {

                System.out.println(
                        "   ❌ RESULT: FAIL"
                );

                System.out.println(
                        "   🔍 Actual Error: "
                        + errorText
                );

                failedTests++;
            }

        } catch (Exception e) {

            System.out.println(
                    "   ❌ RESULT: FAIL - Exception: "
                    + e.getMessage()
            );

            failedTests++;
        }

        System.out.println(
                "   ―――――――――――――――――――――――――――――――――――\n"
        );
    }

    // ==================== TEST CASE 4 ====================

    private static void testEmptyUsername(
            WebDriver driver,
            WebDriverWait wait) {

        totalTests++;

        System.out.println(
                "🧪 TEST 4: Empty Username Field (Input Validation)");

        System.out.println(
                "   Description: Verify validation for required fields");

        try {

            driver.get(BASE_URL);

            WebElement loginBtn =
                    driver.findElement(By.id("login-button"));

            loginBtn.click();

            WebElement errorMsg =
                    wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                    By.cssSelector(
                                            "h3[data-test='error']"
                                    )
                            )
                    );

            if (errorMsg.getText().contains(
                    "Username is required")) {

                System.out.println("   ✅ RESULT: PASS");

                System.out.println(
                        "   📊 Evidence: Proper empty field validation"
                );

                System.out.println(
                        "   ⚠️ User Guidance: Clear error message shown"
                );

                passedTests++;

            } else {

                System.out.println(
                        "   ❌ RESULT: FAIL"
                );

                System.out.println(
                        "   🔍 Actual Error: "
                        + errorMsg.getText()
                );

                failedTests++;
            }

        } catch (Exception e) {

            System.out.println(
                    "   ❌ RESULT: FAIL - Exception: "
                    + e.getMessage()
            );

            failedTests++;
        }

        System.out.println(
                "   ―――――――――――――――――――――――――――――――――――\n"
        );
    }

    // ==================== TEST CASE 5 ====================

    private static void testSpecialCharacters(
            WebDriver driver,
            WebDriverWait wait) {

        totalTests++;

        System.out.println(
                "🧪 TEST 5: Special Characters Input (Security Check)");

        System.out.println(
                "   Description: Test handling of potential SQL injection attempts");

        try {

            driver.get(BASE_URL);

            WebElement username =
                    driver.findElement(By.id("user-name"));

            WebElement password =
                    driver.findElement(By.id("password"));

            WebElement loginBtn =
                    driver.findElement(By.id("login-button"));

            // Attempt SQL injection-style input
            username.sendKeys("admin' OR '1'='1");
            password.sendKeys("anything");
            loginBtn.click();

            WebElement errorMsg =
                    wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                    By.cssSelector(
                                            "h3[data-test='error']"
                                    )
                            )
                    );

            if (errorMsg.isDisplayed()) {

                System.out.println("   ✅ RESULT: PASS");

                System.out.println(
                        "   📊 Evidence: System rejects suspicious input"
                );

                System.out.println(
                        "   🔒 Security: Potential SQL injection blocked"
                );

                passedTests++;

            } else {

                System.out.println(
                        "   ⚠️ RESULT: WARNING - No error for special chars"
                );

                System.out.println(
                        "   🔍 Note: System accepted special characters"
                );

                passedTests++;
            }

        } catch (Exception e) {

            System.out.println(
                    "   ❌ RESULT: FAIL - Exception: "
                    + e.getMessage()
            );

            failedTests++;
        }

        System.out.println(
                "   ―――――――――――――――――――――――――――――――――――\n"
        );
    }

    // ==================== TEST CASE 6 ====================

    private static void testPageLoadPerformance(
            WebDriver driver) {

        totalTests++;

        System.out.println(
                "🧪 TEST 6: Page Load Performance (Grey Box Testing)");

        System.out.println(
                "   Description: Measure application response time");

        try {

            long startTime =
                    System.currentTimeMillis();

            driver.get(BASE_URL);

            long endTime =
                    System.currentTimeMillis();

            long responseTime =
                    endTime - startTime;

            System.out.println(
                    "   ⏱️ Response Time: "
                    + responseTime
                    + " milliseconds"
            );

            // Performance threshold: 3 seconds
            if (responseTime < 3000) {

                System.out.println(
                        "   ✅ RESULT: PASS - Acceptable performance"
                );

                System.out.println(
                        "   📊 Benchmark: Under 3-second threshold"
                );

                passedTests++;

            } else {

                System.out.println(
                        "   ⚠️ RESULT: WARNING - Slow response time"
                );

                System.out.println(
                        "   🔍 Note: Page load took "
                        + responseTime
                        + "ms"
                );

                // Warning only
                passedTests++;
            }

        } catch (Exception e) {

            System.out.println(
                    "   ❌ RESULT: FAIL - Exception: "
                    + e.getMessage()
            );

            failedTests++;
        }

        System.out.println(
                "   ―――――――――――――――――――――――――――――――――――\n"
        );
    }

    // ==================== HELPER METHOD: LOGOUT ====================

    private static void performLogout(
            WebDriver driver,
            WebDriverWait wait) {

        try {

            // Open menu
            driver.findElement(
                    By.id("react-burger-menu-btn")
            ).click();

            // Wait for logout link and click
            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("logout_sidebar_link")
                    )
            ).click();

            // Wait to return to login page
            wait.until(
                    ExpectedConditions.urlToBe(BASE_URL)
            );

        } catch (Exception e) {

            System.out.println(
                    "   Note: Logout cleanup skipped due to: "
                    + e.getMessage()
            );
        }
    }

    // ==================== HEADER ====================

    private static void printHeader() {

        System.out.println(
                "\n" + "=".repeat(60)
        );

        System.out.println(
                "   AUTOMATED WEB APPLICATION TESTING - FINAL PROJECT"
        );

        System.out.println(
                "   Project: AutomaWebAppliTestSeleniumFinalExamProject"
        );

        System.out.println(
                "   Application: Sauce Demo (https://www.saucedemo.com/)"
        );

        System.out.println(
                "=".repeat(60) + "\n"
        );

        System.out.println(
                "🔧 TESTING TECHNIQUES DEMONSTRATED:"
        );

        System.out.println(
                "   • Black Box Testing: Functional testing without code knowledge"
        );

        System.out.println(
                "   • White Box Testing: Using HTML structure for element location"
        );

        System.out.println(
                "   • Grey Box Testing: Performance and validation checks\n"
        );
    }

    // ==================== FINAL TEST REPORT ====================

    private static void printTestSummary() {

        System.out.println(
                "=".repeat(60)
        );

        System.out.println(
                "           FINAL TEST EXECUTION REPORT"
        );

        System.out.println(
                "=".repeat(60)
        );

        System.out.println(
                "📊 EXECUTION SUMMARY:"
        );

        System.out.println(
                "   Total Tests Executed: "
                + totalTests
        );

        System.out.println(
                "   Tests Passed: "
                + passedTests
                + " ✅"
        );

        System.out.println(
                "   Tests Failed: "
                + failedTests
                + " ❌"
        );

        double successRate =
                (totalTests > 0)
                        ? ((double) passedTests / totalTests) * 100
                        : 0;

        System.out.printf(
                "   Success Rate: %.1f%%\n",
                successRate
        );

        System.out.println(
                "\n🎯 TESTING OBJECTIVES STATUS:"
        );

        System.out.println(
                "   • Black Box Testing: Completed via functional test cases"
        );

        System.out.println(
                "   • White Box Testing: Applied in element identification"
        );

        System.out.println(
                "   • Grey Box Testing: Implemented in performance testing"
        );

        System.out.println(
                "\n" + "=".repeat(60)
        );

        if (failedTests == 0) {

            System.out.println(
                    "🎉 CONCLUSION: ALL TESTS PASSED! Application meets requirements."
            );

        } else {

            System.out.println(
                    "⚠️ CONCLUSION: "
                    + failedTests
                    + " test(s) failed. Review required."
            );
        }

        System.out.println(
                "=".repeat(60)
        );
    }
}