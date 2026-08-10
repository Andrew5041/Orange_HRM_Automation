import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import static org.orange_hrm.driver.DriverSingleton.quitDriver;

public class LoginTest {

    @Test
    public void userShouldLoginSuccessfully() {

        String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

        LoginPage loginPage = new LoginPage();

        loginPage.openLoginPage(url);

        DashboardPage dashboardPage = loginPage.login("Admin", "admin123");

        Assertions.assertTrue(dashboardPage.isBrandBannerPresent(), "User was not successfully logged in");

        quitDriver();
    }
}