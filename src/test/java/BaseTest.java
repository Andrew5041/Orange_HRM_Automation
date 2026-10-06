import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.orange_hrm.pages.LoginPage;

import static org.orange_hrm.driver.DriverSingleton.quitDriver;

public class BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setup(){

        loginPage = new LoginPage();

        loginPage
                .enterLoginUsername("Admin")
                .enterLoginPassword("admin123")
                .clickLoginButton();
    }

    @AfterEach
    public void quitBrowser() {
        quitDriver();
    }
}
