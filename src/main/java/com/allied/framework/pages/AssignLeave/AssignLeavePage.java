package com.allied.framework.pages.AssignLeave;
import org.openqa.selenium.By;

public class AssignLeavePage {
    // Navigation
    public static final By LEAVE_MENU = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-navigation > aside > nav > div.oxd-sidepanel-body > ul > li:nth-child(3) > a");
    public static final By ASSIGN_LEAVE = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-navigation > header > div.oxd-topbar-body > nav > ul > li:nth-child(7)");
    // Thread.sleep(5000);
    // Form fields
    public static final By EMPLOYEE_NAME_INPUT = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(1) > div > div > div > div:nth-child(2) > div > div > input");
    
    public static final By EMPOLYEE_CLICK = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(1) > div > div > div > div:nth-child(2) > div > div > div.oxd-autocomplete-text-input--after");
    

    public static final By LEAVE_TYPE_SELECT = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(2) > div > div:nth-child(1) > div > div:nth-child(2) > div > div > div.oxd-select-text-input");

    
    public static final By LEAVE_TYPE_OPTION = By.xpath("//div[@role='option']"); // generic option list


    // public static final By ICON_TO = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(3) > div > div:nth-child(1) > div > div:nth-child(2) > div > div > i");

    public static final By FROM_DATE_INPUT = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(3) > div > div:nth-child(1) > div > div:nth-child(2) > div > div > input");
    
    
    public static final By TO_DATE_INPUT   = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(3) > div > div:nth-child(2) > div > div:nth-child(2) > div > div > input");

    public static final By CLICK = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(4) > div");

    public static final By DURATION_SELECT  = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(4) > div > div:nth-child(1) > div > div:nth-child(2) > div > div");
    public static final By DURATION_OPTION  = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(4) > div > div:nth-child(2) > div > div:nth-child(2) > div > div > div.oxd-select-text-input"); // generic

    public static final By COMMENT_TEXTAREA  = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div:nth-child(5) > div > div > div > div:nth-child(2) > textarea");

    public static final By ASSIGN_BUTTON    = By.cssSelector("#app > div.oxd-layout.orangehrm-upgrade-layout > div.oxd-layout-container > div.oxd-layout-context > div > div > form > div.oxd-form-actions > button");

    public static final By MODAL_OK_CLICK  = By.cssSelector("#app > div.oxd-overlay.oxd-overlay--flex.oxd-overlay--flex-centered > div > div > div > div.orangehrm-modal-footer > button.oxd-button.oxd-button--medium.oxd-button--secondary.orangehrm-button-margin");

    // Confirmation or resulting message
    public static final By ASSIGN_SUCCESS_MSG = By.xpath("//div[contains(text(),'Successfully Assigned')]");

}

