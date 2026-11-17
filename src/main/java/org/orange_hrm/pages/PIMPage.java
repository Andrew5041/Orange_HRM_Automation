package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.orange_hrm.models.EmployeePIM;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PIMPage extends BasePage {

    @FindBy(xpath = "//button[text()=' Add ']")
    private WebElement addButton;

    @FindBy(css = "input[name='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='middleName']")
    private WebElement middleNameInput;

    @FindBy(css = "input[name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[text()='Employee Id']/ancestor::div[contains(@class, 'oxd-input-group')]//input")
    private WebElement employeeIdInput;

    @FindBy(xpath = "//label[text()='Employee Name']/ancestor::div[contains(@class, 'oxd-input-group')]//input")
    private WebElement employeeNameInput;

    @FindBy(xpath = "//a[text()='Job']")
    private WebElement jobSideMenuButton;

    @FindBy(xpath = "//a[text()='Employee List']")
    private WebElement employeeListUpperMenuButton;

    @FindBy(xpath = "//label[text()='Job Title']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-wrapper']")
    private WebElement jobTitleDropdown;

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']//span")
    private List<WebElement> jobTitlesList;

    @FindBy(xpath = "//label[text()='Employment Status']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-wrapper']")
    private WebElement employmentStatusDropdown;

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']//span")
    private List<WebElement> employmentStatusList;

    @FindBy(xpath = "//button[text()=' Save ']")
    private WebElement addEmployeeSaveButton;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(className = "orangehrm-container")
    private WebElement searchResultsContainer;

    @FindBy(xpath = "//h6[text()='Job Details']/ancestor::div[contains(@class, 'orangehrm-horizontal-padding')]//button[text()=' Save ']")
    private WebElement jobDetailsSaveButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']")
    private List<WebElement> employeesList;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][2]")
    private List<WebElement> idColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][3]")
    private List<WebElement> firstAndMiddleNameColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][4]")
    private List<WebElement> lastNameColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][5]")
    private List<WebElement> jobTitleColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][6]")
    private List<WebElement> employmentStatusColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-header')]//div[contains(@class, 'oxd-checkbox-wrapper')]")
    private WebElement tableHeaderCheckbox;

    @FindBy(xpath = "//div[contains(@class, 'orangehrm-horizontal-padding')]//button[contains(@class, 'oxd-button--label-danger')]")
    private WebElement deleteSelectedButton;

    @FindBy(xpath = "//div[contains(@class, 'orangehrm-modal-footer')]//button[contains(@class, 'oxd-button--medium oxd-button--label-danger')]")
    private WebElement confirmDeletionButton;

    @FindBy(xpath = "//h6[text()='Personal Details']/ancestor::div[contains(@class, 'orangehrm-horizontal-padding')]//form")
    private WebElement personalDetailsContainer;

    public PIMPage clickAddButton() {
        clickButton(addButton);
        return this;
    }

    public PIMPage enterFirstName(String firstname) {
        waitForElementToDisappear(loadingSpinner);

        type(firstNameInput, firstname);
        return this;
    }

    public PIMPage enterMiddleName(String middlename) {
        type(middleNameInput, middlename);
        return this;
    }

    public PIMPage enterLastName(String lastname) {
        type(lastNameInput, lastname);
        return this;
    }

    public String getEmployeeID() {
        return getInputElementText(employeeIdInput);
    }

    public String enterEmployeeID(boolean shouldIncrement) {

        String currentEmployeeID = getEmployeeID();

        if (shouldIncrement) {
            int nextEmployeeID = Integer.parseInt(getEmployeeID().substring(1)) + 1;

            currentEmployeeID = new StringBuilder("0")
                    .append(nextEmployeeID)
                    .toString();
            type(employeeIdInput, currentEmployeeID);

        }
        return currentEmployeeID;
    }

    public PIMPage saveEmployeeDetails() {
        clickButton(addEmployeeSaveButton);
        waitForElementToDisappear(loadingSpinner);
        waitForElementToAppear(personalDetailsContainer);
        return this;
    }

    public PIMPage clickJobSideMenuButton() {
        waitForElementToAppear(jobSideMenuButton);
        clickButton(jobSideMenuButton);
        return this;
    }

    public PIMPage expandJobTitleOptions() {
        clickButton(jobTitleDropdown);
        return this;
    }

    public PIMPage selectJobTitle(String jobTitle) {
        chooseOptionFromList(jobTitlesList, jobTitle);
        return this;
    }

    public PIMPage expandEmploymentStatusOptions() {
        clickButton(employmentStatusDropdown);
        return this;
    }

    public PIMPage selectEmploymentStatus(String employmentStatus) {
        chooseOptionFromList(employmentStatusList, employmentStatus);
        return this;
    }

    public PIMPage saveJobDetails() {
        clickButton(jobDetailsSaveButton);
        waitForElementToDisappear(loadingSpinner);
        return this;
    }

    public String getEmployeeFirstName() {
        return firstNameInput.getDomProperty("value");
    }

    public String getEmployeeMiddleName() {
        return middleNameInput.getDomProperty("value");
    }

    public String getEmployeeLastName() {
        return lastNameInput.getDomProperty("value");
    }

    public PIMPage clickEmployeeListButton() {
        clickButton(employeeListUpperMenuButton);
        return this;
    }

    public PIMPage enterEmployeeName(String employeeName) {
        type(employeeNameInput, employeeName);
        return this;
    }

    public PIMPage clickSearchButton() {
        clickButton(searchButton);
        waitForElementToDisappear(loadingSpinner);
        waitForElementToAppear(searchResultsContainer);
        return this;
    }

    public boolean isEmployeeDataProperlySaved(String firstName, String middleName, String lastName, String employeeID) {
        return getEmployeeFirstName().equals(firstName) && getEmployeeMiddleName().equals(middleName) && getEmployeeLastName().equals(lastName) && getEmployeeID().equals(employeeID);
    }

    public int getNumberOfFoundUsers() {
        return employeesList.size();
    }

    private List<EmployeePIM> getEmployeeDetailsFromList() {

        List<EmployeePIM> employeePIMS = new ArrayList<>();

        for (int i = 0; i < employeesList.size(); i++) {
            String id = idColumn.get(i).getText().trim();
            String firstAndMiddleName = firstAndMiddleNameColumn.get(i).getText().trim();
            String lastName = lastNameColumn.get(i).getText().trim();
            String jobTitle = jobTitleColumn.get(i).getText().trim();
            String employmentStatus = employmentStatusColumn.get(i).getText().trim();

            String firstName = null;
            String middleName = null;
            if (firstAndMiddleName.contains(" ")) {
                String[] nameParts = firstAndMiddleName.split(" ", 2);
                firstName = nameParts[0];
                middleName = nameParts[1];
            }

            employeePIMS.add(new EmployeePIM(
                    id,
                    firstName,
                    middleName,
                    lastName,
                    jobTitle,
                    employmentStatus
            ));
        }
        return employeePIMS;
    }

    public String getEmployeeFromTheList(EmployeePIM employeePIM) {

   /*     return getEmployeeDetailsFromList().stream()
                .filter(emp -> emp.jobTitle().equals(employeePIM.jobTitle()) && emp.employmentStatus().equals(employeePIM.employmentStatus()))
                //.map(emp -> (emp.firstName() + " " + emp.middleName() + " " + emp.lastName()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("User was not found"));

        .map(emp -> (emp.firstName() + " " + emp.middleName() + " " + emp.lastName()))
*/
        Optional<EmployeePIM> aaa = getEmployeeDetailsFromList().stream()
                .filter(emp -> emp.jobTitle().equals(employeePIM.jobTitle()) && emp.employmentStatus().equals(employeePIM.employmentStatus()))
                .findFirst();
        // .map(emp -> (emp.firstName() + " " + emp.middleName() + " " + emp.lastName()))

        aaa.ifPresentOrElse(e -> System.out.println(e.firstName() + " " + e.lastName()), () -> System.out.println("No such user"));
        //aaa.ifPresent();

        //Mamy operacje pośrednie i końcowe
        //ifPresent - jest metodą końcową

        return "";
    }


    public List<String> getQAEngineersEmployees() {

        List<EmployeePIM> filteredEmployeeList = getEmployeeDetailsFromList();

        return filteredEmployeeList.stream()
                .filter(e -> e.jobTitle().equals("QA Engineer"))
                .map(e -> e.firstName() + " " + e.middleName() + " " + e.lastName())
                .sorted()
                .toList();
    }

    public Optional<EmployeePIM> getQAEngineerDataByID(String employeeID) {

        return getEmployeeDetailsFromList().stream()
                .filter(e -> e.employeeID().equals(employeeID))
                .findFirst();
    }

    public String getQAEngineerEmploymentStatus(String employeeID) {

        return getQAEngineerDataByID(employeeID).map(e -> e.employmentStatus()).orElseThrow(() -> new AssertionError("User with ID " + employeeID + " was not found"));
    }

    public List<String> getDisplayedEmployeeIDs() {
        return getEmployeeDetailsFromList().stream()
                .map(e -> e.employeeID())
                .toList();
    }

    public PIMPage clickSelectAllInTableCheckbox() {
        waitForElementToAppear(tableHeaderCheckbox);
        clickButton(tableHeaderCheckbox);
        return this;
    }

    public PIMPage clickDeleteSelectedButton() {
        clickButton(deleteSelectedButton);
        return this;
    }

    public PIMPage clickConfirmDeletionButton() {
        clickButton(confirmDeletionButton);
        waitForElementToDisappear(loadingSpinner);
        return this;
    }
}
