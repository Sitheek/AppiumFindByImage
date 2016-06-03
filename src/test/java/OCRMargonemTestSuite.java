import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class OCRMargonemTestSuite {
    private AppiumDriver driver;
    private WebDriverWait wait;
    private OCR OCR;
    private File imgDir;
    private int fightCounter = 1;


    @Before
    public void setUp() throws Exception {

        //Appium setup for the app
        //needs to be installed on target device before the test
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appPackage", "com.garmory.mobilemargonem");
//        capabilities.setCapability("appActivity", "com.garmory.mobilemargonem/com.unity3d.player.UnityPlayerNativeActivity");
        capabilities.setCapability("deviceName", "ApplauseN");
        capabilities.setCapability("platformVersion", "6.0");

        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 10);

        //Sikuli settings
        OCR = new OCR(driver);

        //location of screenshots
        File classpathRoot = new File(System.getProperty("user.dir"));
        imgDir = new File(classpathRoot, "src/main/resources");

        //switch to native app + portrait mode
        driver.context("NATIVE_APP");
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void gatherAllGiftsTest() throws InterruptedException {
        String loginButtonImgLoc = imgDir + "/LoginButton.png";
        String loginFieldImgLoc = imgDir + "/LoginField.png";
        String passwordFieldImgLoc = imgDir + "/PasswordField.png";
        String loginLoginButtonImgloc = imgDir + "/LoginLoginButton.png";
        String heroProfileImgLoc = imgDir + "/HeroProfile.png";
        String fightButtonImgLoc = imgDir + "/FightButton.png";
        String arenaImgLoc = imgDir + "/OrcsArena.png";
        String autoFightImgLoc = imgDir + "/AutoFightButton.png";
        String nextFightButtonImgLoc = imgDir + "/NextFightButton.png";

        long startTime = System.currentTimeMillis();

        OCR.waitUntilImageExists(loginButtonImgLoc, 60);
        OCR.clickByImage(loginButtonImgLoc);

        OCR.waitUntilImageExists(loginFieldImgLoc, 60);
        OCR.clickByImage(loginFieldImgLoc);
        driver.findElementByClassName("android.widget.EditText").sendKeys("Saurgalen");
        driver.findElementByClassName("android.widget.Button").click();

        OCR.waitUntilImageExists(passwordFieldImgLoc, 60);
        OCR.clickByImage(passwordFieldImgLoc);
        driver.findElementByClassName("android.widget.EditText").sendKeys("MAINboard000!");
        driver.findElementByClassName("android.widget.Button").click();

        OCR.waitUntilImageExists(loginLoginButtonImgloc, 60);
        OCR.clickByImage(loginLoginButtonImgloc, 0.5);

        OCR.waitUntilImageExists(heroProfileImgLoc, 60);
        OCR.clickByImage(heroProfileImgLoc, 0.6);

        OCR.waitUntilImageExists(fightButtonImgLoc, 60);
        OCR.clickByImage(fightButtonImgLoc, 0.5);

//        Drag & drop to scroll screen.
//        TouchAction action = new TouchAction(driver);
//        action.press(500,1444).perform();


        OCR.waitUntilImageExists(arenaImgLoc, 60);
        OCR.clickByImage(arenaImgLoc, 0.5);

        long estimatedPrepareTime = System.currentTimeMillis() - startTime;

        System.out.println("Estimated time for fight: "+estimatedPrepareTime+"ms");

        for (int i = 0; i < 2000; i++) {
            long startFightTime = System.currentTimeMillis();
            OCR.waitUntilImageExists(autoFightImgLoc, 90);
            OCR.clickByImage(autoFightImgLoc, 0.8);
            Thread.sleep(10000);
//            driver.tap(1, 1170, 1400, 1);

            OCR.waitUntilImageExists(nextFightButtonImgLoc, 90);
            Thread.sleep(5000);
            OCR.clickByImage(nextFightButtonImgLoc, 0.8);
//            driver.tap(1, 1050, 2300, 1);

            System.out.println("Walka numer: "+ fightCounter);
            fightCounter++;

            long estimatedFightTime = System.currentTimeMillis() - startFightTime;
            System.out.println("Estimated fight time: "+estimatedFightTime+"ms");

        }

        long estimatedLongRunTime = System.currentTimeMillis() - startTime;

        System.out.println("Estimated Time For Fight: "+estimatedPrepareTime+"ms");
        System.out.println("Estimated Whole Run Time: "+estimatedLongRunTime+"ms");
        System.out.println("optA EWRT: "+TimeUnit.MILLISECONDS.toMinutes(estimatedLongRunTime)+"min");

        long EWRTinMinutes = ((estimatedLongRunTime / 1000) / 60);
        System.out.println("optB EWRT: "+EWRTinMinutes+"min");

    }

}
