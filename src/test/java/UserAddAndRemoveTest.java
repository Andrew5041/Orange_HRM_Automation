import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.AdminPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserAddAndRemoveTest extends BaseTest {

    private AdminPage adminPage;

    private String loggedEmployeeName;

    @BeforeEach
    public void setUp() {
        LoginPage loginPage = new LoginPage();
        loginPage
                .enterLoginUsername("Admin")
                .enterLoginPassword("admin123");
        DashboardPage dashboardPage = loginPage.clickLoginButton();
        loggedEmployeeName = dashboardPage.getLoggedEmployeeName();
        adminPage = dashboardPage.goToAdminPage();
    }

    @ParameterizedTest
    @CsvSource({
            "apuser12345, Admin, Enabled, abc123def"
    })
    public void userShouldBeSuccessfullyAddedAndRemovedFromSearchResults(String username, String role, String status, String password) {
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
