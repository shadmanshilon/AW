package com.allied.framework.pages.quotepage;

import com.allied.framework.pages.submissionspage.SubmissionDetail;
import com.allied.framework.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Navigation helper for Quote page.
 *
 * <p>This utility class provides helper methods to navigate from the Submission Detail page
 * to the Quote page.</p>
 *
 * <p>This is the Java equivalent of Python's quote_page/navigate_to_quote_01.py.</p>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // High-level preconditions (done by other setups / tests):
 * // - Login_Setup_01
 * // - Send_Attachment_For_Submission_Creation_Setup_01
 * // - Submission_List_Setup_01
 * // - Submission_List_02
 * // - Submission_Detail_02 / 04 / 05 / 12
 * // - Navigate_to_RiskDetails_01
 * // - Risk_Detail_* checks
 * // (etc. — all the earlier steps)
 *
 * // Navigate to quote page
 * NavigateToQuote01.navigateToQuote01(driver);
 * }</pre>
 *
 * @see Quote
 * @see SubmissionDetail
 * @see SeleniumUtils
 */
public class NavigateToQuote01 {

    private static final Logger logger = LoggerFactory.getLogger("ui.navigate_to_quote_01");

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private NavigateToQuote01() {
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    /**
     * Navigate to Quote page from Submission Detail / Risk context.
     *
     * <p><b>High-level preconditions (done by other setups / tests), e.g.:</b></p>
     * <ul>
     *   <li>Login_Setup_01</li>
     *   <li>Send_Attachment_For_Submission_Creation_Setup_01</li>
     *   <li>Submission_List_Setup_01</li>
     *   <li>Submission_List_02</li>
     *   <li>Submission_Detail_02 / 04 / 05 / 12</li>
     *   <li>Navigate_to_RiskDetails_01</li>
     *   <li>Risk_Detail_* checks</li>
     *   <li>(etc. — all the earlier steps you listed)</li>
     * </ul>
     *
     * <p><b>Step performed by this helper:</b></p>
     * <ul>
     *   <li>Click on the 'Quote' button on the top navigation bar
     *       and wait until the Quote page is loaded.</li>
     * </ul>
     *
     * @param driver the WebDriver instance
     */
    public static void navigateToQuote01(WebDriver driver) {
        logger.info("Navigating to Quote page from Submission Detail / Risk context");

        // Click top nav "Quote"
        SeleniumUtils.clickElement(driver, SubmissionDetail.TOP_NAV_QUOTE_BUTTON, 10);

        // Assert the Quote page root is visible
        SeleniumUtils.assertElementVisible(
            driver,
            Quote.QUOTE_PAGE_ROOT,
            15,
            "Quote page did not load after clicking Quote button"
        );

        logger.info("Successfully navigated to Quote page");
    }
}
