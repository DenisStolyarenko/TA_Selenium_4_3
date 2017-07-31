package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import services.Driver;
import services.Screenshoter;

public class LoginPage extends AbstractPage{
    private final String userName = "cool.tests2018";//todo remove hardcode, pass as param
    private final String pwdName = "1q2w3e4r5T";//todo remove hardcode, pass as param

    @FindBy(xpath = "//input[@name='login']")
    private WebElement userNameInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit' and @role='button']")
    private WebElement loginButton;

    @FindBy(xpath = "//span[@class='header__username']")
    private WebElement headerUserName;

    public LoginPage open(String BASE_URL){//todo not appropriate class for this method (method is too general for it so it should be in general class)
        Driver.getDriverInstance().get(BASE_URL);
        return this;
    }

    public MainPage login(){
        Screenshoter.takeScreenshot();
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(pwdName);
        loginButton.click();
        Screenshoter.takeScreenshot();
        return new MainPage();//todo Чем обусловлен возврат методом данного объекта?
    }

    public String getLoggedUserName(){
        return headerUserName.getText();
    }

    //todo Упростить код
    public boolean isUserLogged(){//todo Данный метод относится к другому слою фреймворка
        return getLoggedUserName().contains(userName);
    }

    //todo Упростить код
    public boolean isUserNameInputPresent(){
        return userNameInput.isDisplayed();
    }
}
