package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import services.Driver;
import services.Screenshoter;

import javax.swing.*;
import java.util.List;

public class MainPage extends AbstractPage{
    @FindBy(xpath = ".//input[@class='radio-button__control' and @value='icons']")
    WebElement iconsRadioButton;

    @FindBy(xpath = ".//input[@class='radio-button__control' and @value='tile']")
    WebElement tileRadioButton;

    @FindBy(xpath = ".//input[@class='radio-button__control' and @value='list']")
    WebElement listRadioButton;

    @FindBy(xpath = ".//div[@data-nb='resource' and @data-ext='jpg']")
    List<WebElement> listPicture;

    @FindBy(xpath = "//div[@data-nb='resource' and @title='TestingFolder']")
    WebElement targetFolder;

//    public MainPage(WebDriver driver){
//        super(driver);
//    }

    public MainPage changeView(){
        iconsRadioButton.click();
        tileRadioButton.click();
        return this;
    }

    public String getPictureName(){
        return listPicture.get(0).getAttribute("title").toString();
    }

    public MainPage selectItems() {
//        Thread.sleep(20000);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.clickAndHold(listPicture.get(0)).moveToElement(listPicture.get(3)).release().build().perform();
        return this;
    }

    public MainPage dragNDropPicture(){
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.dragAndDrop(listPicture.get(2),targetFolder).build().perform();
        return this;
    }
    public MainPage doubleClicking(){
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.doubleClick(targetFolder).doubleClick().perform();
        return this;
    }
//
//
//    public MainPage changeViewOfFiles(){
////        waitForElementVisible(ICONS_RADIO_BUTTON_LOCATOR);
////        highlightElement(ICONS_RADIO_BUTTON_LOCATOR);
//        Driver.getDriverInstance().findElement(ICONS_RADIO_BUTTON_LOCATOR).click();
////        unHighlightElement(HEADER_USERNAME_LOCATOR);
////        if(!Driver.getDriverInstance().findElement(ICONS_RADIO_BUTTON_LOCATOR).getAttribute("value").contains("tile")){
////            Driver.getDriverInstance().findElement(ICONS_RADIO_BUTTON_LOCATOR).click();
////        }
//        return this;
//    }
//
//    public MainPage hideLastLoadedFiles(){
//        Driver.getDriverInstance().findElement(HIDE_SHOW_LAST_LOADED_FILES_LOCATOR).click();
////        waitForElementVisibleEnabled(HIDE_SHOW_LAST_LOADED_FILES_LOCATOR);
////        if (Driver.getDriverInstance().findElement(HIDE_SHOW_LAST_LOADED_FILES_LOCATOR).getAttribute("aria-pressed").contains("true")){
////            Driver.getDriverInstance().findElement(HIDE_SHOW_LAST_LOADED_FILES_LOCATOR).click();
////        }
//        return this;
//    }
//
//    public MainPage closePromo(){
////        waitForElementVisibleEnabled(PROMO_LOCATOR);
////        if(isElementPresent(PROMO_LOCATOR)){
////            Driver.getDriverInstance().findElement(PROMO_LOCATOR).click();
////        }
//        return this;
//    }
//
//    public MainPage closeSaveTimeWindow(){
////        waitForElementVisibleEnabled(SAVE_TIME_WINDOW_LOCATOR);
////        if(isElementPresent(SAVE_TIME_WINDOW_LOCATOR)){
////            Driver.getDriverInstance().findElement(CLOSE_SAVE_TIME_WINDOW_LOCATOR).click();
////        }
//        return this;
//    }
//
//    public LoginPage logOut(){
//        waitForElementVisibleEnabled(HEADER_USERNAME_LOCATOR);
////        highlightElement(HEADER_USERNAME_LOCATOR);
//        Driver.getDriverInstance().findElement(HEADER_USERNAME_LOCATOR).click();
////        unHighlightElement(HEADER_USERNAME_LOCATOR);
////        driver.findElement(EXIT_LOCATOR).click();
//        String executeString = Driver.getDriverInstance().findElement(EXIT_LOCATOR).getAttribute("href");
//        ((JavascriptExecutor)Driver.getDriverInstance()).executeScript(executeString);
//        Screenshoter.takeScreenshot();
//        return new LoginPage();
//    }


}
