import org.junit.jupiter.api.Test;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void userShouldLoginSuccessfully() {

        LoginPage loginPage = new LoginPage();
        loginPage.enterLoginUsername("Admin");
        loginPage.enterLoginPassword("admin123");
        loginPage.clickLoginButton();

        DashboardPage dashboardPage = new DashboardPage();

        assertTrue(dashboardPage.isBrandBannerPresent(), "User was not successfully logged in");
    }
}