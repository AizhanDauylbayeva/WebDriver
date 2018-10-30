import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AuthorizationTest {

    @Test
    public void assertLogin() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://mail.ru/");

        WebElement login = driver.findElement(By.id("mailbox:login"));
        login.click();
        login.sendKeys("new_account_2018");
        driver.findElement(By.id("mailbox:password")).sendKeys("password2018");
        driver.findElement(By.xpath("//*[@id=\"mailbox:domain\"]/option[4]")).click();
        driver.findElement(By.xpath("//*[@id=\"mailbox:submit\"]/input")).click();
        Thread.sleep(3000);

        boolean status = driver.findElement(By.xpath("//*[@id=\"PH_user-email\"]")).isDisplayed();
        Assert.assertTrue(status);
    }
}
