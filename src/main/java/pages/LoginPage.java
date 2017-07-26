package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import services.Driver;

public class LoginPage extends AbstractPage{
    private final String userName = "cool.tests2018";
    private final String pwdName = "1q2w3e4r5T";

    @FindBy(xpath = ".//input[@name='login']")
    WebElement userNameInput;

    @FindBy(xpath = ".//input[@name='password']")
    WebElement passwordInput;

    @FindBy(xpath = ".//button[@type='submit' and @role='button']")
    WebElement loginButton;

//    public LoginPage(WebDriver driver){
//        super(driver);
//    }

    public LoginPage open(String BASE_URL){
        Driver.getDriverInstance().get(BASE_URL);
        return this;
    }

    public MainPage login(){
        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(pwdName);
        loginButton.click();
        return new MainPage();
    }
}
