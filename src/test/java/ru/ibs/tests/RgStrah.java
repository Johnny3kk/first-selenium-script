package ru.ibs.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15, 3000);

        driver.get("https://www.rgs.ru");
    }

    @Test
    public void test() {

        WebElement cookie = driver.findElement(By.xpath("//button[contains(text(), 'Хорошо')]"));
        cookie.click();
        WebElement baseMenu = driver.findElement(By.xpath("//a[contains(text(), 'Компаниям')]"));
        baseMenu.click();
        waitUntilElementToBeVisible(By.xpath("//span[contains(text(), 'Здоровье')]"));
        WebElement nextMenu = driver.findElement(By.xpath("//span[contains(text(), 'Здоровье')]"));
        waitUntilElementToBeClickable(nextMenu);
        nextMenu.click();
        WebElement targetMenu = driver.findElement(By.xpath("//a[contains(text(), 'Добровольное медицинское страхование')]"));
        waitUntilElementToBeVisible(targetMenu);
        targetMenu.click();

        wait.until(ExpectedConditions.textToBe(By.xpath("//h1[@class='title word-breaking title--h2']"), "Добровольное медицинское страхование"));

        WebElement sendButton = driver.findElement(By.xpath("//span[contains(text(), 'Отправить заявку')]"));
        sendButton.click();

        String fieldXPath = "//input[@name='%s']";
        waitUntilElementToBeClickable(driver.findElement(By.xpath(String.format(fieldXPath, "userName"))));
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "userName"))), "Иванов Иван Иванович");
        fillInputPhoneField(driver.findElement(By.xpath(String.format(fieldXPath, "userTel"))), "911", "111", "1111");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "userEmail"))), "superEmail");
        fillInputField(driver.findElement(By.xpath("//input[@class='vue-dadata__input']")), "Чудогород, Чудоулица 3");

        WebElement check = driver.findElement(By.xpath("//div[@class='checkbox-body form__checkbox']"));
        check.click();
        check.submit();

        waitUntilElementToBeVisible(By.xpath("//span[contains(text(), 'Введите корректный адрес электронной почты')]"));

    }

    @After
    public void after() {
        driver.quit();
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

    private void fillInputPhoneField(WebElement element, String value1, String value2, String value3) {
        scrollToElementJs(element);
        waitUntilElementToBeVisible(element);
        element.click();
        element.clear();
        element.sendKeys(value1);
        element.sendKeys(value2);
        element.sendKeys(value3);
        boolean checkFlag =
                wait.until(ExpectedConditions.attributeContains(
                        element, "value", "+7 (" + value1 + ") " + value2 + "-" + value3));
        Assert.assertTrue("Поле было заполнено неверно.", checkFlag);
    }

}

