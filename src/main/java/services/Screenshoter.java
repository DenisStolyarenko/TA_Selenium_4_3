package services;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshoter {
    private static final String SCREENSHOTS_NAME_TPL = "screenshots/scr";

    public static void takeScreenshot(){
        WebDriver driver = Driver.getDriverInstance();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            Logger.info("Saved screenshot: " + screenshotName);
        } catch (IOException e) {
            Logger.error("Failed to make screenshot");
        }
    }
}
