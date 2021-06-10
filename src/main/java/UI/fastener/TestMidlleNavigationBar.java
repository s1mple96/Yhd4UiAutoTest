package UI.fastener;

import Utils.WebDriverUtil;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * @author s1mple
 * @create 2021/6/10-15:23
 */
@Test(description = "中部导航栏")
public class TestMidlleNavigationBar {
    WebDriverUtil driver = new WebDriverUtil();

    @BeforeClass
    public void setUp() throws Exception {
        System.out.println("测试开始");
        driver.openBrowser("http://test.fastener.yhdfa.com/", "chrome");
        driver.maxBrowser();
        driver.waitUntilElementVisible(By.cssSelector("a:nth-of-type(2) > img[alt='首购有礼']"), 5);
        System.out.println("首页断言");
        driver.findElementClick("css", ".notLogin .el-button--text");
        System.out.println("点击登录按钮");
        driver.waitUntilElementVisible(By.cssSelector(".LoginModalBox .content"), 5);
        System.out.println("登录模态框断言");
        driver.findElementClearAndSendKeys("css", ".el-form .is-required:nth-of-type(1) .el-input__inner", "qiye@163.com");
        System.out.println("输入账号");
        driver.findElementClearAndSendKeys("css", ".el-form .is-required:nth-of-type(2) .el-input__inner", "123456");
        System.out.println("输入密码");
        driver.findElementClick("css", ".btn .el-button--primary");
        System.out.println("点击登录");
        String text = driver.getTextByXpath("//div[@id='__layout']//div[@class='Home']/div[1]//div[@class='userToolTotal']//p[.='Hi, 怡合达超级管理员']");
        if (text.equals("Hi, 怡合达超级管理员")) {
            System.out.println("登录后断言");
        } else {
            driver.takeScreenshotByNow();
            throw new RuntimeException("登陆失败,断言失败");
        }
    }

    @Test
    public void test(){
        driver.moveToElementByCss(".navs > li:nth-of-type(1) > a[target='_blank']");
        driver.waitUntilElementVisible(By.cssSelector(".homeClass > div > div:nth-of-type(1)"), 2);
        driver.waitUntilPageContainText("内六角圆柱头型");
        driver.moveToElementByCss(".navs > li:nth-of-type(2) > a[target='_blank']");
        driver.waitUntilPageContainText("外六角型");
        driver.moveToElementByCss(".navs > li:nth-of-type(3) > a[target='_blank']");
        driver.waitUntilPageContainText("外六角螺母");
        driver.moveToElementByCss(".navs > li:nth-of-type(4) > a[target='_blank']");
        driver.waitUntilPageContainText("吊环螺钉");
        driver.moveToElementByCss(".navs > li:nth-of-type(5) > a[target='_blank']");
        driver.waitUntilPageContainText("轴用挡圈");
        driver.moveToElementByCss(".navs > li:nth-of-type(6) > a[target='_blank']");
        driver.waitUntilPageContainText("平垫圈");
        driver.moveToElementByCss(".navs > li:nth-of-type(7) > a[target='_blank']");
        driver.waitUntilPageContainText("六角头带挡块");
        driver.moveToElementByCss(".navs > li:nth-of-type(8) > a[target='_blank']");
        driver.waitUntilPageContainText("等长双头螺柱");
        driver.moveToElementByCss(".navs > li:nth-of-type(9) > a[target='_blank']");
        driver.waitUntilPageContainText("树脂螺钉");
        driver.moveToElementByCss(".navs > li:nth-of-type(10) > a[target='_blank']");
        driver.waitUntilPageContainText("压铆螺母");
        driver.moveToElementByCss(".navs > li:nth-of-type(11) > a[target='_blank']");
        driver.waitUntilPageContainText("定位珠");
        driver.moveToElementByCss(".navs > li:nth-of-type(12) > a[target='_blank']");
        driver.waitUntilPageContainText("分度销");
        driver.moveToElementByCss(".navs > li:nth-of-type(13) > a[target='_blank']");
        driver.waitUntilPageContainText("内六角型");
        driver.moveToElementByCss(".navs > li:nth-of-type(14) > a[target='_blank']");
        driver.waitUntilPageContainText("内六角圆柱头组合螺钉");
        driver.moveToElementByCss(".navs > li:nth-of-type(15) > a[target='_blank']");
        driver.waitUntilPageContainText("紧定螺钉用套件");
        System.out.println("中午导航栏栏检索完毕");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        System.out.println("测试结束");
        driver.closeAllBrowser();
    }
}
