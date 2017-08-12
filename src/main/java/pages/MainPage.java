package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import services.ActionsOnObject;
import services.Driver;
import services.Screenshoter;

import java.util.List;

public class MainPage extends AbstractPage {
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

    @FindBy(xpath = "//span[@class='header__username']")
    private WebElement headerUserName;

    ActionsOnObject actionsOnObject = new ActionsOnObject();

    public void changeView() {
        tileRadioButton.click();
        Screenshoter.takeScreenshot();
    }

    public String getPictureName() {
        return listPicture.get(0).getAttribute("title");
    }

    public void recoveryFromTrash() {
        actionsOnObject.recoveryPicture(trash, listInFolder, listPicture, restoreButton);
//        return this;
    }

    public void selectItems() {
        actionsOnObject.selectItemsWithShift(listPicture);
//        return this;
    }


    public void dragNDropPicture() {
        actionsOnObject.dragNDropPicture(listPicture, targetFolder, 2);
//        return this;
    }


    public MainPage openFolder(String folderName) {
        for (int i = 0; i < listInFolder.size(); i++) {
            if (listInFolder.get(i).getAttribute("title").contains(folderName)) {
                actionsOnObject.makeDoubleClicking(listInFolder.get(i));
            }

        }
        return this;
    }

    public void dragNDropToTrash() {
        actionsOnObject.dragNDropPicture(listPicture, trash, 1);
//        return this;
    }

    public void goToBaseFolder() {
        waitForElementVisibleEnabled(baseFolder);
        baseFolder.click();
//        return this;
    }

    public void logOut() {
        waitForElementVisibleEnabled(headerUser);
        headerUser.click();
        waitForElementVisible(exitButton);
        exitButton.click();
//        return new LoginPage();
    }

    public String getFolderName() {
        waitForElementVisible(folderNameElement);
        return folderNameElement.getText();
    }

    public int showCountFilesInFolder() {
        waitForElementVisible(folderNameElement);
        return listInFolder.size();
    }

    public void restoreFilesForTesting() {
        recoveryFromTrash();
        goToBaseFolder();
    }

    public String getLoggedUserName() {
        return headerUserName.getText();
    }

    public boolean isUserLogged(String userName) {
        return getLoggedUserName().contains(userName);
    }
}
