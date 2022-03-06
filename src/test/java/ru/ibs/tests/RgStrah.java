package ru.ibs.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RgStrah {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void before() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 12, 3000);

        driver.get("https://www.rgs.ru");
    }

    @Test
    public void test() {

        WebElement cookie = driver.findElement(By.xpath("//button[contains(text(), 'Хорошо')]"));
        cookie.click();
        WebElement baseMenu = driver.findElement(By.xpath("//a[contains(text(), 'Компаниям')]"));
        baseMenu.click();
        WebElement nextMenu = driver.findElement(By.xpath("//span[contains(text(), 'Здоровье')]"));
        nextMenu.click();
        WebElement targetMenu = driver.findElement(By.xpath("//a[contains(text(), 'Добровольное медицинское страхование')]"));
        targetMenu.click();

        wait.until(ExpectedConditions.textToBe(By.xpath("//h1[@class='title word-breaking title--h2']"), "Добровольное медицинское страхование"));


//        Assert.assertEquals("Страница не загрузилась", "Добровольное медицинское страхование", titleDms.getText());

        WebElement sendButton = driver.findElement(By.xpath("//span[contains(text(), 'Отправить заявку')]"));
        sendButton.click();

    }

    @After
    public void after() {
//        driver.quit();
    }

    private void waitUntilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUntilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitUntilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        waitUntilElementToBeVisible(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "value", value));
        Assert.assertTrue("Поле было заполнено неверно.", checkFlag);
    }

    private void pressDown() {
        try {
            Actions action = new Actions(driver);
            action.keyDown(Keys.ARROW_DOWN).keyUp(Keys.ARROW_DOWN).perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//        try {
//            Thread.sleep(3000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
