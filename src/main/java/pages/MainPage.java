package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import services.Driver;
import services.Screenshoter;

import java.util.List;

//todo вынести весь код с экшенами из этого класса в отдельный утильный класс
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

    //todo хардкод имени папки! На будущее - привязываться к какому-то конкретному контенту нельзя. -->
    //todo Иначе ты будешь привязан только к конкретному окружению. Да и вообще, в случае каких-либо изменений -->
    //todo этого самого контента (что на практике очень часто происходит, ведь ты не единственный, -->
    //todo кто работает с системой), тесты поломаются
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

    @FindBy(xpath = "//div[@class='nb-panel__content nb-panel__content_icon']")
    private WebElement panelForAdditionalFolders;

    @FindBy(xpath = "//div[@class='b-aside-tree__inner-line' and contains(text(),'Яндекс.Диск')]")
    private WebElement yandexDiskFolder;

    @FindBy(xpath = "//span[@class='crumbs__current']")
    private WebElement folderNameElement;

    @FindBy(xpath = "//div[@class='b-progressbar__fill']")
    private WebElement progressBar;

    @FindBy(xpath = "//span[@class='_nb-button-content' and contains(text(),'Восстановить')]")
    private WebElement restoreButton;

    public void changeView(){
        tileRadioButton.click();
        Screenshoter.takeScreenshot();
    }

    public String getPictureName(){
        return listPicture.get(0).getAttribute("title").toString();
    }

    public MainPage recoveryFromTrash(){
        openFolder("Корзина");
        if (listInFolder.size() > 0){
            waitForElementVisible(listInFolder.get(0));
            Actions actions = new Actions(Driver.getDriverInstance());
            actions.click(listInFolder.get(0)).keyDown(Keys.SHIFT).click(listPicture.get(listInFolder.size() - 1)).keyUp(Keys.SHIFT).build().perform();
            Screenshoter.takeScreenshot();
            waitForElementVisible(restoreButton);
            actions.click(restoreButton).build().perform();
        }
        return this;
    }

    public MainPage selectItemsWithShift(){
        waitForElementVisible(listPicture.get(0));
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.click(listPicture.get(0)).keyDown(Keys.SHIFT).click(listPicture.get(2)).keyUp(Keys.SHIFT).build().perform();
        Screenshoter.takeScreenshot();
        return this;
    }

    public MainPage dragNDropPicture(){
        waitForElementEnabled(listPicture.get(2));
        highlightElement(listPicture.get(2));
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.dragAndDrop(listPicture.get(2),targetFolder).build().perform();
        return this;
    }

    //todo метод утильный. вынести его
    public MainPage makeDoubleClicking(WebElement element){
        waitForElementVisibleEnabled(element);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.doubleClick(element).doubleClick().build().perform();
        return this;
    }

    //todo если назовешь хоть один аргумент в пользу полезности этого метода, то можешь его оставить
    public MainPage openFolder(String folderName){
        switch (folderName){
            case "TestingFolder":
                makeDoubleClicking(targetFolder);
                break;
            case "Корзина":
            case "Trash":
                makeDoubleClicking(trash);
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
        return new LoginPage();//todo почему ты возвращаешь новый объект страницы? Почему нельзя вернуть конкретно этот?
    }

    public String getFolderName(){
        waitForElementVisible(folderNameElement);
        return folderNameElement.getText();
    }

    public int showCountFilesInFolder(){
        waitForElementVisible(folderNameElement);
        return listInFolder.size();
    }

    //todo переименовать, чтобы отражал суть
    public void prepareStep() {
        recoveryFromTrash();
        goToBaseFolder();
    }
}
