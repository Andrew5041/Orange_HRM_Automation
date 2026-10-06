import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.AdminPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserSearchTest extends BaseTest {

    private AdminPage adminPage;

    private DashboardPage dashboardPage;

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
    public void notRegisteredUserShouldNotBeFoundInUsersSearchResults() {
        adminPage
                .enterUsername("user_that_does_not_exist_123")
                .clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());
    }

    //TODO:
    //przerzucic tą metodę do innej klasy, ktora nie korzysta z DashBoardPage

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

    @ParameterizedTest
    @CsvSource({
            "Admin, Admin, Enabled"
    })
    public void registeredUsersShouldBeFoundInTableWithoutFiltering(String username, String role, String status) {

        List<String> actualUserDetails = adminPage.getUserDetailsFromTable(username);

        adminPage = new AdminPage();

        dashboardPage = new DashboardPage();

        String loggedEmployeeName = dashboardPage.getLoggedEmployeeName();

        dashboardPage.goToAdminPage();

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
            "Tom, ESS, Disabled"
    })
    public void resettingShouldClearAllFiltersFields(String username, String role, String status){

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
}
