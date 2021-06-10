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
@Test(description = "轮播图")
public class TestBanner {
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
    public void test() {
        driver.findElementClick("css", "li:nth-of-type(1) > .el-carousel__button");
        driver.getSelectStatByCss("li:nth-of-type(1) > .el-carousel__button");
        driver.findElementClick("css", "li:nth-of-type(2) > .el-carousel__button");
        driver.getSelectStatByCss("li:nth-of-type(2) > .el-carousel__button");
        driver.findElementClick("css", "li:nth-of-type(3) > .el-carousel__button");
        driver.getSelectStatByCss("li:nth-of-type(3) > .el-carousel__button");
        driver.findElementClick("css", ".promotionDiscounts > a:nth-of-type(1)");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        driver.waitUntilPageContainText("小积分，抽大奖");
        driver.closeCurrentBrowser();
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        driver.findElementClick("css", ".promotionDiscounts > a:nth-of-type(2)");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        driver.waitUntilPageContainText("热门关注");
        driver.closeCurrentBrowser();
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        driver.waitUntilElementVisible(By.linkText("展会回顾 | 未来可期，感恩有您同行！"), 10);
        driver.findElementClick("link", "展会回顾 | 未来可期，感恩有您同行！");
        driver.waitUntilPageContainText("展会回顾 | 未来可期，感恩有您同行！");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        driver.closeCurrentBrowser();
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        driver.waitUntilElementVisible(By.linkText("点击领取你的专属礼物！"), 10);
        driver.findElementClick("link", "点击领取你的专属礼物！");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        driver.closeCurrentBrowser();
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        driver.findElementClick("css", ".rollInformation .more [target]");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        driver.waitUntilPageContainText("热烈祝贺东莞怡合达智能制造供应链华南中心项目奠基仪式圆满结束！");
        driver.closeCurrentBrowser();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        System.out.println("测试结束");
        driver.closeAllBrowser();
    }
}
