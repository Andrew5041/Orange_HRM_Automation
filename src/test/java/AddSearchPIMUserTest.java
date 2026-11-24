import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.models.EmployeePIM;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.PIMPage;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddSearchPIMUserTest extends BaseTest {

    private DashboardPage dashboardPage;

    private PIMPage pimPage;

    private int userOrder = 0;

    private String annaID;
    private String martaID;

    private List<EmployeePIM> createdEmployees = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        dashboardPage = new DashboardPage();
        pimPage = new PIMPage();
    }

    @ParameterizedTest
    @CsvSource({
            "Anna, Maria, TestStream, QA Engineer, Full-Time Contract",
            "Piotr, Adam, TestStream, Software Engineer, Full-Time Contract",
            "Marta, Ewa, TestStream, QA Engineer, Part-Time Contract",
            "Tomasz, Jan, TestStream, Software Engineer, Part-Time Contract",
    })
    public void shouldAddAndVerifyEmployeesTest(String firstName, String middleName, String lastName, String jobTitle, String employmentStatus) {

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

        createdEmployees.add(employeePIM);

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
            List<String> expectedQAEngineers = createdEmployees.stream()
                    .filter(e -> e.jobTitle().equals("QA Engineer"))
                    .map(e -> e.firstName() + " " + e.middleName() + " " + e.lastName())
                    .sorted()
                    .toList();

            Assertions.assertEquals(expectedQAEngineers, pimPage.getQAEngineersEmployees());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Anna, Maria, TestStream, QA Engineer, Full-Time Contract",
            "Piotr, Adam, TestStream, Software Engineer, Full-Time Contract",
            "Marta, Ewa, TestStream, QA Engineer, Part-Time Contract",
            "Tomasz, Jan, TestStream, Software Engineer, Part-Time Contract",
    })
    public void shouldFilterQAEngineersAndVerifyEmploymentStatusTest(String firstName, String middleName, String lastName, String jobTitle, String employmentStatus) {

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

        if ("Anna".equals(firstName)) {
            annaID = fetchedEmployeeID;
        } else if ("Marta".equals(firstName)) {
            martaID = fetchedEmployeeID;
        }

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
                .saveJobDetails();

        if (userOrder == 4) {
            pimPage
                    .clickEmployeeListButton()
                    .expandJobTitleOptions()
                    .selectJobTitle("QA Engineer")
                    .clickSearchButton();

            List<String> displayedIDs = pimPage.getDisplayedEmployeeIDs();

            Assertions.assertAll(
                    () -> Assertions.assertEquals("Part-Time Contract", pimPage.getQAEngineerEmploymentStatus(martaID)),
                    () -> Assertions.assertEquals(2, displayedIDs.size(), "Table should return 2 results"),
                    () -> Assertions.assertTrue(displayedIDs.containsAll(List.of(annaID, martaID)), "Table should contain AnnaID " + annaID + " oraz MartaID " + martaID + " Found IDs: " + displayedIDs)
            );
        }
    }
}
