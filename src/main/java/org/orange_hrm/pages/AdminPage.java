package org.orange_hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.orange_hrm.driver.DriverSingleton.getDriver;

public class AdminPage {

    @FindBy(xpath = "//label[text()='Username']/ancestor::div[contains(@class, 'oxd-input-group')]//input")
    private WebElement usernameInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='oxd-toast-content oxd-toast-content--info']//p[text()='No Records Found']")
    private WebElement noRecordsFoundPopup;

/*
    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell']/div")
    private List<WebElement> usersList;
*/

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']")
    private List<WebElement> usersList;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][2]/div")
    private List<WebElement> usernamesList;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][3]/div")
    private List<WebElement> userRolesList;

    @FindBy(xpath = "//div[contains(@class, 'oxd-table-body')]//div[@role='row']/div[@role='cell'][5]/div")
    private List<WebElement> userStatusesList;

    public AdminPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public AdminPage enterAdminSearchUsername(String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public AdminPage clickAdminSearchButton() {
        searchButton.click();
        return this;
    }
    //TODO:
    //Porozdzielac na odzielne metody
    //W jaki sposob zrobic jedna metode ktora by robiła isUserPresent w tabeli i zwracała by true jeżeli wszystkie parametry usera są spełnione. Jeden stream

    public boolean isUsernamePresentInTable(String username) {
        System.out.println(username);
        System.out.println("Co nam zwraca: " + usernamesList.stream().anyMatch(e -> e.getText().trim().equals(username)));
        return usernamesList.stream().anyMatch(e -> e.getText().trim().equals(username));
    }

    public boolean isUserRolePresentInTable(String role) {
        System.out.println(role);
        System.out.println("Co nam zwraca: " + usernamesList.stream().anyMatch(e -> e.getText().trim().equals(role)));
        return userRolesList.stream().anyMatch(e -> e.getText().trim().equals(role));
    }

    public boolean isUserStatusPresentInTable(String status) {
        return userStatusesList.stream().anyMatch(e -> e.getText().trim().equals(status));
    }

    public boolean isUserPresentInTable(String username, String role, String status) {

        for (WebElement row : usersList) {
            String text = row.getText();
            System.out.println(text);
            if (text.contains(username) && text.contains(role) && text.contains(status)) {
                return true;
            }
        }
        return false;


/*        return usersList.stream()
                .map(WebElement::getText)
                .anyMatch(text -> text.contains(username) &&
                        text.contains(role) &&
                        text.contains(status));

    }*/
    }






    public boolean isNoRecordsFoundPopupVisible() {
        return noRecordsFoundPopup.isDisplayed();
    }
}
