package ru.netology.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldFormSend() {
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetErrorIfNameEmpty() {
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver
                .findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetErrorIfTelEmpty() {
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver
                .findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetErrorIfNameInvalid() {
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("123");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver
                .findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetErrorIfTelInvalid() {
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("телефон");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver
                .findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"))
                .getText();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldGetErrorIfCheckedBoxFalse() {
        driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("input[type='tel")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("button")).click();

        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver
                .findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text"))
                .getText();

        assertEquals(expected, actual);
    }
}
