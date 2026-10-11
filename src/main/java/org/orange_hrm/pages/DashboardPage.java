package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(css = "div[class='oxd-brand-banner']")
    private WebElement brandBanner;

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenuButton;

    @FindBy(xpath = "//span[text()='My Info']")
    private WebElement myInfoButton;

    @FindBy(xpath = "//h6[text()='Personal Details']")
    private WebElement personalDetailsTitle;

    public DashboardPage goToAdminPage() {
        clickButton(adminMenuButton);
        return this;
    }

    public DashboardPage goToMyInfoPage() {
        clickButton(myInfoButton);
        waitForElementToAppear(personalDetailsTitle);
        return this;
    }

    public boolean isBrandBannerPresent() {
        return brandBanner.isDisplayed();
    }
}