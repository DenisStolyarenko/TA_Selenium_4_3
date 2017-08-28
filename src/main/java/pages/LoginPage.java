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
        highlitingOfTextInputField(userNameInput, border_3PX_green, border_0PX, userName);
        highlitingOfTextInputField(passwordInput, border_3PX_green, border_0PX, pwdName);
        changeHighlightingOfElement(loginButton, border_3PX_green);
        loginButton.click();
    }

    public boolean isUserNameInputPresent(){
        return userNameInput.isDisplayed();
    }

    private void highlitingOfTextInputField(WebElement element, String first_border, String second_border, String string){
        changeHighlightingOfElement(element, first_border);
        element.sendKeys(string);
        changeHighlightingOfElement(element, second_border);
        Screenshoter.takeScreenshot();
    }
}
