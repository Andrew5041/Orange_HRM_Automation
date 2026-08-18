import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.AdminPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

public class UserSearchTest extends BaseTest {

    @BeforeEach
    public void setUpAdminPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginSuccessfully("Admin", "admin123");
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.goToAdminPage();
    }

    @ParameterizedTest
    @CsvSource({
            "AdminD, Admin, Disabled",
            "EssEna, ESS, Enabled",
            "Essdis, ESS, Disabled"
    })
    public void registeredUsersShouldBeFoundInUsersSearchResults(String username, String role, String status) {
        AdminPage adminPage = new AdminPage();
        adminPage.searchUser(username);

        Assertions.assertTrue(adminPage.isUserDataPresentInTable(username));
        Assertions.assertTrue(adminPage.isUserDataPresentInTable(role));
        Assertions.assertTrue(adminPage.isUserDataPresentInTable(status));
    }

    @Test
    public void notRegisteredUserShouldNotBeFoundInUsersSearchResults() {
        AdminPage adminPage = new AdminPage();
        adminPage.searchUser("user_that_does_not_exist_123");

        Assertions.assertTrue(adminPage.isNoRecordsFoundPopupVisible());
    }
}
