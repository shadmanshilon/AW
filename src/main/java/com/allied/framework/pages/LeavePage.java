package com.allied.framework.pages;

import org.openqa.selenium.By;

public class LeavePage {

    // Menu navigation
    public static final By LEAVE_MENU1 = By.xpath("//span[text()='Leave']");
    public static final By LEAVE_LIST_SUBMENU = By.xpath("//a[contains(@href,'viewLeaveList')]");

    // Filters
    public static final By EMPLOYEE_NAME_INPUT = By.xpath("//input[@placeholder='Type for hints...']");
    public static final By FROM_DATE_INPUT = By.xpath("//label[text()='From Date']/following::input[1]");
    public static final By TO_DATE_INPUT = By.xpath("//label[text()='To Date']/following::input[1]");
    public static final By SEARCH_BUTTON = By.xpath("//button[normalize-space()='Search']");

    // Result table
    public static final By RESULT_TABLE = By.xpath("//div[@class='oxd-table-body']");
}
