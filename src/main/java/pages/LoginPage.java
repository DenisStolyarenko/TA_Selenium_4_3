package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import services.Driver;
import services.Screenshoter;

public class LoginPage extends AbstractPage{

    @FindBy(xpath = "//input[@name='login']")
    private WebElement userNameInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit' and @role='button']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@class='header__username']")
    private WebElement headerUserName;

    public void login(String BASE_URL, String userName, String pwdName){
        open(BASE_URL);
        Screenshoter.takeScreenshot();
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(pwdName);
        loginButton.click();
        Screenshoter.takeScreenshot();
    }

    public String getLoggedUserName(){
        return headerUserName.getText();
    }

    //todo метод относится к другому слою фреймворка -> перенести
    public boolean isUserLogged(String userName){
        return getLoggedUserName().contains(userName);
    }

    public boolean isUserNameInputPresent(){
        return userNameInput.isDisplayed();
    }
}
