package org.example;


import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AppTest {
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized", "disable-popup-blocking");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(05));
        driver.get("https://www.trendyol.com/");
        TimeUnit.SECONDS.sleep(2);
        findElement(By.cssSelector("img[alt='Male'")).click();
    }

    @Test
    public void actionsTest() throws InterruptedException {
        Actions actions = new Actions(driver);

        actions.moveToElement(findElement(By.cssSelector("div[class='account-nav-item user-login-container']")))
                .click()
                .build()
                .perform();
        //findElement(By.cssSelector("div[class='login-button']")).click();
        TimeUnit.SECONDS.sleep(5);

    }

    @Test
    public void login() throws InterruptedException {
        actionsTest();
        WebElement username = findElement(By.id("login-email"));
        username.sendKeys("dorukyurugen@gmail.com");

        WebElement password = findElement(By.id("login-password-input"));
        TimeUnit.SECONDS.sleep(2);
        password.sendKeys("BOTmustafa1" + Keys.ENTER);
        TimeUnit.SECONDS.sleep(10);


    }

    @Test
    public void search() throws InterruptedException {
        login();
        WebElement searchBox = findElement(By.className("search-box"));
        searchBox.sendKeys("Kazak" + Keys.ENTER);
        TimeUnit.SECONDS.sleep(5);
        //findElement(By.cssSelector("div[class='overlay']")).click();

    }

    @Test
    public void addFavorite() throws InterruptedException {
        search();
        List<WebElement> imageList = driver.findElements(By.className("fvrt-btn-wrppr"));
        System.out.println(imageList.size());
        String price = findElement(By.cssSelector("div[class='prc-box-sllng']")).getText();
        System.out.println("Price :" + price);
        WebElement item = imageList.get(9);

        item.click();
    }

    @Test
    public void tenthElement() throws InterruptedException {
        addFavorite();
        List<WebElement> imageList = driver.findElements(By.className("p-card-wrppr"));
        System.out.println(imageList.size());
        WebElement item = imageList.get(9);
        TimeUnit.SECONDS.sleep(3);
        item.click();
        TimeUnit.SECONDS.sleep(3);

    }

    @Test
    public void addToBasket() throws InterruptedException {
        tenthElement();
        String currentWindow = driver.getWindowHandle();
        //findElement(By.cssSelector("button[class='add-to-basket']")).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        Set<String> windowHandles = driver.getWindowHandles();

        for (String window : windowHandles) {
            if (!currentWindow.equals(window)) {
                driver.switchTo().window(window);

            }
        }
        TimeUnit.SECONDS.sleep(5);
        findElement(By.cssSelector("button[class='add-to-basket'")).click();
        TimeUnit.SECONDS.sleep(5);
        driver.switchTo().window(currentWindow);

    }


    public WebElement findElement(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

