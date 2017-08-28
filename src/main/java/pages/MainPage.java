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

    @FindBy(xpath = "//input[@class='radio-button__control' and @value='tile']")
    private WebElement tileRadioButton;

    @FindBy(xpath = "//div[@data-nb='resource' and @data-ext='jpg']")
    private List<WebElement> listPicture;

    @FindBy(xpath = "//div[@data-nb='resource']")
    private List<WebElement> listInFolder;

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

    public void changeView() {
        tileRadioButton.click();
        Screenshoter.takeScreenshot();
    }

//    public String getPictureName() {
//        return listPicture.get(0).getAttribute("title");
//    }

    public void selectItems() {
        ActionsOnObject.selectItemsWithShift(listPicture);
    }


    public void dragNDropPicture(String targetFolder) {
        waitForElementVisible(searchElementbyTitle(targetFolder));
        ActionsOnObject.dragNDropPicture(listPicture, searchElementbyTitle(targetFolder), 2);
    }

    private WebElement searchElementbyTitle(String folderName){
        for (int i = 0; i < listInFolder.size(); i++) {
            if (listInFolder.get(i).getAttribute("title").contains(folderName)) {
                return listInFolder.get(i);
            }
        }
            return null;
    }


    public MainPage openFolder(String folderName) {
        ActionsOnObject.makeDoubleClicking(searchElementbyTitle(folderName));
        return this;
    }

    public void dragNDropToTrash(String folderName) {
        waitForElementVisible(searchElementbyTitle(folderName));
        ActionsOnObject.dragNDropPicture(listPicture, searchElementbyTitle(folderName), 1);
    }

    public void goToBaseFolder() {
        waitForElementVisibleEnabled(baseFolder);
        System.out.println(Driver.getDriverInstance().getWindowHandle());
        waitForOpeningOfWindow(Driver.getDriverInstance().getWindowHandle());
        System.out.println(Driver.getDriverInstance().getWindowHandle());
        baseFolder.click();
    }

    public void logOut() {
        waitForElementVisibleEnabled(headerUser);
        headerUser.click();
        waitForElementVisible(exitButton);
        exitButton.click();
    }

//    public String getFolderName() {
//        waitForElementVisible(folderNameElement);
//        return folderNameElement.getText();
//    }

    public int showCountFilesInFolder() {
        waitForElementVisible(folderNameElement);
        return listInFolder.size();
    }

    public void restoreFilesForTesting(String folderName) {
        recoveryFromTrash(searchElementbyTitle(folderName));
        goToBaseFolder();
    }

    public String getLoggedUserName() {
        return headerUserName.getText();
    }

    public boolean isUserLogged(String userName) {
        return getLoggedUserName().contains(userName);
    }

    public void recoveryFromTrash(WebElement trash) {
        recoveryPicture(trash, listInFolder, listPicture, restoreButton);
    }

    public void recoveryPicture(WebElement trash, List<WebElement> listInFolder, List<WebElement> listPicture, WebElement restoreButton){
        ActionsOnObject.makeDoubleClicking(trash);
        if (listInFolder.size() > 0){
            waitForElementVisible(listInFolder.get(0));
            Actions actions = new Actions(Driver.getDriverInstance());
            actions.click(listInFolder.get(0)).keyDown(Keys.SHIFT).click(listPicture.get(listInFolder.size() - 1)).keyUp(Keys.SHIFT).build().perform();
            Screenshoter.takeScreenshot();
            waitForElementVisible(restoreButton);
            actions.click(restoreButton).build().perform();
        }
    }
}
