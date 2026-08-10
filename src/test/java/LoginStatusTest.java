import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import java.util.List;
import static org.orange_hrm.driver.DriverSingleton.quitDriver;

public class LoginStatusTest {

    @AfterEach
    public void quitBrowser() {
        quitDriver();
    }

    @ParameterizedTest
    @CsvSource({
            "AdminE, admin123",
            "AdminD, admin123",
            "Essena, admin123",
            "Essdis, admin123"
    })
    public void shouldLoginOrFailBasedOnUserStatus(String username, String password) {

        String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage(url);

        boolean shouldUserLogin = shouldUserLogin(username);

        if (shouldUserLogin) {
            DashboardPage dashboardPage = loginPage.loginSuccessfully(username, password);
            Assertions.assertTrue(dashboardPage.isBrandBannerPresent(), "User was not successfully logged in");
        } else {
            loginPage.loginWithFailure(username, password);
            Assertions.assertTrue(loginPage.isErrorMessagedDisplayed(), "Error: User should not login");
        }
    }

    public boolean shouldUserLogin(String username) {

        List<String> enabledUsers = List.of("AdminE", "Essena");
        List<String> disabledUsers = List.of("AdminD", "Essdis");

        if (enabledUsers.contains(username)) return true;
        if (disabledUsers.contains(username)) return false;

        throw new IllegalArgumentException("Unrecognized user" + username);
    }
}