package ru.netology.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CardOrderTest {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/linux/chromedriver");
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldFormSend() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.tagName("p")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTextInputFilled() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver
                .findElement(By.cssSelector(".input_invalid"))
                .findElement(By.className("input__inner"))
                .findElement(By.className("input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTelInputFilled() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver
                .findElement(By.cssSelector(".input_invalid"))
                .findElement(By.className("input__inner"))
                .findElement(By.className("input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTextInputFilledValidData() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("123");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver
                .findElement(By.cssSelector(".input_invalid"))
                .findElement(By.className("input__inner"))
                .findElement(By.className("input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldTelInputFilledValidData() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("телефон");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver
                .findElement(By.cssSelector(".input_invalid"))
                .findElement(By.className("input__inner"))
                .findElement(By.className("input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldCheckBoxChecked() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("button")).click();

        String expected = "rgba(255, 92, 92, 1)";
        String actual = driver.findElement(By.cssSelector(".checkbox__text")).getCssValue("color");

        assertEquals(expected, actual);
    }


}
