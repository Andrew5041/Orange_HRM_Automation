import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.orange_hrm.pages.DashboardPage;
import org.orange_hrm.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginStatusTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "AdminEna, admin123, enabled",
            "AdminD, admin123, disabled",
            "EssEna, admin123, enabled",
            "Essdis, admin123, disabled"
    })
    public void shouldLoginOrFailBasedOnUserStatus(String username, String password, String status) {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = loginPage
                .enterLoginUsername(username)
                .enterLoginPassword(password)
                .clickLoginButton();

        if (status.equals("enabled")) {
            assertTrue(dashboardPage.isBrandBannerPresent(), "User was not successfully logged in");
        } else {
            assertTrue(loginPage.isErrorMessagedDisplayed(), "Error: User should not login");
        }
    }
}