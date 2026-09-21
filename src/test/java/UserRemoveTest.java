import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.LoginPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.AdminPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRemoveTest extends BaseTest {

    private AdminPage adminPage;

    @BeforeEach
    public void setUp() {
        LoginPage loginPage = new LoginPage();
        loginPage.enterLoginUsername("Admin");
        loginPage.enterLoginPassword("admin123");
        DashboardPage dashboardPage = loginPage.clickLoginButton();
        adminPage = dashboardPage.goToAdminPage();
    }

    @Test
    public void removedUserShouldNotBeFoundInUsersSearchResults() {
        adminPage.enterUsername("testuser123");
        adminPage.clickSearchButton();
        adminPage.removeSearchedUser("testuser123");
        adminPage.clickConfirmDeletionButton();
        adminPage.clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundPopupVisible());
    }

    @ParameterizedTest
    @CsvSource({
            "TuwaiqNasser111, ESS, Timothy Amiano, Enabled"
    })
    public void removedUserByDeleteSelectedButtonShouldNotBeFoundInUsersSearchResults(String username, String role, String employeeName, String status) {

        adminPage.enterUsername(username);
        adminPage.clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, employeeName, status), "User " + username + " with provided details was not found");

        adminPage.markCheckboxForSearchedUser(username);
        adminPage.clickDeleteSelectedButton();
        adminPage.clickConfirmDeletionButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());
    }
}
