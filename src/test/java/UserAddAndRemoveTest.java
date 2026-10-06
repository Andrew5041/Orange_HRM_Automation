import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.AdminPage;
import org.orange_hrm.pages.DashboardPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAddAndRemoveTest extends BaseTest {

    private AdminPage adminPage;

    private DashboardPage dashboardPage;

    @ParameterizedTest
    @CsvSource({
            "apuser12345, Admin, Enabled, abc123def"
    })
    public void userShouldBeSuccessfullyAddedAndRemovedFromSearchResults(String username, String role, String status, String password) {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        String loggedEmployeeName = dashboardPage.getLoggedEmployeeName();

        dashboardPage.goToAdminPage();

        adminPage
                .clickAddButton()
                .expandUserRoleOptions()
                .chooseOption(role)
                .enterEmployeeName(loggedEmployeeName)
                .chooseOption(loggedEmployeeName)
                .expandStatusOptions()
                .chooseOption(status)
                .enterUsername(username)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .clickSaveButton()
                .enterUsername(username)
                .clickSearchButton()
                .removeSearchedUser(username)
                .clickConfirmDeletionButton()
                .clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());
    }
}
