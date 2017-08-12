package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import services.Screenshoter;

public class LoginPage extends AbstractPage{

    @FindBy(xpath = "//input[@name='login']")
    private WebElement userNameInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit' and @role='button']")
    private WebElement loginButton;


    public void login(String BASE_URL, String userName, String pwdName){
        open(BASE_URL);
        Screenshoter.takeScreenshot();
        changeHighlightingOfElement(userNameInput, style1);
        userNameInput.sendKeys(userName);
        changeHighlightingOfElement(userNameInput, style0);
        changeHighlightingOfElement(passwordInput, style1);
        passwordInput.sendKeys(pwdName);
        changeHighlightingOfElement(passwordInput, style0);
        changeHighlightingOfElement(loginButton, style1);
        loginButton.click();
        Screenshoter.takeScreenshot();
    }

    public boolean isUserNameInputPresent(){
        return userNameInput.isDisplayed();
    }
}
