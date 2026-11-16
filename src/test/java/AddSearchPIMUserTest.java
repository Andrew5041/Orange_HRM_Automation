import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.models.EmployeePIM;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.PIMPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddSearchPIMUserTest extends BaseTest {

    private DashboardPage dashboardPage;

    private PIMPage pimPage;

    private int userOrder = 0;

    @ParameterizedTest
    @CsvSource({
            "Anna, Maria, TestStream, QA Engineer, Full-Time Contract",
            "Piotr, Adam, TestStream, Software Engineer, Full-Time Contract",
            "Marta, Ewa, TestStream, QA Engineer, Part-Time Contract",
            "Tomasz, Jan, TestStream, Software Engineer, Part-Time Contract",
    })
    public void shouldAddAndVerifyEmployeesTest(String firstName, String middleName, String lastName, String jobTitle, String employmentStatus) {

        dashboardPage = new DashboardPage();

        pimPage = new PIMPage();

        if (userOrder == 0) {
            dashboardPage.goToPIMPage();
            pimPage
                    .clickEmployeeListButton()
                    .enterEmployeeName("TestStream")
                    .clickSearchButton();

            if (pimPage.getNumberOfFoundUsers() > 0) {
                pimPage
                        .clickSelectAllInTableCheckbox()
                        .clickDeleteSelectedButton()
                        .clickConfirmDeletionButton();
            }
        }

        dashboardPage.goToPIMPage();

        boolean shouldIncrement = (userOrder > 0);

        pimPage
                .clickAddButton()
                .enterFirstName(firstName)
                .enterMiddleName(middleName)
                .enterLastName(lastName)
                .enterEmployeeID(shouldIncrement);

        String fetchedEmployeeID = pimPage.getEmployeeID();

        userOrder++;

        EmployeePIM employeePIM = new EmployeePIM(
                fetchedEmployeeID,
                firstName,
                middleName,
                lastName,
                jobTitle,
                employmentStatus
        );

        pimPage.saveEmployeeDetails();

        Assertions.assertTrue(pimPage.isEmployeeDataProperlySaved(firstName, middleName, lastName, fetchedEmployeeID));

        pimPage
                .clickJobSideMenuButton()
                .expandJobTitleOptions()
                .selectJobTitle(employeePIM.jobTitle())
                .expandEmploymentStatusOptions()
                .selectEmploymentStatus(employeePIM.employmentStatus())
                .saveJobDetails()
                .clickEmployeeListButton()
                .enterEmployeeName(employeePIM.lastName())
                .clickSearchButton();

        Assertions.assertEquals(userOrder, pimPage.getNumberOfFoundUsers());

        String expectedFullName = employeePIM.firstName() + " " + employeePIM.middleName() + " " + employeePIM.lastName();

        Assertions.assertEquals(expectedFullName, pimPage.getEmployeeFromTheList(employeePIM));

        if (userOrder == 4) {
            Assertions.assertEquals("Anna Maria TestStream" + " " + "Marta Ewa TestStream", pimPage.getQAEngineersEmployees().get(0) + " " + pimPage.getQAEngineersEmployees().get(1));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Anna, Maria, TestStream, QA Engineer, Full-Time Contract",
            "Piotr, Adam, TestStream, Software Engineer, Full-Time Contract",
            "Marta, Ewa, TestStream, QA Engineer, Part-Time Contract",
            "Tomasz, Jan, TestStream, Software Engineer, Part-Time Contract",
    })
    public void test2(String firstName, String middleName, String lastName, String jobTitle, String employmentStatus) {


        dashboardPage = new DashboardPage();

        pimPage = new PIMPage();

        if (userOrder == 0) {
            dashboardPage.goToPIMPage();
            pimPage
                    .clickEmployeeListButton()
                    .enterEmployeeName("TestStream")
                    .clickSearchButton();

            if (pimPage.getNumberOfFoundUsers() > 0) {
                pimPage
                        .clickSelectAllInTableCheckbox()
                        .clickDeleteSelectedButton()
                        .clickConfirmDeletionButton();
            }
        }

        dashboardPage.goToPIMPage();

        boolean shouldIncrement = (userOrder > 0);

        pimPage
                .clickAddButton()
                .enterFirstName(firstName)
                .enterMiddleName(middleName)
                .enterLastName(lastName)
                .enterEmployeeID(shouldIncrement);

        String fetchedEmployeeID = pimPage.getEmployeeID();

        userOrder++;

        EmployeePIM employeePIM = new EmployeePIM(
                fetchedEmployeeID,
                firstName,
                middleName,
                lastName,
                jobTitle,
                employmentStatus
        );

        pimPage.saveEmployeeDetails();













    }



}
