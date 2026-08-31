package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class DashboardPage {

    @FindBy(css = "div[class='oxd-brand-banner']")
    private WebElement brandBanner;

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenuButton;

    @FindBy(css = ".oxd-userdropdown-name")
    private WebElement loggedEmployeeName;

    public DashboardPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public DashboardPage goToAdminPage() {
        adminMenuButton.click();
        return this;
    }

    public String getLoggedEmployeeName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(loggedEmployeeName)).getText();
    }

    public boolean isBrandBannerPresent() {
        return brandBanner.isDisplayed();
    }
}