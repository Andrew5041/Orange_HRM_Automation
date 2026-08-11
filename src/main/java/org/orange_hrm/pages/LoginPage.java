package org.orange_hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.orange_hrm.driver.DriverSingleton;

public class LoginPage {

    private final By usernameInput = By.xpath("//input[@name='username']");
    private final By passwordInput = By.xpath("//input[@name='password']");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.xpath("//p[text()='Invalid credentials']");

    public LoginPage() {
    }

    private WebDriver getDriver() {
        return DriverSingleton.getDriver();
    }

    public void openLoginPage(String url) {
        getDriver().get(url);
    }

    public DashboardPage login(String username, String password) {
        getDriver().findElement(usernameInput).sendKeys(username);
        getDriver().findElement(passwordInput).sendKeys(password);
        getDriver().findElement(loginButton).click();
        return new DashboardPage();
    }

    public DashboardPage loginSuccessfully(String username, String password) {
        getDriver().findElement(usernameInput).sendKeys(username);
        getDriver().findElement(passwordInput).sendKeys(password);
        getDriver().findElement(loginButton).click();
        return new DashboardPage();
    }

    public LoginPage loginWithFailure(String username, String password) {
        getDriver().findElement(usernameInput).sendKeys(username);
        getDriver().findElement(passwordInput).sendKeys(password);
        getDriver().findElement(loginButton).click();
        return this;
    }

    public boolean isErrorMessagedDisplayed() {
        return getDriver().findElement(errorMessage).isDisplayed();
    }
}