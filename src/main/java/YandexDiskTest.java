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
    @Parameters({"BASE_URL","userName", "pwdName"})
    public void loginToYandex(String BASE_URL, String userName, String pwdName) {
        loginPage.login(BASE_URL, userName, pwdName);
        mainPage.prepareStep();
        Assert.assertTrue(loginPage.isUserLogged(userName), "User is not logged");
    }

    @Test(dependsOnMethods = "loginToYandex", description = "Selecting test")
    public void selectPictures() throws InterruptedException {
        mainPage.changeView();
        mainPage.selectItemsWithShift();
    }

    @Test(dependsOnMethods = "selectPictures", description = "Drag picture to folder")
    public void checkResultOfDragPicture() {
        int i = mainPage.openFolder("TestingFolder").showCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropPicture();
        Assert.assertNotEquals(i, i+1, "The transfer was unsuccessful");
    }

    @Test(dependsOnMethods = "checkResultOfDragPicture", description = "Open folder")
    public void openFolderbyDoubleClick() {
        mainPage.openFolder("TestingFolder");
        Assert.assertTrue(mainPage.checkURLofPage("TestingFolder"), "Folder is not found");
    }

    @Test(dependsOnMethods = "openFolderbyDoubleClick", description = "Remove picture")
    public void movePicturesToTrash() {
        mainPage.goToBaseFolder();
        int i = mainPage.openFolder("Корзина").showCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropToTrash();
        int j = mainPage.openFolder("Корзина").showCountFilesInFolder();
        Assert.assertNotEquals(i, j , "Removing is failed");
    }

    @Test(dependsOnMethods = "movePicturesToTrash", description = "Logout from Yandex Disk")
    public void logoutFromYandex(){
        mainPage.logOut();
        Assert.assertTrue(loginPage.isUserNameInputPresent(),"Logout failed");
    }


    @AfterClass(description = "Close browser")
    public void closeBrowser() {
        Driver.closeBrowser();
    }
}
