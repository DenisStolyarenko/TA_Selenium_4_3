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

    //todo это не тест, это подготовительный этап, т.е. лучше использовать аннотацию before... и убрать ассерт
    //todo либо же переместить prepareStep() из этого метода в тот, где это действие действительно нужно
    @Test(description = "Login to Yandex Disk")
    @Parameters({"BASE_URL","userName", "pwdName"})//todo почему капсом?
    public void loginToYandex(String BASE_URL, String userName, String pwdName) {//todo почему капсом?
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
    public void openFolderbyDoubleClick() {//todo некорректное название тестового метода -> переименовать
        mainPage.openFolder("TestingFolder");
        Assert.assertTrue(mainPage.checkURLofPage("TestingFolder"), "Folder is not found");
    }

    @Test(dependsOnMethods = "openFolderbyDoubleClick", description = "Remove picture")
    public void movePicturesToTrash() {//todo некорректное название тестового метода -> переименовать
        mainPage.goToBaseFolder();
        int i = mainPage.openFolder("Корзина").showCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropToTrash();
        int j = mainPage.openFolder("Корзина").showCountFilesInFolder();
        Assert.assertNotEquals(i, j , "Removing is failed");//todo Проверку можно сделать определенней - что количество увеличилось на 1.
    }

    //todo если решишь login перенести в before..., то этот метод придется перенести в after... и также убрать ассерт
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
