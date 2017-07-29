package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import services.Driver;
import services.Screenshoter;

public class LoginPage extends AbstractPage{
    private final String userName = "cool.tests2018";
    private final String pwdName = "1q2w3e4r5T";

    @FindBy(xpath = ".//input[@name='login']")
    private WebElement userNameInput;

    @FindBy(xpath = ".//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = ".//button[@type='submit' and @role='button']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@class='header__username']")
    private WebElement headerUserName;

    public LoginPage open(String BASE_URL){
        Driver.getDriverInstance().get(BASE_URL);
        return this;
    }

    public MainPage login(){
        Screenshoter.takeScreenshot();
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(pwdName);
        loginButton.click();
        Screenshoter.takeScreenshot();
        return new MainPage();
    }

    public String getLoggedUserName(){
        return headerUserName.getText();
    }

    public boolean isUserLogged(){
        if (getLoggedUserName().contains(userName)){
            return true;
        } else return false;
    }

    public boolean isUserNameInputPresent(){
        if (userNameInput.isDisplayed()){
            return true;
        } else return false;
    }
}
