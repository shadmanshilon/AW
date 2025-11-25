package com.allied.framework.pages.riskdetailspage;

import com.allied.framework.pages.submissionspage.SubmissionDetail;
import com.allied.framework.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Navigation helper for Risk Details page.
 *
 * <p>This utility class provides helper methods to navigate from the Submission Detail page
 * to the Risk Details page.</p>
 *
 * <p>This is the Java equivalent of Python's risk_details_page/navigate_to_risk_details_01.py.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // Preconditions (handled by other helpers / tests):
 * // 1. Login_Setup_01
 * // 2. Send_Attachment_For_Submission_Creation_Setup_01
 * // 3. Submission_List_Setup_01
 * // 4. Submission_List_02
 *
 * // Navigate to risk details page
 * NavigateToRiskDetails01.navigateToRiskDetails01(driver);
 * }</pre>
 *
 * @see RiskDetails
 * @see SubmissionDetail
 * @see SeleniumUtils
 */
public class NavigateToRiskDetails01 {

    private static final Logger logger = LoggerFactory.getLogger("ui.navigate_to_risk_details_01");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private NavigateToRiskDetails01() {
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    /**
     * Navigate to Risk Details from Submission Detail page.
     *
     * <p><b>Preconditions (handled by other helpers / tests):</b></p>
     * <ol>
     *   <li>Login_Setup_01</li>
     *   <li>Send_Attachment_For_Submission_Creation_Setup_01</li>
     *   <li>Submission_List_Setup_01</li>
     *   <li>Submission_List_02</li>
     * </ol>
     *
     * <p><b>Step performed by this helper:</b></p>
     * <ol start="5">
     *   <li>Click on the 'Risk Details' button on the top navigation bar
     *       and wait until the Risk Details page is loaded.</li>
     * </ol>
     *
     * @param driver the WebDriver instance
     */
    public static void navigateToRiskDetails01(WebDriver driver) {
        logger.info("Navigating to Risk Details from Submission Detail page");

        // Click top nav "Risk Details"
        SeleniumUtils.clickElement(driver, SubmissionDetail.TOP_NAV_RISK_DETAILS_BUTTON, 10);

        // Assert Risk Details view is loaded (root of Risk Details page)
        SeleniumUtils.assertElementVisible(
            driver,
            RiskDetails.RISK_DETAILS_PAGE_ROOT,
            15,
            "Risk Details page did not load after clicking Risk Details button"
        );

        logger.info("Successfully navigated to Risk Details page");
    }
}
