import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstSeleniumTest {
    public WebDriver driver;
    private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final String configPath = rootPath + "config.xml";
    private final Properties userProps = new Properties();
    @Before
    public void setup() throws IOException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            userProps.loadFromXML(fis);
        }catch (FileNotFoundException ex) {
            // FileNotFoundException catch is optional and can be collapsed
            System.out.println("Cannot find the file: " + configPath);
        } catch (IOException ex) {

        }
    }

    @Test
    public void testLogin() {
        MainPage mainPage = new MainPage(this.driver);
        WebElement loginButton = mainPage.waitAndReturnElement(By.xpath("//button[@type='submit']"));
        mainPage.setClassBy(By.xpath("//input[@name='email']"));
        mainPage.search(userProps.getProperty("username"));//"lilystudent05@gmail.com");
        mainPage.setClassBy(By.xpath("//*[@id=\"__next\"]/div[2]/div/div/form/div[2]/input"));
        mainPage.search(userProps.getProperty("password"));//"Y&chQqA.PSf&%3k");
        loginButton.click();
        WebElement logo = mainPage.waitAndReturnElement(By.xpath("//nav[contains(@class, 'MuiBox-root')]//img[contains(@alt, 'Logo')]"));
        assertEquals(mainPage.driver.getTitle(),"Magic Tools - MagicSchool.ai");
        assertTrue(logo.isDisplayed());
    }
    @Test
    public void testLogout() {
        MainPage mainPage = new MainPage(this.driver);
        WebElement loginButton = mainPage.waitAndReturnElement(By.xpath("//button[@type='submit']"));
        mainPage.setClassBy(By.xpath("//input[@name='email']"));
        mainPage.search(userProps.getProperty("username"));
        mainPage.setClassBy(By.xpath("//*[@id=\"__next\"]/div[2]/div/div/form/div[2]/input"));
        mainPage.search(userProps.getProperty("password"));
        loginButton.click();
        WebElement settingsButton = mainPage.waitAndReturnElement(By.xpath("//div[contains(@class, 'MuiChip-clickable')]"));
        settingsButton.click();
        mainPage.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
        WebElement logoutButton = (WebElement)new WebDriverWait(mainPage.driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div/div/div/div/div[2]/nav/div[3]")));
        logoutButton.click();
        WebElement logo = mainPage.waitAndReturnElement(By.xpath("//div[contains(@class, 'flex')]//img[contains(@alt, 'Logo')]"));
        assertTrue(logo.isDisplayed());
    }

    @Test
    public void testSendForm() {
        MainPage mainPage = new MainPage(this.driver);
        WebElement loginButton = mainPage.waitAndReturnElement(By.xpath("//button[@type='submit']"));
        mainPage.setClassBy(By.xpath("//input[@name='email']"));
        mainPage.search(userProps.getProperty("username"));
        mainPage.setClassBy(By.xpath("//*[@id=\"__next\"]/div[2]/div/div/form/div[2]/input"));
        mainPage.search(userProps.getProperty("password"));
        loginButton.click();
        mainPage.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        WebElement toolButton = (WebElement)new WebDriverWait(mainPage.driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-tour-id='professional-email']")));
        toolButton.click();
        mainPage.setClassBy(By.xpath("//textarea[@name='content']"));
        mainPage.search("The assignment is almost done");
        WebElement generateButton = mainPage.waitAndReturnElement(By.xpath("//button[@type='submit']"));
        generateButton.click();
        WebElement chat = mainPage.waitAndReturnElement(By.xpath("//div[@data-tour-id='chat-input-panel']"));
        assertTrue(chat.isDisplayed());
    }

    @Test
    public void testStaticPage() {
        MainPage mainPage = new MainPage(this.driver);
        WebElement loginButton = mainPage.waitAndReturnElement(By.xpath("//button[@type='submit']"));
        mainPage.setClassBy(By.xpath("//input[@name='email']"));
        mainPage.search(userProps.getProperty("username"));
        mainPage.setClassBy(By.xpath("//*[@id=\"__next\"]/div[2]/div/div/form/div[2]/input"));
        mainPage.search(userProps.getProperty("password"));
        loginButton.click();
        WebElement historyButton = mainPage.waitAndReturnElement(By.xpath("//html/body/div[1]/div[3]/nav/div/div/div/div/div[4]/a/button"));
        historyButton.click();
        WebElement staticBlock = mainPage.waitAndReturnElement(By.xpath("/html/body/div[1]/div[3]/main/div/div/div/div[1]"));
        WebElement blockText = mainPage.waitAndReturnElement(By.xpath("/html/body/div[1]/div[3]/main/div/div/div/div[1]/div/div[1]/div"));
        assertTrue(staticBlock.isDisplayed());
        assertEquals(blockText.getText(), "History Filters:");
    }
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

}
