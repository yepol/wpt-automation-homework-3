import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.time.Duration.ofSeconds;


public class Rozetka {

    public static WebDriver driver;
    public static Actions actions;

    public static void main(String[] args) throws InterruptedException, AWTException {

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        actions = new Actions(driver);

        driver.manage().timeouts().implicitlyWait(ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://rozetka.ua");

        findElement("//span[@class='exponea-close-cross']", true, 3000);

        WebElement searchInput = driver.findElement(By.name("search"));
        searchInput.sendKeys("Велосипеди");
        System.out.println("1. Pass. Found all bicycles");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);

        findElement("//button[contains(@class, 'search-form__submit')]", true, 1000);
        addToCartFromCategory("(//li[contains(@class, 'catalog-grid__cell')])[3]");
        System.out.println("2. Pass. Open third product and add it to the cart");

        findElement("fat-menu", true, 3000);
        findElement("//a[contains(text(), 'Дача, сад і город')]", false, 3000);
        findElement("//a[contains(text(), 'Системи поливання')]", true, 3000);
        findElement("//span[contains(text(), 'Таймери для поливу')]", true, 3000);
        System.out.println("3. Pass. Open catalog. Find category.");

        addToCartFromCategory("(//li[contains(@class, 'catalog-grid__cell')])[3]");
        System.out.println("4. Pass. Add third product to catalog.");

        Thread.sleep(3000);
        driver.quit();
    }

    private static void findElement(String findStr, boolean click, Integer time) throws InterruptedException {
        Thread.sleep(time);
        WebElement element;
        if (findStr.contains("//")) {
            element = driver.findElement(By.xpath(findStr));
        } else {
            element = driver.findElement(By.id(findStr));
        }
        if (click) {
            actions.moveToElement(element).click().perform();
        } else {
            actions.moveToElement(element).perform();
        }
    }

    private static void addToCartFromCategory(String xpath) throws InterruptedException {
        findElement(xpath, true, 3000);
        findElement("(//button[contains(@class, 'buy-button')])[1]", true, 5000);
        findElement("(//button[contains(@class, 'modal__close')])[1]", true, 3000);
    }


}

