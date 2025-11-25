package com.allied.framework.pages.submissionspage;

import com.allied.framework.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Submission validation helper for failed submissions (variant 2).
 *
 * <p>This utility class provides helper methods to validate that a submission
 * was created with "Failed to create" status. This is similar to SubmissionCheckSetup01
 * but uses a different logging namespace.</p>
 *
 * <p>This is the Java equivalent of Python's submissions_page/submission_list_setup_02.py.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Preconditions (handled elsewhere):
 * // 1. Login_Setup_01
 * // 2. Send_Attachment_For_Submission_Creation_Setup_01
 * // 3. We are already on the Submissions List page
 *
 * // Validate the failed submission
 * Map<String, String> submissionData = SubmissionListSetup02.submissionListSetup02(driver);
 * String submissionId = submissionData.get("submission_id");
 * String status = submissionData.get("status");
 * }</pre>
 *
 * @see SubmissionList
 * @see SeleniumUtils
 */
public class SubmissionListSetup02 {

    private static final Logger logger = LoggerFactory.getLogger("ui.submissions.submission_list_setup_02");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private SubmissionListSetup02() {
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    /**
     * Validates a failed submission on the Submissions List page.
     *
     * <p><b>Expected values for the failed submission:</b></p>
     * <ul>
     *   <li>Submission ID: generates by the system</li>
     *   <li>Status: "Failed to create"</li>
     * </ul>
     *
     * <p><b>Steps performed:</b></p>
     * <ol>
     *   <li>Reload the Submissions list page</li>
     *   <li>A new record should be shown showing the new submission</li>
     *   <li>The new record should have Status = "Failed to create"</li>
     * </ol>
     *
     * @param driver the WebDriver instance
     * @return a Map containing submission_id and status values
     * @throws AssertionError if validation fails
     */
    public static Map<String, String> submissionListSetup02(WebDriver driver) {
        logger.info("Starting Submission_List_Setup_02");

        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_LIST_PAGE_ROOT, 15, "Submissions list page not loaded");
        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_TABLE, 15, "Submissions table not visible");

        logger.debug("Reloading submissions list page via reloadPage()");
        SeleniumUtils.reloadPage(driver, SubmissionList.REFRESH_PAGE_BUTTON);

        SeleniumUtils.assertElementVisible(driver, SubmissionList.SUBMISSIONS_TABLE, 15, "Submissions table not visible after reload");

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

        logger.debug(
            "Row values (failed create): id={} status={}",
            submissionId,
            status
        );

        if (submissionId.isEmpty()) {
            throw new AssertionError("Expected a Submission ID in the first/newest row");
        }

        String expectedStatus = "Failed to create";
        if (!status.equals(expectedStatus)) {
            throw new AssertionError(
                String.format("Expected Status \"%s\", got \"%s\"", expectedStatus, status)
            );
        }

        logger.info(
            "Submission_List_Setup_02 validated submission_id={} status={}",
            submissionId,
            status
        );

        // Build and return the result map
        Map<String, String> result = new HashMap<>();
        result.put("submission_id", submissionId);
        result.put("status", status);

        return result;
    }
}
