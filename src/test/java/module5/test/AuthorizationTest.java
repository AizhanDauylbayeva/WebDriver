package module5.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AuthorizationTest extends BaseSeleniumDemoTest {

    @Test
    public static void assertLogin() {
        WebElement login = driver.findElement(By.id("mailbox:login"));
        login.click();
        login.sendKeys("new_account_2018");
        driver.findElement(By.id("mailbox:password")).sendKeys("password2018");
        driver.findElement(By.xpath("//*[@id='mailbox:domain']/option[4]")).click();
        driver.findElement(By.xpath("//*[@id='mailbox:submit']/input")).click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean status = driver.findElement(By.xpath("//*[@id='PH_user-email']")).isDisplayed();
        Assert.assertTrue(status);
    }

    @Test(dependsOnMethods = "assertLogin")
    public void testSaveNewMail() {
        driver.findElement(By.xpath("//*[@id='b-toolbar__left']//span")).click();
        WebElement addressee = driver.findElement(By.xpath("//textarea[@data-original-name='To']"));
        addressee.sendKeys("ayzhan7797@mail.ru");
        WebElement subject = driver.findElement(By.xpath("//input[@class='b-input' ]"));
        subject.sendKeys("test(module 5)");
        driver.switchTo().frame(0);
        WebElement body = driver.findElement(By.xpath("html/body[@id='tinymce']"));
        body.sendKeys("Hello!");
        driver.switchTo().defaultContent();

        driver.findElement(By.xpath("//div[@data-name='saveDraft']")).click();
        boolean isSaved = driver.findElement(By.xpath("//a[@class='toolbar__message_info__link']")).isDisplayed();
        Assert.assertTrue(isSaved);
    }

    @Test(dependsOnMethods = "testSaveNewMail")
    public void testAddresse(){
        driver.findElement(By.xpath("//div[@class='b-toolbar__message']/a")).click();
        WebElement addressee = driver.findElement(By.xpath("//div[@class='b-datalist__item__addr' and contains(string(), 'ayzhan7797@mail.ru')]"));
        boolean addr = addressee.isDisplayed();
        Assert.assertTrue(addr);
        addressee.click();
    }

    @Test(dependsOnMethods = "testSaveNewMail")
    public void testSubject() {
        WebElement subject = driver.findElement(By.xpath("//a[@data-subject = 'test(module 5)']")); //
        boolean subj = subject.isEnabled();
        Assert.assertTrue(subj);
    }

    @Test(dependsOnMethods = "testSaveNewMail")
    public void testTheDraftContent(){
        driver.switchTo().frame(0);
        boolean body = driver.findElement(By.xpath("//*[@class = 'js-helper js-readmsg-msg' and contains(string(), 'Hello!')]")).isDisplayed();
        Assert.assertTrue(body);
        driver.switchTo().defaultContent();
    }

    @Test(dependsOnMethods = "testTheDraftContent")
    public void sendAndTestTheMail(){
        driver.findElement(By.xpath("//div[@data-name='send']/span")).click();
        driver.findElement(By.xpath("//a[@data-mnemo='drafts']")).click();
        driver.navigate().refresh();

        WebElement select = driver.findElement(By.xpath("//*[@class='b-datalist b-datalist_letters b-datalist_letters_to']//div[@class='b-datalist__item__subj']"));
        List<WebElement> subjects = select.findElements(By.tagName("Subject"));
        for (WebElement subject: subjects){
            boolean subj = ("test(module 5)".equals(subject.getText()));
            Assert.assertFalse(subj);
        }

    }

    @Test(dependsOnMethods = "sendAndTestTheMail")
    public void testTheMailIsInSentFolder(){
        driver.findElement(By.xpath("//a[@href='/messages/sent/']")).click();
        boolean subj = driver.findElement(By.xpath("//*[@class='b-datalist__item__subj' and contains(string(), 'test(module 5)')]")).isDisplayed();
        Assert.assertTrue(subj);
    }

    @Test(dependsOnMethods = "testTheMailIsInSentFolder")
    public void logout(){
        driver.findElement(By.id("PH_logoutLink")).click();
    }
}
