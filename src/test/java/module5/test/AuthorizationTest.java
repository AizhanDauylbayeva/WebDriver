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
        driver.switchTo().frame(0);
        WebElement body = driver.findElement(By.xpath("html/body[@id='tinymce']"));
        body.sendKeys("Hello!");
        driver.switchTo().defaultContent();
    }

    @Test(dependsOnMethods = "createNewMail")
    public void saveTheMail(){
        driver.findElement(By.xpath("//div[@data-name='saveDraft']")).click();
    }

    @Test(dependsOnMethods = "saveTheMail")
    public void testTheMailIsSaved(){
        boolean isSaved = driver.findElement(By.xpath("//a[@class='toolbar__message_info__link']")).isDisplayed();
        Assert.assertTrue(isSaved);
    }

    @Test(dependsOnMethods = "testTheMailIsSaved")
    public void testDraftsFolder() {
        driver.findElement(By.xpath("//div[@class='b-toolbar__message']/a")).click();
    }

    @Test(dependsOnMethods = "testDraftsFolder")
    public void testTheDraftContent(){
        WebElement addressee = driver.findElement(By.xpath("//div[@class='b-datalist__item__addr' and contains(string(), 'ayzhan7797@mail.ru')]"));
        boolean addr = addressee.isDisplayed();
        Assert.assertTrue(addr);
        addressee.click();

        WebElement subject = driver.findElement(By.xpath("//a[@data-subject = 'test(module 5)']")); //
        boolean subj = subject.isEnabled();
        Assert.assertTrue(subj);

        driver.switchTo().frame(0);
        boolean body = driver.findElement(By.xpath("//*[@class = 'js-helper js-readmsg-msg' and contains(string(), 'Hello!')]")).isDisplayed();
        Assert.assertTrue(body);
        driver.switchTo().defaultContent();
    }

    @Test(dependsOnMethods = "testTheDraftContent")
    public void sendTheMail(){
        driver.findElement(By.xpath("//div[@data-name='send']/span")).click();
    }

    @Test(dependsOnMethods = "sendTheMail")
    public void testTheMailDisappearedFromDrafts(){
        driver.findElement(By.xpath("//a[@data-mnemo='drafts']")).click();
        driver.navigate().refresh();
        try{
            driver.findElement(By.xpath("//*[@class='b-datalist__item__subj' and contains(string(), 'test(module 5)')]"));
            System.err.println("The mail still in ‘Drafts’ folder");
        } catch (Exception e){
            System.out.println("The mail successfully disappeared from ‘Drafts’ folder");
        }
    }

    @Test(dependsOnMethods = "testTheMailDisappearedFromDrafts")
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
