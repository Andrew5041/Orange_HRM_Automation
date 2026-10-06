package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(css = "div[class='oxd-brand-banner']")
    private WebElement brandBanner;

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenuButton;

    @FindBy(css = ".oxd-userdropdown-name")
    private WebElement loggedEmployeeName;

    public DashboardPage goToAdminPage() {
        clickButton(adminMenuButton);
        return this;
    }

    public String getLoggedEmployeeName() {
        return getElementText(loggedEmployeeName);
    }

    public boolean isBrandBannerPresent() {
        return brandBanner.isDisplayed();
    }
}