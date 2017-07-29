package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import services.Driver;

public abstract class AbstractPage {
    private static final int WAIT_FOR_ELEMENT_TIMEOUT_SECONDS = 20;

    protected AbstractPage(){
        PageFactory.initElements(Driver.getDriverInstance(), this);
    }

    protected void waitForElementVisible(WebElement element){
        new WebDriverWait(Driver.getDriverInstance(), WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementEnabled(WebElement element){
        new WebDriverWait(Driver.getDriverInstance(), WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementVisibleEnabled(WebElement element){
        new WebDriverWait(Driver.getDriverInstance(), WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until
                (ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(element),
                        ExpectedConditions.elementToBeClickable(element)
                        )
                );
    }

    protected void waitForElementHided(WebElement element){
        new WebDriverWait(Driver.getDriverInstance(), WAIT_FOR_ELEMENT_TIMEOUT_SECONDS).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
    }

    protected void highlightElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriverInstance()).executeScript("arguments[0].style.border='3px solid green'", element);
    }

    protected void unHighlightElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriverInstance()).executeScript("arguments[0].style.border='0px'", element);
    }
}

