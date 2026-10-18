import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.orange_hrm.models.Employee;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.MyInfoPage;

import java.time.LocalDate;

import static org.orange_hrm.utils.FilePaths.ANDRZEJ_NEW_TEST_FILE;
import static org.orange_hrm.utils.FilePaths.ANDRZEJ_TEST_FILE;

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

        Employee employee = Employee.builder()
                .firstName("Marcin")
                .middleName("Tomasz")
                .lastName("Nowacki")
                .employeeId("MNowacki")
                .otherId("1233223")
                .driverLicenseNumber("546756")
                .licenseExpiryDate(LocalDate.of(2026, 12, 22))
                .nationality("Polish")
                .maritalStatus("Married")
                .dateOfBirth(LocalDate.of(1990, 9, 21))
                .gender("Male")
                .build();

        myInfoPage
                .enterFirstName(employee.getFirstName())
                .enterMiddleName(employee.getMiddleName())
                .enterLastName(employee.getLastName())
                .enterEmployeeId(employee.getEmployeeId())
                .enterOtherId(employee.getOtherId())
                .enterDriverLicenseNumber(employee.getDriverLicenseNumber())
                .selectLicenseExpiryDate(employee.getLicenseExpiryDate())
                .expandNationalityOptions()
                .selectNationality(employee.getNationality())
                .expandMaritalStatusesOptions()
                .selectMaritalStatus(employee.getMaritalStatus())
                .selectDateOfBirth(employee.getDateOfBirth())
                .chooseGender(employee.getGender())
                .savePersonalFields()
                .refreshPage();

        Assertions.assertEquals(employee.getFirstName() + " " + employee.getLastName(), myInfoPage.getLoggedEmployeeName());
    }

    @Test
    public void updateCustomFieldsShouldBeSavedSuccessfully() {

        Employee employee = Employee.builder()
                .bloodType("B+")
                .testField("999")
                .build();

        myInfoPage
                .expandBloodTypes()
                .selectBloodType(employee.getBloodType())
                .enterTestField(employee.getTestField())
                .saveCustomFields();

        Assertions.assertEquals(employee.getBloodType() + " " + employee.getTestField(), myInfoPage.getCustomFieldsValues());
    }

    @Test
    public void addedAndRemovedAttachmentShouldNotBeVisibleInAttachmentsTable() {

        myInfoPage
                .clickAddAttachmentsButton()
                .addCommentToAttachment("Andrzej testuje")
                .uploadAttachment(ANDRZEJ_TEST_FILE)
                .saveAttachment();

        Assertions.assertTrue(myInfoPage.isAttachmentPresentInTable("andrzejtest.jfif"), "Uploaded file was not found");

        myInfoPage
                .deleteAttachment("andrzejtest.jfif")
                .clickConfirmDeletionButton();

        Assertions.assertFalse(myInfoPage.isAttachmentPresentInTable("andrzejtest.jfif"), "Uploaded file was not found");
    }

    @Test
    public void replacedAttachmentShouldBeVisibleInAttachmentsTable() {

        myInfoPage
                .clickAddAttachmentsButton()
                .addCommentToAttachment("Andrzej testuje")
                .uploadAttachment(ANDRZEJ_TEST_FILE)
                .saveAttachment()
                .editAttachment("andrzejtest.jfif")
                .uploadAttachment(ANDRZEJ_NEW_TEST_FILE)
                .saveAttachment();

        Assertions.assertTrue(myInfoPage.isAttachmentPresentInTable("andrzejnewtest.jfif"), "Uploaded file was not found");
    }

    @Test
    public void dateOfAddedAttachmentShouldEqualTodayDate() {

        String expectedDate = LocalDate.now().toString();

        myInfoPage
                .clickAddAttachmentsButton()
                .addCommentToAttachment("Andrzej testuje")
                .uploadAttachment(ANDRZEJ_TEST_FILE)
                .saveAttachment();

        Assertions.assertEquals(expectedDate, myInfoPage.getAttachmentDate("andrzejtest.jfif"));
    }
}
