import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import services.Driver;

public class YandexDiskTest {
    private MainPage mainPage = new MainPage();
    private LoginPage loginPage = new LoginPage();

    @Test(description = "Login to Yandex Disk")
    @Parameters({"base_url","userName", "pwdName"})
    public void loginToYandex(String base_url, String userName, String pwdName) {
        loginPage.login(base_url, userName, pwdName);
        mainPage.restoreFilesForTesting();
        Assert.assertTrue(mainPage.isUserLogged(userName), "User is not logged");
    }

    @Test(dependsOnMethods = "loginToYandex", description = "Selecting test")
    public void selectPictures() throws InterruptedException {
        mainPage.changeView();
        mainPage.selectItems();
    }

    @Test(dependsOnMethods = "selectPictures", description = "Drag picture to folder")
    public void checkResultOfDragPicture() {
        int i = mainPage.openFolder("TestingFolder").showCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropPicture();
        Assert.assertNotEquals(i, i+1, "The transfer was unsuccessful");
    }

    @Test(dependsOnMethods = "checkResultOfDragPicture", description = "Open folder")
    public void checkOpeningFolder() {
        mainPage.openFolder("TestingFolder");
        Assert.assertTrue(mainPage.checkURLofPage("TestingFolder"), "Folder is not found");
    }

    @Test(dependsOnMethods = "checkOpeningFolder", description = "Remove picture")
    public void checkRemovingPictures() {
        mainPage.goToBaseFolder();
        int i = mainPage.openFolder("Корзина").showCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropToTrash("Корзина");
        Assert.assertNotEquals(i, i + 1 , "Removing is failed");
    }

    @Test(dependsOnMethods = "checkRemovingPictures", description = "Logout from Yandex Disk")
    public void logoutFromYandex(){
        mainPage.logOut();
        Assert.assertTrue(loginPage.isUserNameInputPresent(),"Logout failed");
    }


    @AfterClass(description = "Close browser")
    public void closeBrowser() {
        Driver.closeBrowser();
    }
}
