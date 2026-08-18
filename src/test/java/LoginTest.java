import org.junit.jupiter.api.Test;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void userShouldLoginSuccessfully() {

        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        loginPage.loginSuccessfully("Admin", "admin123");

        assertTrue(dashboardPage.isBrandBannerPresent(), "User was not successfully logged in");
    }
}