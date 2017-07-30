import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MainPage;
import services.Driver;

public class YandexDiskTest {
    private final String BASE_URL = "https://disk.yandex.ru";
    private MainPage mainPage = new MainPage();
    private LoginPage loginPage = new LoginPage();

    @Test(description = "Login to Yandex Disk")
    public void loginToYandex() {
        loginPage.open(BASE_URL);
        loginPage.login();
        Assert.assertTrue(loginPage.isUserLogged(), "User is not logged");
    }

    @Test(dependsOnMethods = "loginToYandex", description = "Prepare for testing")
    public void prepareStep() {
        mainPage.recoveryFromTrash();
        mainPage.goToBaseFolder();
//        mainPage.cleanWorkingFolder("TestingFolder");
//        mainPage.goToBaseFolder();
    }

    @Test(dependsOnMethods = "prepareStep", description = "Selecting test")
    public void selectPictures() throws InterruptedException {
        mainPage.changeView();
        mainPage.selectItemsWithShift();
    }

    @Test(dependsOnMethods = "selectPictures", description = "Drag picture to folder")
    public void dragPicture() {
        int i = mainPage.openFolder("TestingFolder").checkCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropPicture();
        int j = mainPage.openFolder("TestingFolder").checkCountFilesInFolder();
        Assert.assertNotEquals(i, j, "Failed");
    }

    @Test(dependsOnMethods = "dragPicture", description = "Open folder")
    public void openFolderbyDoubleClick() {
        mainPage.goToBaseFolder();
        mainPage.openFolder("TestingFolder");
        Assert.assertTrue(mainPage.checkURLofPage("TestingFolder"), "Folder is not found");
    }

    @Test(dependsOnMethods = "openFolderbyDoubleClick", description = "Remove picture")
    public void removePicture() {
        mainPage.goToBaseFolder();
        int i = mainPage.openFolder("Корзина").checkCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropToTrash();
        int j = mainPage.openFolder("Корзина").checkCountFilesInFolder();
        Assert.assertNotEquals(i, j, "Removing is failed");
    }

    @Test(dependsOnMethods = "removePicture", description = "Logout from Yandex Disk")
    public void logoutFromYandex() {
        mainPage.logOut();
        Assert.assertTrue(loginPage.isUserNameInputPresent(), "Logout failed");
    }

    @AfterClass(description = "Close browser")
    public void closeBrowser() {
        Driver.closeBrowser();
    }
}
