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
        loginPage.enterLoginUsername("Admin");
        loginPage.enterLoginPassword("admin123");
        DashboardPage dashboardPage = loginPage.clickLoginButton();
        loggedEmployeeName = dashboardPage.getLoggedEmployeeName();
        adminPage = dashboardPage.goToAdminPage();
    }

    @ParameterizedTest
    @CsvSource({
            "apuser123, Admin, Enabled, abc123def"
    })
    public void userShouldBeSuccesfullyAddedAndRemovedFromSearchResults(String username, String role, String status, String password) {
        adminPage.clickAddButton();
        adminPage.expandUserRoleOptions();
        adminPage.chooseOption(role);
        adminPage.enterEmployeeName(loggedEmployeeName);
        adminPage.chooseOption(loggedEmployeeName);
        adminPage.expandStatusOptions();
        adminPage.chooseOption(status);
        adminPage.enterUsername(username);
        adminPage.enterPassword(password);
        adminPage.enterConfirmPassword(password);
        adminPage.clickSaveButton();
        adminPage.enterUsername(username);
        adminPage.clickSearchButton();
        adminPage.removeSearchedUser(username);
        adminPage.clickConfirmDeletionButton();
        adminPage.clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundPopupVisible());
    }
}
