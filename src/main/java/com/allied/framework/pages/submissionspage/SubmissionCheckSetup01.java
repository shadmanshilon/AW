package com.allied.framework.pages.submissionspage;

import com.allied.framework.utils.SeleniumUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Submission validation helper for failed submissions.
 *
 * <p>This utility class provides helper methods to validate that a submission
 * was created with "Failed to create" status.</p>
 *
 * <p>This is the Java equivalent of Python's submissions_page/submission_check_setup_01.py.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Preconditions (handled elsewhere):
 * // 1. Login_Setup_01
 * // 2. Send_Attachment_For_Submission_Creation_Setup_01
 * // 3. We are already on the Submissions List page
 * //    after creating a submission expected to fail
 *
 * // Validate the failed submission
 * Map<String, String> submissionData = SubmissionCheckSetup01.submissionCheckSetup01(driver);
 * String submissionId = submissionData.get("submission_id");
 * String status = submissionData.get("status");
 * }</pre>
 *
 * @see SubmissionList
 * @see SeleniumUtils
 */
public class SubmissionCheckSetup01 {

    private static final Logger logger = LoggerFactory.getLogger("ui.submission_check_setup_01");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private SubmissionCheckSetup01() {
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    /**
     * Helper method to safely get cell text or return empty string if not found.
     *
     * @param driver  the WebDriver instance
     * @param locator the element locator
     * @return the cell text or empty string
     */
    private static String safeCellText(WebDriver driver, org.openqa.selenium.By locator) {
        try {
            return driver.findElement(locator).getText().strip();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Validates a failed submission on the Submissions List page.
     *
     * <p><b>Preconditions (done elsewhere):</b></p>
     * <ol>
     *   <li>Login_Setup_01</li>
     *   <li>Send_Attachment_For_Submission_Creation_Setup_01</li>
     *   <li>We are already on the Submissions List page
     *       after creating a submission expected to fail.</li>
     * </ol>
     *
     * <p><b>This helper:</b></p>
     * <ul>
     *   <li>(Optionally) refreshes the list via the page's refresh button</li>
     *   <li>Reads the first/newest row</li>
     *   <li>Asserts that its Status is "Failed to create"</li>
     *   <li>Returns the captured values for logging or further checks</li>
     * </ul>
     *
     * @param driver the WebDriver instance
     * @return a Map containing submission_id, status, channel, and customer values
     * @throws AssertionError if validation fails
     */
    public static Map<String, String> submissionCheckSetup01(WebDriver driver) {
        logger.info("Starting Submission_check_setup_01");

        // Ensure table is visible
        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_TABLE, 15, "Submissions table not visible");

        // Try to reload via in-page refresh button; fall back to browser refresh
        try {
            logger.debug("Clicking Submissions list refresh button");
            SeleniumUtils.clickElement(driver, SubmissionList.REFRESH_PAGE_BUTTON, 10);
        } catch (Exception e) {
            logger.debug("Refresh button click failed; falling back to driver.refresh()");
            driver.navigate().refresh();
        }

        // Wait again for table after reload
        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_TABLE, 15, "Submissions table not visible after refresh");

        // Read the first/newest row cells
        String submissionId = safeCellText(driver, SubmissionList.CELL_SUBMISSION_ID);
        String status = safeCellText(driver, SubmissionList.CELL_STATUS);
        String channel = safeCellText(driver, SubmissionList.CELL_CHANNEL);
        String customer = safeCellText(driver, SubmissionList.CELL_CUSTOMER);

        logger.debug(
            "Top row after failed submission: id={} status={} channel={} customer={}",
            submissionId,
            status,
            channel,
            customer
        );

        // Basic assertions for the failed submission case
        if (submissionId.isEmpty()) {
            throw new AssertionError("Expected a Submission ID in the first/newest row");
        }

        String expectedStatus = "Failed to create";
        if (!status.equals(expectedStatus)) {
            throw new AssertionError(
                String.format("Expected Status \"%s\" for failed submission, got \"%s\"",
                    expectedStatus, status)
            );
        }

        logger.info("Submission_check_setup_01 completed successfully");

        // Build and return the result map
        Map<String, String> result = new HashMap<>();
        result.put("submission_id", submissionId);
        result.put("status", status);
        result.put("channel", channel);
        result.put("customer", customer);

        return result;
    }
}
