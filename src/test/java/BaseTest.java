import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.orange_hrm.driver.DriverSingleton.getDriver;
import static org.orange_hrm.driver.DriverSingleton.quitDriver;

public class BaseTest {

    private static final String url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    @BeforeEach
    public void setup(){
        getDriver().get(url);
    }

    @AfterEach
    public void quitBrowser() {
        quitDriver();
    }
}
