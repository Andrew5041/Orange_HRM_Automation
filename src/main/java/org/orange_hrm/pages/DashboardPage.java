package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class DashboardPage {

    @FindBy(css = "div[class='oxd-brand-banner']")
    private WebElement brandBanner;

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenuButton;

    public DashboardPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public DashboardPage goToAdminPage() {
        adminMenuButton.click();
        return this;
    }

    public boolean isBrandBannerPresent() {
        return brandBanner.isDisplayed();
    }
}