import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import services.Screenshoter;
import services.Driver;

import java.util.concurrent.TimeUnit;

public class YandexDiskTest {
    private final String BASE_URL = "https://disk.yandex.ru";
    private MainPage mainPage = new MainPage();
    private LoginPage loginPage = new LoginPage();


    public String[] arrayOfFileName =
            {"1426364091_412325540.jpg","418034.jpg", "Rogue-One-Star-Wars-Story-Main-Characters.jpg",
            "izgoj-odin-zvezdnye-vojny-istorii-2016-48.jpg", "maxresdefault (1).jpg", "maxresdefault.jpg", "starwarsrogueone_1t.jpg", "Море.jpg"};


    @Test(description = "Login to Yandex Disk")
    public void loginToYandex(){
        loginPage.open(BASE_URL);
        loginPage.login();
//        System.out.println(loginPage.getLoggedUserName());
        Assert.assertTrue(loginPage.isUserLogged(), "User is not logged");
    }

    @Test(dependsOnMethods = "loginToYandex", description = "Selecting test")
    public void selectPictures(){
        mainPage.changeView();
        mainPage.selectItems();
    }

    @Test(dependsOnMethods = "selectPictures", description = "Drag picture to folder")
    public void dragPicture(){
        mainPage.dragNDropPicture();
    }

    @Test(dependsOnMethods = "dragPicture", description = "Open folder")
    public void openFolder(){
        mainPage.doubleClicking();
    }

    @Test(dependsOnMethods = "openFolder", description = "Remove picture")
    public void removePicture(){
        mainPage.goToBaseFolder();
        mainPage.dragNDropToTrash();
    }

    @Test(dependsOnMethods = "removePicture", description = "Logout from Yandex Disk")
    public void logoutFromYandex(){
        mainPage.logOut();
    }

    @AfterClass(description = "Close browser")
    public void closeBrowser() {
        Driver.closeBrowser();
    }
}
