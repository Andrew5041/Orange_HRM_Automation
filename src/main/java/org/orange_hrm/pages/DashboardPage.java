package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class DashboardPage {

    @FindBy(css = "div[class='oxd-brand-banner']")
    WebElement brandBanner;

    public DashboardPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public boolean isBrandBannerPresent() {
        return brandBanner.isDisplayed();
    }
}