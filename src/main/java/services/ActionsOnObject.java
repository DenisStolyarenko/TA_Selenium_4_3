package services;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.AbstractPage;

import java.util.List;

public class ActionsOnObject extends AbstractPage{

    public void recoveryPicture(WebElement trash, List<WebElement> listInFolder, List<WebElement> listPicture, WebElement restoreButton){
        makeDoubleClicking(trash);
        if (listInFolder.size() > 0){
            waitForElementVisible(listInFolder.get(0));
            Actions actions = new Actions(Driver.getDriverInstance());
            actions.click(listInFolder.get(0)).keyDown(Keys.SHIFT).click(listPicture.get(listInFolder.size() - 1)).keyUp(Keys.SHIFT).build().perform();
            Screenshoter.takeScreenshot();
            waitForElementVisible(restoreButton);
            actions.click(restoreButton).build().perform();
        }
    }

    public void selectItemsWithShift(List<WebElement> listPicture){
        waitForElementVisible(listPicture.get(0));
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.click(listPicture.get(0)).keyDown(Keys.SHIFT).click(listPicture.get(2)).keyUp(Keys.SHIFT).build().perform();
        Screenshoter.takeScreenshot();
    }

    public void dragNDropPicture(List<WebElement> list, WebElement target, int elementPosition){
        waitForElementVisibleEnabled(target);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.dragAndDrop(list.get(elementPosition),target).build().perform();
    }

    public void makeDoubleClicking(WebElement element){
        waitForElementVisibleEnabled(element);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.doubleClick(element).doubleClick().build().perform();
    }

}
