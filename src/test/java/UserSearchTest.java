import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.AdminPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import java.time.Duration;

import static org.orange_hrm.driver.DriverSingleton.getDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSearchTest extends BaseTest {

    @BeforeEach
    public void setUpAdminPage() {

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        LoginPage loginPage = new LoginPage();
        loginPage.enterLoginUsername("Admin");
        loginPage.enterLoginPassword("admin123");
        loginPage.clickLoginButton();

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.goToAdminPage();
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, john Doe, Enabled"
    })
    public void registeredUsersShouldBeFoundInUsersSearchResults(String username, String role, String employeeName, String status) {
        AdminPage adminPage = new AdminPage();
        adminPage.enterAdminSearchUsername(username);
        adminPage.clickAdminSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, employeeName, status));
    }

    @Test
    public void notRegisteredUserShouldNotBeFoundInUsersSearchResults() {
        AdminPage adminPage = new AdminPage();
        adminPage.enterAdminSearchUsername("user_that_does_not_exist_123");
        adminPage.clickAdminSearchButton();

        assertTrue(adminPage.isNoRecordsFoundPopupVisible());
    }
}
