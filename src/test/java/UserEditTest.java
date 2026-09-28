import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.AdminPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserEditTest extends BaseTest {

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
            "Bartek, ESS, Enabled, abc123def"
    })
    public void userDetailsShouldBeSuccessfullyUpdatedAndRemoved(String username, String role, String status, String password) {

        String newUsername = "Tomasz";
        String newRole = "Admin";
        String newStatus = "Disabled";

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
                .editSearchedUser(username)
                .expandUserRoleOptions()
                .chooseOption(newRole)
                .expandStatusOptions()
                .chooseOption(newStatus)
                .enterUsername(newUsername)
                .clickSaveButton()
                .enterUsername(newUsername)
                .clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(newUsername, newRole, loggedEmployeeName, newStatus), "User " + username + " with provided details was not found");

        adminPage
                .enterUsername(username)
                .clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());

        adminPage
                .refresh()
                .enterUsername(newUsername)
                .clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(newUsername, newRole, loggedEmployeeName, newStatus), "User " + username + " with provided details was not found");

        adminPage
                .removeSearchedUser(newUsername)
                .clickConfirmDeletionButton()
                .clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());
    }
}
