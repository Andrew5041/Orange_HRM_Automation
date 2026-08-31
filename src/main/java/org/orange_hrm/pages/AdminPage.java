package org.orange_hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class AdminPage {

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']/span")
    private List<WebElement> optionsList;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']")
    private List<WebElement> usersList;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='oxd-toast-content oxd-toast-content--info']//p[text()='No Records Found']")
    private WebElement noRecordsFoundPopup;


    public AdminPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public AdminPage enterInput(String label, String value) {
        String inputField = "//label[text()='" + label + "']/following::input[1]";
        getDriver().findElement(By.xpath(inputField)).sendKeys(value);
        return this;
    }

    public AdminPage expandDropDownOptions(String label) {
        String dropDownButton = "//label[text()='" + label + "']/ancestor::div[contains(@class, 'oxd-input-group')]//i";
        getDriver().findElement(By.xpath(dropDownButton)).click();
        return this;
    }

    public AdminPage clickSearchButton() {
        searchButton.click();
        return this;
    }

    public AdminPage chooseOption(String option) {
        optionsList.stream()
                .filter(e -> e.getText().trim().equals(option))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option " + option + " not found"))
                .click();

        return this;
    }

    public boolean isUserPresentInTable(String username, String role, String employeeName, String status) {

        for (WebElement row : usersList) {
            String text = row.getText();
            if (text.contains(username) && text.contains(role) && text.contains(employeeName) && text.contains(status)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getUserDetailsFromTable(String expectedUsername) {

        for (WebElement row : usersList) {
            String actualUsername = row.findElement(By.xpath("./div[@role='cell'][2]")).getText().trim();
            if (actualUsername.equals(expectedUsername)) {

                String role = row.findElement(By.xpath("./div[@role='cell'][3]")).getText().trim();
                String employeeName = row.findElement(By.xpath("./div[@role='cell'][4]")).getText().trim();
                String status = row.findElement(By.xpath("./div[@role='cell'][5]")).getText().trim();

                return Arrays.asList(actualUsername, role, employeeName, status);
            }
        }
        return Collections.emptyList();
    }

    public boolean isNoRecordsFoundPopupVisible() {
        return noRecordsFoundPopup.isDisplayed();
    }
}
