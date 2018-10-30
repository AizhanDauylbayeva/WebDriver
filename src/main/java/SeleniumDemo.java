import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

class SeleniumDemo {

    private WebDriver driver;

    void invokeBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://mail.ru/");
    }

    void login() {
        WebElement login = driver.findElement(By.id("mailbox:login"));
        login.click();
        login.sendKeys("new_account_2018");
        driver.findElement(By.id("mailbox:password")).sendKeys("password2018");
        driver.findElement(By.xpath("//*[@id=\"mailbox:domain\"]/option[4]")).click();
        driver.findElement(By.xpath("//*[@id=\"mailbox:submit\"]/input")).click();

    }
}
