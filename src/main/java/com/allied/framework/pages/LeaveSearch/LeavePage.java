package com.allied.framework.pages.LeaveSearch;

import org.openqa.selenium.By;

public class LeavePage {

    // Menu navigation
    public static final By LEAVE_MENU1 = By.xpath("//span[text()='Leave']");
    public static final By LEAVE_LIST_SUBMENU = By.xpath("//a[contains(@href,'viewLeaveList')]");

    // Filters
    public static final By EMPLOYEE_NAME_INPUT = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div.oxd-table-filter > div.oxd-table-filter-area > form > div:nth-child(2) > div > div:nth-child(1) > div > div:nth-child(2) > div > div > input");
    public static final By FROM_DATE_INPUT = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div.oxd-table-filter > div.oxd-table-filter-area > form > div:nth-child(1) > div > div:nth-child(1) > div > div:nth-child(2) > div > div > input");
    public static final By SELECT_STATUS = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div.oxd-table-filter > div.oxd-table-filter-area > form > div:nth-child(1) > div > div:nth-child(3) > div > div:nth-child(2) > div > div.oxd-select-text.oxd-select-text--active > div.oxd-select-text-input");
    public static final By SEARCH_BUTTON = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div.oxd-table-filter > div.oxd-table-filter-area > form > div.oxd-form-actions > button.oxd-button.oxd-button--medium.oxd-button--secondary.orangehrm-left-space");

    // Result table
    public static final By RESULT_TABLE = By.xpath("//div[@class='oxd-table-body']");
}
