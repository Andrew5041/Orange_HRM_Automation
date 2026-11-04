import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.models.EmployeePIM;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.PIMPage;

public class AddSearchPIMUserTest extends BaseTest {

    private DashboardPage dashboardPage;

    private PIMPage pimPage;

    int userOrder = 0;

    @ParameterizedTest
    @CsvSource({
            "Anna, Maria, TestStream, QA Engineer, Full-Time Contract",
            "Piotr, Adam, TestStream, Software Engineer, Full-Time Contract",
            "Marta, Ewa, TestStream, QA Engineer, Part-Time Contract",
            "Tomasz, Jan, TestStream, Software Engineer, Part-Time Contract",
    })
    public void test1(String firstName, String middleName, String lastName, String jobTitle, String employmentStatus) {

        dashboardPage = new DashboardPage();

        pimPage = new PIMPage();

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

        //To poniżej nie zadziała bo po każdym dodaniu employee będzie to sprawdzane. Może użyć jakiegoś @order i zrobić dwa niezależne od siebie testy ?

        Assertions.assertEquals(4, pimPage.getNumberOfFoundUsers());

        Assertions.assertEquals("Anna Maria TestStream", pimPage.getEmployeeFromTheList(employeePIM));
    }
}
