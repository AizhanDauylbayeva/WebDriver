package module5.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthorizationTest extends BeforeSuite {

    @Test
    public static void login() {
        WebElement login = driver.findElement(By.id("mailbox:login"));
        login.click();
        login.sendKeys("new_account_2018");
        driver.findElement(By.id("mailbox:password")).sendKeys("password2018");
        driver.findElement(By.xpath("//*[@id='mailbox:domain']/option[4]")).click();
        driver.findElement(By.xpath("//*[@id='mailbox:submit']/input")).click();
    }

    @Test(dependsOnMethods = "login")
    public void assertLogin() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean status = driver.findElement(By.xpath("//*[@id='PH_user-email']")).isDisplayed();
        Assert.assertTrue(status);
    }

    @Test(dependsOnMethods = "login")
    public void createNewMail() {
        driver.findElement(By.xpath("//*[@id='b-toolbar__left']//span")).click();
        WebElement addressee = driver.findElement(By.xpath("//textarea[@data-original-name='To']"));
        addressee.sendKeys("ayzhan7797@mail.ru");
        WebElement subject = driver.findElement(By.xpath("//input[@class='b-input' ]"));
        subject.sendKeys("test(module 5)");
      //  WebElement body = driver.findElement(By.cssSelector("#tinymce"));
      //  body.sendKeys("Hello!");
    }

    @Test(dependsOnMethods = "createNewMail")
    public void saveTheMail(){
        driver.findElement(By.xpath("//div[@data-name='saveDraft']")).click();
    }

    @Test(dependsOnMethods = "saveTheMail")
    public void verifyTheMailIsSaved(){
        boolean isSaved = driver.findElement(By.xpath("//a[@class='toolbar__message_info__link']")).isDisplayed();
        Assert.assertTrue(isSaved);
    }

    @Test(dependsOnMethods = "verifyTheMailIsSaved")
    public void verifyDraftsFolder(){
        driver.findElement(By.xpath("//div[@class='b-nav__item js-href b-nav__item_active']")).click();

    }
}
