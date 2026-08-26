import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.AdminPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.orange_hrm.driver.DriverSingleton.getDriver;

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
            "Admin, Admin, manda user, Enabled"
    })
    public void registeredUsersShouldBeFoundInUsersSearchResults(String username, String role, String employeeName, String status) {
        AdminPage adminPage = new AdminPage();
        adminPage.enterUsername(username);
        adminPage.clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, employeeName, status), "User " + username + " was not found");
    }

    @Test
    public void notRegisteredUserShouldNotBeFoundInUsersSearchResults() {
        AdminPage adminPage = new AdminPage();
        adminPage.enterUsername("user_that_does_not_exist_123");
        adminPage.clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundPopupVisible());
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, manda user, Enabled"
    })
    public void registeredUsersShouldBeFoundWhenSearchingByAllFilters(String username, String role, String employeeName, String status) {
        AdminPage adminPage = new AdminPage();
        adminPage.enterUsername(username);
        adminPage.expandDropDownOptions("User Role");
        adminPage.chooseDropDownOption(role);
        adminPage.enterEmployeeName(employeeName);
        adminPage.chooseEmployeeName();
        adminPage.expandDropDownOptions("Status");
        adminPage.chooseDropDownOption(status);
        adminPage.clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, employeeName, status), "User " + username + " with role " + role + " with Employee Name " + employeeName + " and status " + status + " was not found");
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, manda user, Enabled"
    })
    public void registeredUsersShouldBeFoundInTableWithoutFiltering(String username, String role, String employeeName, String status) {
        AdminPage adminPage = new AdminPage();

        List<String> actualUserDetails = adminPage.getUserDetailsFromTableWithoutSearching(username);

        assertAll(
                () -> assertEquals(username, actualUserDetails.get(0), "User " + username + " was not found"),
                () -> assertEquals(role, actualUserDetails.get(1), "User Role " + role + " is not correct"),
                () -> assertEquals(employeeName, actualUserDetails.get(2), "Employee Name " + employeeName + " is not correct"),
                () -> assertEquals(status, actualUserDetails.get(3), "Status " + status + " is not correct")
        );
    }
}
