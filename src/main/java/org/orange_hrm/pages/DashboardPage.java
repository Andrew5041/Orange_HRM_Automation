package org.orange_hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.orange_hrm.driver.DriverSingleton;

public class DashboardPage {
    private final By brandBanner = By.xpath("//div[@class='oxd-brand-banner']");

    public DashboardPage() {
    }

    private WebDriver getDriver() {
        return DriverSingleton.getDriver();
    }

    public boolean isBrandBannerPresent() {
        return getDriver().findElement(brandBanner).isDisplayed();
    }
}