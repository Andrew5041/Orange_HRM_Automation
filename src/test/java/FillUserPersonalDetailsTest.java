import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.MyInfoPage;

import java.time.LocalDate;

public class FillUserPersonalDetailsTest extends BaseTest {

    private DashboardPage dashboardPage;
    private MyInfoPage myInfoPage;

    @BeforeEach
    public void setUp() {

        dashboardPage = new DashboardPage();

        myInfoPage = new MyInfoPage();

        dashboardPage.goToMyInfoPage();
    }

    @Test
    public void updatedPersonalDetailsShouldBeVisibleInProfile() {

        String firstName = "Marcin";
        String lastName = "Nowacki";

        myInfoPage
                .enterFirstName(firstName)
                .enterMiddleName("Tomasz")
                .enterLastName(lastName)
                .enterEmployeeId("JKowalski")
                .enterOtherId("123321")
                .enterDriverLicenseNumber("54541")
                .openLicenseExpiryDateCalendar()
                .openYearDropdown()
                .selectYear("2026")
                .openMonthDropdown()
                .selectMonth("December")
                .selectDay("22")
                .expandNationalityOptions()
                .selectNationality("Polish")
                .expandMaritalStatusesOptions()
                .selectMaritalStatus("Married")
                .openDateOfBirthCalendar()
                .openYearDropdown()
                .selectYear("1990")
                .openMonthDropdown()
                .selectMonth("September")
                .selectDay("11")
                .chooseGender("Male")
                .savePersonalFields()
                .refreshPage();

        Assertions.assertEquals(myInfoPage.getLoggedEmployeeName(), firstName + " " + lastName);
    }

    @Test
    public void updateCustomFieldsShouldBeSavedSuccessfully() {

        String bloodType = "A-";
        String testFieldInput = "abc";

        myInfoPage
                .expandBloodTypes()
                .selectBloodType(bloodType)
                .enterTestField(testFieldInput)
                .saveCustomFields();

        Assertions.assertEquals(bloodType + " " + testFieldInput, myInfoPage.getCustomFieldsValues());
    }

    @Test
    public void addedAndRemovedAttachmentShouldNotBeVisibleInAttachmentsTable() {

        String fileRelativePath = "src/test/resources/files/andrzejtest.jfif";

        myInfoPage
                .clickAddAttachmentsButton()
                .addCommentToAttachment("Andrzej testuje")
                .uploadAttachment(fileRelativePath)
                .saveAttachment();

        Assertions.assertTrue(myInfoPage.isAttachmentPresentInTable("andrzejtest.jfif"), "Uploaded file was not found");

        myInfoPage
                .deleteAttachment("andrzejtest.jfif")
                .clickConfirmDeletionButton();

        Assertions.assertFalse(myInfoPage.isAttachmentPresentInTable("andrzejnewtest.jfif"), "Uploaded file was not found");
    }

    @Test
    public void replacedAttachmentShouldBeVisibleInAttachmentsTable() {

        String initialFileRelativePath = "src/test/resources/files/andrzejtest.jfif";
        String newFileRelativePath = "src/test/resources/files/andrzejnewtest.jfif";

        myInfoPage
                .clickAddAttachmentsButton()
                .addCommentToAttachment("Andrzej testuje")
                .uploadAttachment(initialFileRelativePath)
                .saveAttachment()
                .editAttachment("andrzejtest.jfif")
                .uploadAttachment(newFileRelativePath)
                .saveAttachment();

        Assertions.assertTrue(myInfoPage.isAttachmentPresentInTable("andrzejnewtest.jfif"), "Uploaded file was not found");
    }

    @Test
    public void dateOfAddedAttachmentShouldEqualTodayDate() {

        String fileRelativePath = "src/test/resources/files/andrzejtest.jfif";
        String expectedDate = LocalDate.now().toString();

        myInfoPage
                .clickAddAttachmentsButton()
                .addCommentToAttachment("Andrzej testuje")
                .uploadAttachment(fileRelativePath)
                .saveAttachment();

        Assertions.assertEquals(expectedDate, myInfoPage.getAttachmentDate("andrzejtest.jfif"));
    }
}
