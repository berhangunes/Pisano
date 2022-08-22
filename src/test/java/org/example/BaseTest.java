package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static WebDriver driver;
    protected static Actions actions;
    protected WebDriverWait wait;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    DesiredCapabilities capabilities;
    ChromeOptions chromeOptions;
    FirefoxOptions firefoxOptions;

    String browserName = "chrome";
    String selectPlatform = "mac";

    public static final String DEFAULT_DIRECTORY_PATH = "locators";
    ConcurrentMap<String, Object> elementMapList = new ConcurrentHashMap<>();

    @BeforeScenario
    public void setUp() {
        logger.info("************************************  BeforeScenario  ************************************");

        logger.info("Local cihazda " + selectPlatform + " ortamında " + browserName + " browserında test ayağa kalkacak");
        if ("win".equalsIgnoreCase(selectPlatform)) {
            if ("chrome".equalsIgnoreCase(browserName)) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            } else if ("firefox".equalsIgnoreCase(browserName)) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            }
        } else if ("mac".equalsIgnoreCase(selectPlatform)) {
            if ("chrome".equalsIgnoreCase(browserName)) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            } else if ("firefox".equalsIgnoreCase(browserName)) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions());
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            } else if ("safari".equalsIgnoreCase(browserName)) {
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            }
            actions = new Actions(driver);
        }
    }

    @AfterScenario
    public void tearDown() {
        driver.quit();
    }

    public void initMap(File[] fileList) {
        Type elementType = new TypeToken<List<Elements>>() {
        }.getType();
        Gson gson = new Gson();
        List<Elements> elementsList = null;
        for (File file : fileList) {
            try {
                elementsList = gson
                        .fromJson(new FileReader(file), elementType);
                elementsList.parallelStream()
                        .forEach(elements -> elementMapList.put(elements.getKey(), elements));
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e);
            }
        }
    }

    public File[] getFileList() {
        File[] fileList = new File(
                this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH).getFile())
                .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));
        if (fileList == null) {
            logger.warn(
                    "File Directory Is Not Found! Please Check Directory Location. Default Directory Path = {}",
                    DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException();
        }
        return fileList;
    }

    public ChromeOptions chromeOptions() {
        chromeOptions = new ChromeOptions();
        capabilities = DesiredCapabilities.chrome();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addArguments("--kiosk");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--start-fullscreen");
        chromeOptions.merge(capabilities);
        return chromeOptions;
    }

    public FirefoxOptions firefoxOptions() {
        firefoxOptions = new FirefoxOptions();
        capabilities = DesiredCapabilities.firefox();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        firefoxOptions.addArguments("--kiosk");
        firefoxOptions.addArguments("--disable-notifications");
        firefoxOptions.addArguments("--start-fullscreen");
        FirefoxProfile profile = new FirefoxProfile();
        capabilities.setCapability(FirefoxDriver.PROFILE, profile);
        capabilities.setCapability("marionette", true);
        firefoxOptions.merge(capabilities);
        return firefoxOptions;
    }

    public Elements findElementInfoByKey(String key) {
        return (Elements) elementMapList.get(key);
    }

}
