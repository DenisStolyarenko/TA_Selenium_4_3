package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import services.Driver;
import services.Screenshoter;

import java.util.List;

public class MainPage extends AbstractPage{
    @FindBy(xpath = "//input[@class='radio-button__control' and @value='icons']")
    private WebElement iconsRadioButton;

    @FindBy(xpath = "//input[@class='radio-button__control' and @value='tile']")
    private WebElement tileRadioButton;

    @FindBy(xpath = "//input[@class='radio-button__control' and @value='list']")
    private WebElement listRadioButton;

    @FindBy(xpath = "//div[@data-nb='resource' and @data-ext='jpg']")
    private List<WebElement> listPicture;

    @FindBy(xpath = "//div[@data-nb='resource']")
    private List<WebElement> listInFolder;

    @FindBy(xpath = "//div[@data-nb='resource' and @title='TestingFolder']")
    private WebElement targetFolder;

    @FindBy(xpath = "//div[@data-nb='resource' and @title='Корзина']")
    private WebElement trash;

    @FindBy(xpath = "//div[@class='header__user']/span[@class='header__username']")
    private WebElement headerUser;

    @FindBy(xpath = "//a[@role='link' and contains(@href,'https://passport.yandex.ru?mode=logout')]")
    private WebElement exitButton;

    @FindBy(xpath = "//a[@id='/disk' and contains(text(),'Диск')]")
    private WebElement baseFolder;

    @FindBy(xpath = "//span[@class='crumbs__current']")
    private WebElement folderName;

    @FindBy(xpath = "//div[@class='b-progressbar__fill']")
    private WebElement progressBar;

    public void changeView(){
        tileRadioButton.click();
        Screenshoter.takeScreenshot();
    }

    public String getPictureName(){
        return listPicture.get(0).getAttribute("title").toString();
    }

    public MainPage selectItems() {
        waitForElementVisible(listPicture.get(0));
        highlightElement(listPicture.get(0));
        Screenshoter.takeScreenshot();
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.clickAndHold(listPicture.get(0)).moveToElement(listPicture.get(1)).release().build().perform();
        unHighlightElement(listPicture.get(0));
        return this;
    }

    public MainPage dragNDropPicture(){
        waitForElementEnabled(listPicture.get(2));
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.dragAndDrop(listPicture.get(2),targetFolder).build().perform();
        return this;
    }

    public MainPage openFolderbyDoubleClicking(WebElement element){
        waitForElementVisibleEnabled(element);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.doubleClick(element).doubleClick().perform();
        return this;
    }

    public MainPage openFolder(String folderName){
        switch (folderName){
            case "TestingFolder":
                openFolderbyDoubleClicking(targetFolder);
                break;
            case "Корзина":
                openFolderbyDoubleClicking(trash);
                break;
            case "Trash":
                openFolderbyDoubleClicking(trash);
                break;
            default:
                System.out.println("Folder didn't find");
                break;
        }

        return this;
    }

    public MainPage dragNDropToTrash(){
        waitForElementVisibleEnabled(trash);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.dragAndDrop(listPicture.get(1),trash).build().perform();
        return this;
    }

    public MainPage goToBaseFolder(){
        waitForElementVisibleEnabled(baseFolder);
        baseFolder.click();
        return this;
    }

    public LoginPage logOut(){
        waitForElementVisibleEnabled(headerUser);
        headerUser.click();
        waitForElementVisible(exitButton);
        exitButton.click();
        return new LoginPage();
    }


    public boolean checkURLofPage(String value){
        String urlz = Driver.getDriverInstance().getCurrentUrl().toString();
        return urlz.contains(value);
    }

    public String getFolderName(){
        waitForElementVisible(folderName);
        return folderName.getText();
    }

    public int checkCountFilesInFolder(){
        waitForElementVisible(folderName);
        return listInFolder.size();
    }
}
