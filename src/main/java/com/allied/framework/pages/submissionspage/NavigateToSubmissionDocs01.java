package com.allied.framework.pages.submissionspage;

import com.allied.framework.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Navigation helper for Submission Documents.
 *
 * <p>This utility class provides helper methods to navigate from the Submission Detail page
 * to the Submission Documents tab.</p>
 *
 * <p>This is the Java equivalent of Python's submissions_page/navigate_to_submission_docs_01.py.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Preconditions (handled elsewhere):
 * // 1. Login_Setup_01
 * // 2. Send_Attachment_For_Submission_Creation_Setup_01
 * // 3. Submission_List_Setup_01
 * // 4. Submission_List_02
 *
 * // Navigate to submission documents
 * NavigateToSubmissionDocs01.navigateToSubmissionDocs01(driver);
 * }</pre>
 *
 * @see SubmissionDetail
 * @see SeleniumUtils
 */
public class NavigateToSubmissionDocs01 {

    private static final Logger logger = LoggerFactory.getLogger("ui.navigate_to_submission_docs_01");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private NavigateToSubmissionDocs01() {
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    /**
     * Navigate to Submission Docs from Submission Detail page.
     *
     * <p><b>Preconditions (handled elsewhere):</b></p>
     * <ol>
     *   <li>Login_Setup_01</li>
     *   <li>Send_Attachment_For_Submission_Creation_Setup_01</li>
     *   <li>Submission_List_Setup_01</li>
     *   <li>Submission_List_02</li>
     * </ol>
     *
     * <p><b>Step performed by this helper:</b></p>
     * <ol start="5">
     *   <li>Click on the 'Submission Docs' button on the top navigation bar
     *       and wait until the submission docs table is visible.</li>
     * </ol>
     *
     * @param driver the WebDriver instance
     */
    public static void navigateToSubmissionDocs01(WebDriver driver) {
        logger.info("Navigating to Submission Docs from Submission Detail page");

        // Click top nav "Submission Docs" / "Documents"
        SeleniumUtils.clickElement(driver, SubmissionDetail.TOP_NAV_DOCUMENTS_TAB, 10);

        // Assert the docs table is present
        SeleniumUtils.assertElementVisible(
            driver,
            SubmissionDetail.SUBMISSION_DOCS_TABLE,
            15,
            "Submission Documents table not visible after clicking Documents tab"
        );

        logger.info("Successfully navigated to Submission Docs tab");
    }
}
