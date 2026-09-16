package org.orange_hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class AdminPage {

    @FindBy(xpath = "//label[text()='Username']/following::input[1]")
    private WebElement usernameInput;

    @FindBy(xpath = "//label[text()='User Role']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-text-input']")
    private WebElement userRoleDropDown;

    @FindBy(xpath = "//label[text()='Employee Name']/following::input[1]")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//label[text()='User Role']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-text-input']")
    private WebElement statusDropDown;

    @FindBy(xpath = "//label[text()='User Role']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-wrapper']")
    private WebElement userRoleDropDownButton;

    @FindBy(xpath = "//label[text()='Status']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-wrapper']")
    private WebElement statusDropDownButton;

    @FindBy(xpath = "//label[text()='Password']/following::input[1]")
    private WebElement passwordInput;

    @FindBy(xpath = "//label[text()='Confirm Password']/following::input[1]")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']/span")
    private List<WebElement> optionsList;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']")
    private List<WebElement> usersList;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][2]")
    private List<WebElement> usernameColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][3]")
    private List<WebElement> roleColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][4]")
    private List<WebElement> employeeColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][5]")
    private List<WebElement> statusColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][6]")
    private List<WebElement> actionsColumn;

    @FindBy(xpath = "//button[text()=' Reset ']")
    private WebElement resetButton;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//button[text()=' Add ']")
    private WebElement addButton;

    @FindBy(xpath = "//button[text()=' Save ']")
    private WebElement saveButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][6]//button[@type='button'][1]")
    private List<WebElement> trashButton;

    @FindBy(xpath = "//div[contains(@class, 'orangehrm-modal-footer')]//button[contains(@class, 'oxd-button--medium oxd-button--label-danger')]")
    private WebElement confirmDeletionButton;

    @FindBy(xpath = "//span[contains(@class, 'oxd-input-field-error-message')]")
    private WebElement inputErrorMessage;

    @FindBy(xpath = "//div[@class='oxd-toast-content oxd-toast-content--info']//p[text()='No Records Found']")
    private WebElement noRecordsFoundPopup;

    public AdminPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public AdminPage enterUsername(String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public AdminPage enterEmployeeName(String employeeName) {
        employeeNameInput.sendKeys(employeeName);
        return this;
    }

    public AdminPage enterPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public AdminPage enterConfirmPassword(String password) {
        confirmPasswordInput.sendKeys(password);
        return this;
    }

    public AdminPage expandUserRoleOptions() {
        userRoleDropDownButton.click();
        return this;
    }

    public AdminPage expandStatusOptions() {
        statusDropDownButton.click();
        return this;
    }

    public AdminPage clickResetButton() {
        resetButton.click();
        return this;
    }

    public AdminPage clickSearchButton() {
        searchButton.click();
        return this;
    }

    public AdminPage clickAddButton() {
        addButton.click();
        return this;
    }

    public AdminPage clickSaveButton() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(@class, 'oxd-input-field-error-message')]")));
        saveButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-loading-spinner")));
        return this;
    }

    public AdminPage clickConfirmDeletionButton() {
        confirmDeletionButton.click();
        return this;
    }

    public AdminPage chooseOption(String option) {
        optionsList.stream()
                .filter(element -> Arrays.stream(option.split("\\s+"))
                        .allMatch(word -> element.getText()
                                .toLowerCase()
                                .contains(word.toLowerCase())))
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

    public AdminPage removeSearchedUser(String username) {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-loading-spinner")));

        for (int i = 0; i < usersList.size(); i++) {
            String actualUsername = usernameColumn.get(i).getText().trim();
            if (actualUsername.equals(username)) {
                trashButton.get(i).click();
                return this;
            }
        }
        throw new RuntimeException("Username " + username + " was not found in the table");
    }

/*        if(!usernameColumn.isEmpty() && usernameColumn.get(0).getText().trim().equals(username)){
            trashButton.get(0).click();
            return this;
        }
        throw new RuntimeException("Username " + username + " was not found in the table");*/

    public boolean isUsernameFieldEmpty() {
        return usernameInput.getDomProperty("value").trim().isEmpty();
    }

    public boolean isUserRoleFieldEmpty() {
        return userRoleDropDown.getText().trim().equals("-- Select --");
    }

    public boolean isEmployeeNameFieldEmpty() {
        return employeeNameInput.getDomProperty("value").trim().isEmpty();
    }

    public boolean isStatusFieldEmpty() {
        return userRoleDropDown.getText().trim().equals("-- Select --");
    }

    public boolean areAllFiltersFieldsClear() {
        return isUsernameFieldEmpty() && isUserRoleFieldEmpty() && isEmployeeNameFieldEmpty() && isStatusFieldEmpty();
    }

    public List<String> getUserDetailsFromTable(String expectedUsername) {

        for (int i = 0; i < usersList.size(); i++) {
            String actualUsername = usernameColumn.get(i).getText().trim();
            if (actualUsername.equals(expectedUsername)) {

                return Arrays.asList(
                        actualUsername,
                        roleColumn.get(i).getText().trim(),
                        employeeColumn.get(i).getText().trim(),
                        statusColumn.get(i).getText().trim());
            }
        }
        throw new NoSuchElementException("User " + expectedUsername + " was not found in results table");
    }

    public boolean isNoRecordsFoundPopupVisible() {
        return noRecordsFoundPopup.isDisplayed();
    }
}

