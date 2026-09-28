import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.LoginPage;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.AdminPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRemoveTest extends BaseTest {

    private AdminPage adminPage;

    @BeforeEach
    public void setUp() {
        LoginPage loginPage = new LoginPage();
        loginPage
                .enterLoginUsername("Admin")
                .enterLoginPassword("admin123");
        DashboardPage dashboardPage = loginPage.clickLoginButton();
        adminPage = dashboardPage.goToAdminPage();
    }

    @Test
    public void removedUserShouldNotBeFoundInUsersSearchResults() {

        List<String> randomUserDetails = adminPage.getRandomUserDetailsFromTable();
        String username = randomUserDetails.get(0);

        adminPage
                .enterUsername(username)
                .clickSearchButton()
                .removeSearchedUser(username)
                .clickConfirmDeletionButton()
                .clickSearchButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());
    }

    @ParameterizedTest
    @CsvSource({
            "TuwaiqNasser111, ESS, Timothy Amiano, Enabled"
    })
    public void removedUserByDeleteSelectedButtonShouldNotBeFoundInUsersSearchResults(String username, String role, String employeeName, String status) {

        adminPage
                .enterUsername(username)
                .clickSearchButton();

        assertTrue(adminPage.isUserPresentInTable(username, role, employeeName, status), "User " + username + " with provided details was not found");

        adminPage
                .markCheckboxForSearchedUser(username)
                .clickDeleteSelectedButton()
                .clickConfirmDeletionButton();

        assertTrue(adminPage.isNoRecordsFoundTextVisible());
    }
}
