package pages.blocks;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class AuthorizationForm extends HtmlElement{
    @FindBy(xpath = ".//input[@name='login']")
    public TextInput userNameInput;

    @FindBy(xpath = ".//input[@name='password']")
    public TextInput passwordInput;

    @FindBy(xpath = ".//button[@type='submit' and @role='button']")
    public Button loginButton;

    public void logIn(String userName, String password){
        userNameInput.clear();
        userNameInput.sendKeys(userName);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
