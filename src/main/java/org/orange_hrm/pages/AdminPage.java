package org.orange_hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class AdminPage {

    @FindBy(xpath = "//label[text()='Username']/ancestor::div[contains(@class, 'oxd-input-group')]//input")
    private WebElement usernameInput;

    @FindBy(xpath = "//label[text()='Employee Name']/ancestor::div[contains(@class, 'oxd-input-group')]//input")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option'][1]")
    private WebElement employeeNameAutocompleteOption;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='oxd-toast-content oxd-toast-content--info']//p[text()='No Records Found']")
    private WebElement noRecordsFoundPopup;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']")
    private List<WebElement> usersList;

    public AdminPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public AdminPage enterUsername(String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public AdminPage expandDropDownOptions(String label) {
        String dropDownButton = "//label[text()='" + label + "']/ancestor::div[contains(@class, 'oxd-input-group')]//i";
        getDriver().findElement(By.xpath(dropDownButton)).click();
        return this;
    }

    public AdminPage chooseDropDownOption(String optionText) {
        String option = "//div[@role='listbox']//div[@role='option']//span[text()='" + optionText + "']";
        getDriver().findElement(By.xpath(option)).click();
        return this;
    }

    public AdminPage enterEmployeeName(String employeeName) {
        employeeNameInput.sendKeys(employeeName);
        return this;
    }

    //Uproszczone założenie że znajdzie tylko jedną opcję
    public AdminPage chooseEmployeeName() {
        if (!employeeNameAutocompleteOption.getText().equals("No Records Found")) {
            employeeNameAutocompleteOption.click();
        }
        return this;
    }

    public AdminPage clickSearchButton() {
        searchButton.click();
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

/*        return usersList.stream()
                .map(WebElement::getText)
                .anyMatch(text -> text.contains(username) &&
                        text.contains(role) &&
                        text.contains(status));*/
    }

    public List<String> getUserDetailsFromTableWithoutSearching(String expectedUsername) {
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
