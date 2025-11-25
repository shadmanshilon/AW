package com.allied.framework.pages.submissionspage;

import com.allied.framework.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Submission validation helper for new submissions.
 *
 * <p>This utility class provides helper methods to validate that a new submission
 * was created successfully with expected values.</p>
 *
 * <p>This is the Java equivalent of Python's submissions_page/submission_list_setup_01.py.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Preconditions (handled elsewhere):
 * // 1. Login_Setup_01
 * // 2. Send_Attachment_For_Submission_Creation_Setup_01
 * // 3. We are already on the Submissions List page
 *
 * // Validate the new submission
 * Map<String, String> submissionData = SubmissionListSetup01.submissionListSetup01(driver);
 * String submissionId = submissionData.get("submission_id");
 * String status = submissionData.get("status");
 * String customer = submissionData.get("customer");
 * }</pre>
 *
 * @see SubmissionList
 * @see SeleniumUtils
 */
public class SubmissionListSetup01 {

    private static final Logger logger = LoggerFactory.getLogger("ui.submissions.submission_list_setup_01");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private SubmissionListSetup01() {
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    /**
     * Validates a new submission on the Submissions List page.
     *
     * <p><b>Expected values for the new submission:</b></p>
     * <ul>
     *   <li>Submission ID: generates by the system</li>
     *   <li>Status: "Intake Review"</li>
     *   <li>Channel: email documents -&gt; submission docs tab</li>
     *   <li>Customer: "Ascend Therapeutics US, LLC"</li>
     *   <li>Line of Business: "phD"</li>
     *   <li>Priority: "" (populates when we do Business Rules)</li>
     *   <li>Received: "" (first row usually latest submission)</li>
     *   <li>Last Modified: "" (TBD how backend populates)</li>
     * </ul>
     *
     * <p><b>Steps performed:</b></p>
     * <ol>
     *   <li>Reload the Submissions list page</li>
     *   <li>A new record should be shown showing the new submission</li>
     *   <li>The new record should have the expected values listed above</li>
     * </ol>
     *
     * @param driver the WebDriver instance
     * @return a Map containing all submission field values
     * @throws AssertionError if validation fails
     */
    public static Map<String, String> submissionListSetup01(WebDriver driver) {
        logger.info("Starting Submission_List_Setup_01");

        // Make sure we're on the list page and table is visible
        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_LIST_PAGE_ROOT, 15, "Submissions list page not loaded");
        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_TABLE, 15, "Submissions table not visible");

        // 1) Reload the list (prefer refresh button, fallback to driver.refresh)
        logger.debug("Reloading submissions list page via reloadPage()");
        SeleniumUtils.reloadPage(driver, SubmissionList.REFRESH_PAGE_BUTTON);

        // Wait for table to be visible again after reload
        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_TABLE, 15, "Submissions table not visible after reload");

        // 2) Read first/newest row cells
        String submissionId = SeleniumUtils.safeGetText(
            driver,
            SubmissionList.CELL_SUBMISSION_ID,
            10
        );
        String status = SeleniumUtils.safeGetText(
            driver,
            SubmissionList.CELL_STATUS,
            10
        );
        String channel = SeleniumUtils.safeGetText(
            driver,
            SubmissionList.CELL_CHANNEL,
            10
        );
        String customer = SeleniumUtils.safeGetText(
            driver,
            SubmissionList.CELL_CUSTOMER,
            10
        );
        String lob = SeleniumUtils.safeGetText(
            driver,
            SubmissionList.CELL_LINE_OF_BUSINESS,
            10
        );
        String priority = SeleniumUtils.safeGetText(driver, SubmissionList.CELL_PRIORITY, 10);
        String received = SeleniumUtils.safeGetText(driver, SubmissionList.CELL_RECEIVED, 10);
        String lastModified = SeleniumUtils.safeGetText(driver, SubmissionList.CELL_LAST_MODIFIED, 10);

        logger.debug(
            "Row values: id={} status={} channel={} customer={} lob={} priority={} received={} last_modified={}",
            submissionId, status, channel, customer, lob, priority, received, lastModified
        );

        // 3) Assertions (strict where requirement is clear)
        if (submissionId.isEmpty()) {
            throw new AssertionError("Expected a Submission ID in the first/newest row");
        }

        String expectedStatus = "Intake Review";
        if (!status.equals(expectedStatus)) {
            throw new AssertionError(
                String.format("Expected Status \"%s\", got \"%s\"", expectedStatus, status)
            );
        }

        // Channel: assert it mentions "email" to be flexible
        if (!channel.toLowerCase().contains("email")) {
            throw new AssertionError(
                String.format("Expected Channel to contain \"email\" (e.g. \"Email Documents\"), got \"%s\"",
                    channel)
            );
        }

        String expectedCustomer = "Ascend Therapeutics US, LLC";
        if (!customer.equals(expectedCustomer)) {
            throw new AssertionError(
                String.format("Expected Customer \"%s\", got \"%s\"", expectedCustomer, customer)
            );
        }

        // LOB "phD" (case-insensitive)
        if (!lob.equalsIgnoreCase("phd")) {
            throw new AssertionError(
                String.format("Expected Line of Business \"phD\", got \"%s\"", lob)
            );
        }

        // 4) Log soft expectations (no prints)
        if (!priority.isEmpty()) {
            logger.warn(
                "Priority was not blank: '{}' (may already be set by Business Rules)",
                priority
            );
        }
        if (!received.isEmpty()) {
            logger.warn(
                "Received was not blank: '{}' (backend may populate submission timestamp)",
                received
            );
        }
        if (!lastModified.isEmpty()) {
            logger.warn(
                "Last Modified was not blank: '{}' (backend may populate last-update timestamp)",
                lastModified
            );
        }

        logger.info("Submission_List_Setup_01 completed successfully");

        // Build and return the result map
        Map<String, String> result = new HashMap<>();
        result.put("submission_id", submissionId);
        result.put("status", status);
        result.put("channel", channel);
        result.put("customer", customer);
        result.put("line_of_business", lob);
        result.put("priority", priority);
        result.put("received", received);
        result.put("last_modified", lastModified);

        return result;
    }
}
