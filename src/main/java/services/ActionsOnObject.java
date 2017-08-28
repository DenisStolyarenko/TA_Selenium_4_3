package services;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class ActionsOnObject extends BaseActions{

    public static void selectItemsWithShift(List<WebElement> listPicture){
        waitForElementVisible(listPicture.get(0));
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.click(listPicture.get(0)).keyDown(Keys.SHIFT).click(listPicture.get(2)).keyUp(Keys.SHIFT).build().perform();
        Screenshoter.takeScreenshot();
    }

    public static void dragNDropPicture(List<WebElement> list, WebElement target, int elementPosition){
        waitForElementVisibleEnabled(target);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.dragAndDrop(list.get(elementPosition),target).build().perform();
    }

    public static void makeDoubleClicking(WebElement element){
        waitForElementVisibleEnabled(element);
        Actions actions = new Actions(Driver.getDriverInstance());
        actions.doubleClick(element).doubleClick().build().perform();
    }

}
