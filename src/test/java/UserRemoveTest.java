import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}
