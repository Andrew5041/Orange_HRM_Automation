import org.junit.jupiter.api.Test;
import org.orange_hrm.pages.DashboardPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    private DashboardPage dashboardPage;

    @Test
    public void userShouldLoginSuccessfully() {
        dashboardPage = new DashboardPage();
        assertTrue(dashboardPage.isBrandBannerPresent(), "User was not successfully logged in");
    }
}