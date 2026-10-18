package org.orange_hrm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class MyInfoPage extends BasePage {

    @FindBy(css = "input[name='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='middleName']")
    private WebElement middleNameInput;

    @FindBy(css = "input[name='lastName']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[text()='Employee Id']/ancestor::div[contains(@class, 'oxd-input-group')]//input[contains(@class, 'oxd-input')]")
    private WebElement employeeIdInput;

    @FindBy(xpath = "//label[text()='Other Id']/ancestor::div[contains(@class, 'oxd-input-group')]//input[contains(@class, 'oxd-input')]")
    private WebElement otherIdInput;

    @FindBy(xpath = "//label[contains(text(), 'License Number')]/ancestor::div[contains(@class, 'oxd-input-group')]//input[contains(@class, 'oxd-input')]")
    private WebElement driversLicenseInput;

    @FindBy(xpath = "//label[text()='License Expiry Date']/ancestor::div[contains(@class, 'oxd-input-group')]//input[@placeholder='yyyy-dd-mm']")
    private WebElement licenseExpiryDatePicker;

    @FindBy(css = ".oxd-calendar-selector-year-selected")
    private WebElement calendarYearDropdown;

    @FindBy(css = ".oxd-calendar-selector-month-selected")
    private WebElement calendarMonthDropdown;

    @FindBy(xpath = "//ul[contains(@class, 'oxd-calendar-dropdown')]/li")
    private List<WebElement> calendarDropdownOptionsList;

    @FindBy(xpath = "//div[@class='oxd-calendar-dates-grid']//div[@class='oxd-calendar-date']")
    private List<WebElement> calendarDaysList;

    @FindBy(xpath = "//label[text()='Nationality']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-wrapper']")
    private WebElement nationalityDropdown;

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']//span")
    private List<WebElement> nationalityList;

    @FindBy(xpath = "//label[text()='Marital Status']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-text-input']")
    private WebElement maritalStatusDropdown;

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']//span")
    private List<WebElement> maritalStatusList;

    @FindBy(xpath = "//label[text()='Date of Birth']/ancestor::div[contains(@class, 'oxd-input-group')]//input[@placeholder='yyyy-dd-mm']")
    private WebElement dateOfBirthDatePicker;

    @FindBy(xpath = "//input[@type='radio' and @value='1']/following-sibling::span")
    private WebElement maleGenderRadioButton;

    @FindBy(xpath = "//input[@type='radio' and @value='2']/following-sibling::span")
    private WebElement femaleGenderRadioButton;

    @FindBy(xpath = "//label[text()='Blood Type']/ancestor::div[contains(@class, 'oxd-input-group')]//div[@class='oxd-select-text-input']")
    private WebElement bloodTypeDropdown;

    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']//span")
    private List<WebElement> bloodTypesList;

    @FindBy(xpath = "//label[text()='Test_Field']/ancestor::div[contains(@class, 'oxd-input-group')]//input[contains(@class, 'oxd-input')]")
    private WebElement testFieldInput;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']")
    private List<WebElement> attachmentsList;

    @FindBy(xpath = "//h6[text()='Attachments']/ancestor::div[contains(@class, 'orangehrm-action-header')]//button")
    private WebElement addAttachmentsButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']//span[contains(@class, 'oxd-checkbox-input')][1]")
    private List<WebElement> checkBox;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][2]")
    private List<WebElement> fileNameColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][3]")
    private List<WebElement> descriptionColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][4]")
    private List<WebElement> sizeColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][5]")
    private List<WebElement> typeColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][6]")
    private List<WebElement> dateAddedColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][7]")
    private List<WebElement> addedByColumn;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']//div[@role='cell'][8]//button[@type='button'][1]")
    private List<WebElement> editButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']//div[@role='cell'][8]//button[@type='button'][2]")
    private List<WebElement> trashButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']//div[@role='cell'][8]//button[@type='button'][3]")
    private List<WebElement> downloadButton;

    @FindBy(xpath = "//h6[text()='Personal Details']/ancestor::div[contains(@class, 'orangehrm-horizontal-padding')]//button[text()=' Save ']")
    private WebElement personalDetailsSaveButton;

    @FindBy(xpath = "//h6[text()='Custom Fields']/ancestor::div[contains(@class, 'orangehrm-custom-fields')]//button[text()=' Save ']")
    private WebElement customFieldsSaveButton;

    @FindBy(xpath = "//div[contains(@class, 'orangehrm-attachment')]//button[@type='submit' and contains(@class, 'oxd-button--secondary')]")
    private WebElement attachmentsSaveButton;

    @FindBy(xpath = "//textarea[@placeholder = 'Type comment here']")
    private WebElement commentAttachmentInput;

    @FindBy(xpath = "//input[@type='file']")
    private WebElement fileUploadInput;

    @FindBy(xpath = "//div[contains(@class, 'orangehrm-modal-footer')]//button[contains(@class, 'oxd-button--medium oxd-button--label-danger')]")
    private WebElement confirmDeletionButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-toast-content-text')]//p[text()='Successfully Updated']")
    private WebElement successfullSavingPopup;

    public MyInfoPage enterFirstName(String firstname) {
        waitForElementToDisappear(loadingSpinner);

        type(firstNameInput, firstname);
        return this;
    }

    public MyInfoPage enterMiddleName(String middlename) {
        type(middleNameInput, middlename);
        return this;
    }

    public MyInfoPage enterLastName(String lastname) {
        type(lastNameInput, lastname);
        return this;
    }

    public MyInfoPage enterEmployeeId(String employeeId) {
        type(employeeIdInput, employeeId);
        return this;
    }

    public MyInfoPage enterOtherId(String otherId) {
        type(otherIdInput, otherId);
        return this;
    }

    public MyInfoPage enterDriverLicenseNumber(String driverLicenseNumber) {
        type(driversLicenseInput, driverLicenseNumber);
        return this;
    }

    public MyInfoPage openLicenseExpiryDateCalendar() {
        clickButton(licenseExpiryDatePicker);
        return this;
    }

    public MyInfoPage selectLicenseExpiryDate(LocalDate localDate) {
        String year = String.valueOf(localDate.getYear());
        String month = ((String.valueOf(localDate.getMonth()).charAt(0)) + (String.valueOf(localDate.getMonth())).substring(1).toLowerCase());
        String day = String.valueOf(localDate.getDayOfMonth());

        openLicenseExpiryDateCalendar();
        openYearDropdown();
        selectYear(year);
        openMonthDropdown();
        selectMonth(month);
        selectDay(day);

        return this;
    }

    public MyInfoPage openDateOfBirthCalendar() {
        clickButton(dateOfBirthDatePicker);
        return this;
    }

    public MyInfoPage openYearDropdown() {
        clickButton(calendarYearDropdown);
        return this;
    }

    public MyInfoPage openMonthDropdown() {
        clickButton(calendarMonthDropdown);
        return this;
    }

    public MyInfoPage selectYear(String year) {
        chooseOptionFromList(calendarDropdownOptionsList, year);
        return this;
    }

    public MyInfoPage selectMonth(String month) {
        chooseOptionFromList(calendarDropdownOptionsList, month);
        return this;
    }

    public MyInfoPage selectDay(String day) {
        chooseOptionFromList(calendarDaysList, day);
        return this;
    }

    public MyInfoPage selectDateOfBirth(LocalDate localDate) {
        String year = String.valueOf(localDate.getYear());
        String month = ((String.valueOf(localDate.getMonth()).charAt(0)) + (String.valueOf(localDate.getMonth())).substring(1).toLowerCase());
        String day = String.valueOf(localDate.getDayOfMonth());

        openDateOfBirthCalendar();
        openYearDropdown();
        selectYear(year);
        openMonthDropdown();
        selectMonth(month);
        selectDay(day);

        return this;
    }

    public MyInfoPage expandNationalityOptions() {
        clickButton(nationalityDropdown);
        return this;
    }

    public MyInfoPage selectNationality(String nationality) {
        chooseOptionFromList(nationalityList, nationality);
        return this;
    }

    public MyInfoPage expandMaritalStatusesOptions() {
        clickButton(maritalStatusDropdown);
        return this;
    }

    public MyInfoPage selectMaritalStatus(String maritalStatus) {
        chooseOptionFromList(maritalStatusList, maritalStatus);
        return this;
    }

    public MyInfoPage chooseGender(String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            clickButton(maleGenderRadioButton);
        } else if (gender.equalsIgnoreCase("Female")) {
            clickButton(femaleGenderRadioButton);
        } else {
            throw new RuntimeException("Given gender " + gender + " does not exist");
        }
        return this;
    }

    public MyInfoPage expandBloodTypes() {
        clickButton(bloodTypeDropdown);
        return this;
    }

    public MyInfoPage selectBloodType(String bloodType) {
        chooseOptionFromList(bloodTypesList, bloodType);
        return this;
    }

    public MyInfoPage savePersonalFields() {
        clickButton(personalDetailsSaveButton);
        waitForElementToDisappear(loadingSpinner);
        return this;
    }

    public MyInfoPage enterTestField(String testFieldText) {
        type(testFieldInput, testFieldText);
        return this;
    }

    public MyInfoPage saveCustomFields() {
        clickButton(customFieldsSaveButton);
        waitForElementToDisappear(loadingSpinner);
        return this;
    }

    public String getBloodType() {
        return getElementText(bloodTypeDropdown);
    }

    public String getTestFieldValue() {
        return getInputElementText(testFieldInput);
    }

    public String getCustomFieldsValues() {
        return ((getBloodType() + " " + getTestFieldValue()));
    }

    public MyInfoPage clickAddAttachmentsButton() {
        clickButton(addAttachmentsButton);
        return this;
    }

    public MyInfoPage addCommentToAttachment(String comment) {
        type(commentAttachmentInput, comment);
        return this;
    }

    public MyInfoPage uploadAttachment(String relativePath) {
        uploadFile(fileUploadInput, relativePath);
        return this;
    }

    public MyInfoPage saveAttachment() {
        clickButton(attachmentsSaveButton);
        waitForElementToDisappear(loadingSpinner);
        return this;
    }

    public MyInfoPage editAttachment(String fileName) {

        for (int i = 0; i < attachmentsList.size(); i++) {
            String actualFileName = fileNameColumn.get(i).getText().trim();
            if (actualFileName.equals(fileName)) {
                clickButton(editButton.get(i));
                return this;
            }
        }
        throw new RuntimeException("File " + fileName + " was not found in the table");
    }

    public MyInfoPage deleteAttachment(String fileName) {

        for (int i = 0; i < attachmentsList.size(); i++) {
            String actualFileName = fileNameColumn.get(i).getText().trim();
            if (actualFileName.equals(fileName)) {
                clickButton(trashButton.get(i));
                return this;
            }
        }
        throw new RuntimeException("File " + fileName + " was not found in the table");
    }

    public MyInfoPage downloadAttachment(String fileName) {

        for (int i = 0; i < attachmentsList.size(); i++) {
            String actualFileName = fileNameColumn.get(i).getText().trim();
            if (actualFileName.equals(fileName)) {
                clickButton(downloadButton.get(i));
                return this;
            }
        }
        throw new RuntimeException("File " + fileName + " was not found in the table");
    }

    public MyInfoPage clickConfirmDeletionButton() {
        clickButton(confirmDeletionButton);
        waitForElementToDisappear(loadingSpinner);
        return this;
    }

    public boolean isAttachmentPresentInTable(String fileName) {
        for (WebElement attachment : attachmentsList) {
            String text = attachment.getText();
            if (text.contains(fileName)) {
                return true;
            }
        }
        return false;
    }

    public String getAttachmentDate(String fileName) {
        for (int i = 0; i < attachmentsList.size(); i++) {
            String actualFileName = fileNameColumn.get(i).getText().trim();
            if (actualFileName.equals(fileName)) {
                return dateAddedColumn.get(i).getText().trim();
            }
        }
        throw new NoSuchElementException("File " + fileName + " was not found in results table");
    }
}
