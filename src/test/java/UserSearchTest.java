import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.models.UserData;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.AdminPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserSearchTest extends BaseTest {

    private AdminPage adminPage;

    private DashboardPage dashboardPage;

    //TODO:
    //Czym jest dependency injection -> do napisania Before dla adminPage, DashBoardPage

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled"
    })
    public void registeredUsersShouldBeFoundInUsersSearchResults(String username, String role, String status) {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        String loggedEmployeeName = dashboardPage.getLoggedEmployeeName();

        dashboardPage.goToAdminPage();

        adminPage
                .enterUsername(username)
                .enterEmployeeName(loggedEmployeeName)
                .chooseOption(loggedEmployeeName)
                .clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, loggedEmployeeName, status), "User " + username + " with provided details was not found");
    }

    @Test
    public void registeredUsersShouldBeFoundInUsersSearchResultsByRecord() {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        UserData userData = new UserData(
                "Admin",
                "Admin",
                dashboardPage.getLoggedEmployeeName(),
                "Enabled"
        );

        dashboardPage.goToAdminPage();

        adminPage
                .enterUsername(userData.username())
                .enterEmployeeName(userData.employeeName())
                .chooseOption(userData.employeeName())
                .clickSearchButton();

        assertTrue(adminPage.isUserPresentInTableByRecord(userData), "User " + userData.username() + " with provided details was not found");
    }

    @Test
    public void notRegisteredUserShouldNotBeFoundInUsersSearchResults() {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        dashboardPage.goToAdminPage();

        adminPage
                .enterUsername("user_that_does_not_exist_123")
                .clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled"
    })
    public void registeredUsersShouldBeFoundWhenSearchingByAllFilters(String username, String role, String status) {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        String loggedEmployeeName = dashboardPage.getLoggedEmployeeName();

        dashboardPage.goToAdminPage();

        adminPage
                .enterUsername(username)
                .expandUserRoleOptions()
                .chooseOption(role)
                .enterEmployeeName(loggedEmployeeName)
                .chooseOption(loggedEmployeeName)
                .expandStatusOptions()
                .chooseOption(status)
                .clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, loggedEmployeeName, status), "User " + username + " with role " + role + " with Employee Name " + loggedEmployeeName + " and status " + status + " was not found");
    }

    @Test
    public void registeredUsersShouldBeFoundWhenSearchingByAllFiltersByRecord() {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        UserData userData = new UserData(
                "Admin",
                "Admin",
                dashboardPage.getLoggedEmployeeName(),
                "Enabled"
        );

        dashboardPage.goToAdminPage();

        adminPage
                .enterUsername(userData.username())
                .expandUserRoleOptions()
                .chooseOption(userData.userRole())
                .enterEmployeeName(userData.employeeName())
                .chooseOption(userData.employeeName())
                .expandStatusOptions()
                .chooseOption(userData.status())
                .clickSearchButton();

        assertTrue(adminPage.isUserPresentInTableByRecord(userData), "User " + userData.username() + " with role " + userData.userRole() + " with Employee Name " + userData.employeeName() + " and status " + userData.status() + " was not found");
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled"
    })
    public void registeredUsersShouldBeFoundInTableWithoutFiltering(String username, String role, String status) {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        String loggedEmployeeName = dashboardPage.getLoggedEmployeeName();

        dashboardPage.goToAdminPage();

        List<String> actualUserDetails = adminPage.getUserDetailsFromTable(username);

        assertAll(
                () -> assertEquals(username, actualUserDetails.get(0), "User " + username + " was not found"),
                () -> assertEquals(role, actualUserDetails.get(1), "User Role " + role + " is not correct"),
                () -> assertEquals(loggedEmployeeName, actualUserDetails.get(2), "Employee Name " + loggedEmployeeName + " is not correct"),
                () -> assertEquals(status, actualUserDetails.get(3), "Status " + status + " is not correct")
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled",
            "Admin, Admin, Disabled"
    })
    public void registeredUsersShouldBeFoundInTableWithoutFilteringByRecord2(String username, String role, String status) {

        dashboardPage = new DashboardPage();

        adminPage = new AdminPage();

        UserData expectedUser = new UserData(
                username,
                role,
                dashboardPage.getLoggedEmployeeName(),
                status
        );

        dashboardPage.goToAdminPage();

        assertTrue(adminPage.isUserFoundInTable(expectedUser), "User" + expectedUser + " has not been found");
    }

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled",
            "Tom, ESS, Disabled"
    })
    public void resettingShouldClearAllFiltersFields(String username, String role, String status) {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        String loggedEmployeeName = dashboardPage.getLoggedEmployeeName();

        dashboardPage.goToAdminPage();

        adminPage
                .enterUsername(username)
                .expandUserRoleOptions()
                .chooseOption(role)
                .enterEmployeeName(loggedEmployeeName)
                .chooseOption(loggedEmployeeName)
                .expandStatusOptions()
                .chooseOption(status)
                .clickResetButton();

        assertTrue(adminPage.areAllFiltersFieldsClear(), "Not all the filters fields are empty");
    }

    @Test
    public void resettingShouldClearAllFiltersFieldsByRecord() {

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        UserData userData = new UserData(
                "Admin",
                "Admin",
                dashboardPage.getLoggedEmployeeName(),
                "Disabled"
        );

        dashboardPage.goToAdminPage();

        adminPage
                .enterUsername(userData.username())
                .expandUserRoleOptions()
                .chooseOption(userData.userRole())
                .enterEmployeeName(userData.employeeName())
                .chooseOption(userData.employeeName())
                .expandStatusOptions()
                .chooseOption(userData.status())
                .clickResetButton();

        assertTrue(adminPage.areAllFiltersFieldsClear(), "Not all the filters fields are empty");
    }
}
