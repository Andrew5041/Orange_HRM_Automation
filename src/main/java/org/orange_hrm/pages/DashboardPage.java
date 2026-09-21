package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.orange_hrm.helpers.WaitHelper;

import java.time.Duration;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class DashboardPage {

    private WaitHelper waitHelper;

    @FindBy(css = "div[class='oxd-brand-banner']")
    private WebElement brandBanner;

    @FindBy(xpath = "//span[text()='Admin']")
    private WebElement adminMenuButton;

    @FindBy(css = ".oxd-userdropdown-name")
    private WebElement loggedEmployeeName;

    public DashboardPage() {
        PageFactory.initElements(getDriver(), this);
        waitHelper = new WaitHelper();
    }

    public AdminPage goToAdminPage() {
        adminMenuButton.click();
        return new AdminPage();
    }

    public String getLoggedEmployeeName() {
        return waitHelper.waitForVisibility(loggedEmployeeName).getText();
    }

    public boolean isBrandBannerPresent() {
        return brandBanner.isDisplayed();
    }
}