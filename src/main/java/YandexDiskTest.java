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
    public void prepareStep() {//todo Если это подготовительный шаг, то он не имеет право носить аннотацию тест
        mainPage.recoveryFromTrash();
        mainPage.goToBaseFolder();
//        mainPage.cleanWorkingFolder("TestingFolder");//todo Закомментированный код не должен находиться в репозитории никогда
//        mainPage.goToBaseFolder();
    }

    @Test(dependsOnMethods = "prepareStep", description = "Selecting test")
    public void selectPictures() throws InterruptedException {
        mainPage.changeView();
        mainPage.selectItemsWithShift();
    }

    @Test(dependsOnMethods = "selectPictures", description = "Drag picture to folder")
    public void dragPicture() {//todo Наименования тестов должны отражать то, что они тестируют. Этот метод не перетаскивает картинку, он выполняет определенную проверку
        //todo Т.е. методы должны в своем названии отражать то, что они делают (это относится ко всем твоим тестовым методам)
        int i = mainPage.openFolder("TestingFolder").checkCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropPicture();
        int j = mainPage.openFolder("TestingFolder").checkCountFilesInFolder();
        Assert.assertNotEquals(i, j, "Failed");//todo Проверку можно сделать определенней - что количество увеличилось на 1.
        //todo Плюс, необходимо написать развернутое сообщение о фейле, которое в случае падения поможет тебе определить, что произошло
    }

    @Test(dependsOnMethods = "dragPicture", description = "Open folder")
    public void openFolderbyDoubleClick() {
        mainPage.goToBaseFolder();//todo Если у тебя есть действия, которые нужно повторять перед каждым тестом, то для того, чтобы убрать дублирующийся код, --->
        //todo можно перенести их в методы с аннотациями @Before... или @After...
        mainPage.openFolder("TestingFolder");
        Assert.assertTrue(mainPage.checkURLofPage("TestingFolder"), "Folder is not found");
    }

    //todo К этому методы относятся все те же замечания, что и к dragPicture()
    @Test(dependsOnMethods = "openFolderbyDoubleClick", description = "Remove picture")
    public void removePicture() {
        mainPage.goToBaseFolder();
        int i = mainPage.openFolder("Корзина").checkCountFilesInFolder();
        mainPage.goToBaseFolder();
        mainPage.dragNDropToTrash();
        int j = mainPage.openFolder("Корзина").checkCountFilesInFolder();
        Assert.assertNotEquals(i, j , "Removing is failed");
    }

    @Test(dependsOnMethods = "removePicture", description = "Logout from Yandex Disk")
    public void logoutFromYandex(){//todo данные действия больше похожи на cleanUp после тестового сценария, чем на тест. Поэтому возможно стоит изменить аннотаци.
        mainPage.logOut();
        Assert.assertTrue(loginPage.isUserNameInputPresent(),"Logout failed");
    }

    @AfterClass(description = "Close browser")
    public void closeBrowser() {
        Driver.closeBrowser();
    }
}
