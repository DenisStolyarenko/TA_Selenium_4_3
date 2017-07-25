import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    private WebDriver driver;
//    MainPage mainPage = new MainPage(driver);
//    LoginPage loginPage = new LoginPage(driver);


    public String[] arrayOfFileName =
            {"1426364091_412325540.jpg","418034.jpg", "Rogue-One-Star-Wars-Story-Main-Characters.jpg",
            "izgoj-odin-zvezdnye-vojny-istorii-2016-48.jpg", "maxresdefault (1).jpg", "maxresdefault.jpg", "starwarsrogueone_1t.jpg", "Море.jpg"};

    @BeforeClass
    public void loadStartPage() {
        System.setProperty("webdriver.chrome.driver", "driverbinaries/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test(description = "Login to Yandex Disk")
    public void loginToYandex(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(BASE_URL);
        loginPage.login();
    }

    @Test(dependsOnMethods = "loginToYandex", description = "Preview picture")
    public void chooseAndPreview() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.changeView();
        Thread.sleep(20000);
//        mainPage.selectItems();
//        System.out.println(mainPage.getPictureName());
//        mainPage.hideLastLoadedFiles();
////        MainPage mainPage = new MainPage().closePromo().closeSaveTimeWindow().hideLastLoadedFiles().changeViewOfFiles();
////        MainPage mainPage = new MainPage().changeViewOfFiles();
//
    }

    @Test(dependsOnMethods = "chooseAndPreview", description = "Selecting test")
    public void selectPictures(){
        MainPage mainPage = new MainPage(driver);
        mainPage.selectItems();
    }

//
    @Test(dependsOnMethods = "selectPictures", description = "Drag picture to folder")
    public void dragPicture() throws InterruptedException {
        Thread.sleep(20000);
        MainPage mainPage = new MainPage(driver);
        mainPage.dragNDropPicture();
    }

    @Test(dependsOnMethods = "dragPicture", description = "Open folder")
    public void openFolder() throws InterruptedException {
        Thread.sleep(20000);
        MainPage mainPage = new MainPage(driver);
        mainPage.doubleClicking();
    }
//
//    @Test(description = "Remove picture")
//    public void removeToTrach(){
//
//    }

//    @Test(dependsOnMethods = "chooseAndPreview", description = "Logout from Yandex Disk")
//    public void logoutFromYandex(){
//        LoginPage mainPage = new MainPage().logOut();
//        Screenshoter.takeScreenshot();
//    }

    @AfterClass(description = "Close browser")
    public void closeBrowser() {
//        driver.quit();
    }
}
