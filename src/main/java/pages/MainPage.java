package pages;

import org.openqa.selenium.Keys;
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

//    public MainPage cleanWorkingFolder(String folderName){
//        openFolder(folderName);
//        Actions actions = new Actions(Driver.getDriverInstance());
//        if (listInFolder.size() > 0) {
////            actions.click(listInFolder.get(0)).keyDown(Keys.SHIFT).click(listPicture.get(listInFolder.size() - 1)).keyUp(Keys.SHIFT).build().perform();
//            actions.click(listInFolder.get(0))
//                    .keyDown(Keys.SHIFT)
//                    .click(listPicture.get(listInFolder.size() - 1))
//                    .keyUp(Keys.SHIFT)
//                    .clickAndHold()
//                    .moveToElement(panelForAdditionalFolders)
//                    .release()
//                    .build()
//                    .perform();
//        }
//        return this;
//    }

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
        //todo Код с Action'ами необходимо вынести в отдельный утильный класс.
        //todo В таком случае можно будет вызывать уже подготовленный метод, только передавая в него нужные параметры.
        //todo Данное замечание относится ко всем местам с вызовом Actions
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

    //todo Метод по сути является утильным. Для него и подобных методов необходимо создать отдельный класс и вызывать его уже оттуда
    public MainPage openFolderbyDoubleClicking(WebElement element){
        waitForElementVisibleEnabled(element);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.doubleClick(element).doubleClick().build().perform();
        return this;
    }

    //todo Для чего был создан данный метод? Почему нельзя было обойтись непосредственными вызовами метода openFolderbyDoubleClicking?
    public MainPage openFolder(String folderName){
        switch (folderName){
            case "TestingFolder":
                openFolderbyDoubleClicking(targetFolder);
                break;
            case "Корзина"://todo Просмотреть примеры использования конструкции switch. Данная запись может быть сокращена (присутствует дублирующийся код)
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
        return new LoginPage();//todo Чем обусловлен возврат именно этого объекта?
    }


    //todo Метод слишком общий для данного класса. Необходимо его вынести в более глобальный класс для возможности переиспользования
    //todo Плюс, в нем есть действия, которые не должны находиться в слое Пейдж Обжектов, а именно логика проверки.
    //todo Метод нужно подкорректировать - распределить действия в нем по соответствующим слоям
    public boolean checkURLofPage(String value){
        String urlz = Driver.getDriverInstance().getCurrentUrl().toString();//todo Код в данном методе можно сократить
        return urlz.contains(value);
    }

    public String getFolderName(){
        waitForElementVisible(folderNameElement);
        return folderNameElement.getText();
    }

    //todo Название метода не отражает его суть, т.е. оно некорректно
    public int checkCountFilesInFolder(){
        waitForElementVisible(folderNameElement);
        return listInFolder.size();
    }
}
