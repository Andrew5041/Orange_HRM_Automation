import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.AdminPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserSearchTest extends BaseTest {

    private String loggedEmployeeName;
    private AdminPage adminPage;

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
            "Admin, Admin, Enabled"
    })
    public void registeredUsersShouldBeFoundInUsersSearchResults(String username, String role, String status) {
        adminPage.enterUsername(username);
        adminPage.enterEmployeeName(loggedEmployeeName);
        adminPage.chooseOption(loggedEmployeeName);
        adminPage.clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, loggedEmployeeName, status), "User " + username + " with provided details was not found");
    }

    @Test
    public void notRegisteredUserShouldNotBeFoundInUsersSearchResults() {
        adminPage.enterUsername("user_that_does_not_exist_123");
        adminPage.clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundPopupVisible());
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled"
    })
    public void registeredUsersShouldBeFoundWhenSearchingByAllFilters(String username, String role, String status) {
        adminPage.enterUsername(username);
        adminPage.expandUserRoleOptions();
        adminPage.chooseOption(role);
        adminPage.enterEmployeeName(loggedEmployeeName);
        adminPage.chooseOption(loggedEmployeeName);
        adminPage.expandStatusOptions();
        adminPage.chooseOption(status);
        adminPage.clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, loggedEmployeeName, status), "User " + username + " with role " + role + " with Employee Name " + loggedEmployeeName + " and status " + status + " was not found");
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled"
    })
    public void registeredUsersShouldBeFoundInTableWithoutFiltering(String username, String role, String status) {

        List<String> actualUserDetails = adminPage.getUserDetailsFromTable(username);

        assertAll(
                () -> assertEquals(username, actualUserDetails.get(0), "User " + username + " was not found"),
                () -> assertEquals(role, actualUserDetails.get(1), "User Role " + role + " is not correct"),
                () -> assertEquals(loggedEmployeeName, actualUserDetails.get(2), "Employee Name " + loggedEmployeeName + " is not correct"),
                () -> assertEquals(status, actualUserDetails.get(3), "Status " + status + " is not correct")
        );
    }
}
