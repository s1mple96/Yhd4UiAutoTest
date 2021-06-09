package fastener;

import Utils.WebDriverUtil;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Demo {
    WebDriverUtil driver = new WebDriverUtil();
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.out.println("测试开始");
        driver.openBrowser("http://test.fastener.yhdfa.com/","chrome");
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
//        driver.sleep(2);
//        driver.takeScreenshotByNow();
    }

    @Test(description = "顶部导航栏")
    public void testNavigationBar() throws Exception {
        driver.findElementClick("css", ".user");
        driver.waitUntilElementVisible(By.cssSelector(".attestation.symbol > span"), 5);
        driver.waitUntilPageContainText("内部测试企业", 3);
        driver.waitUntilPageContainText("认证企业用户", 3);
        driver.waitUntilPageContainText("我的权限", 3);
        driver.waitUntilPageContainText("退出登录", 3);
        System.out.println("用户信息 √√√");

        driver.findElementClick("css", ".nav > li:nth-of-type(1) > a[target='_blank']");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        if (driver.getTitle().equals("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台")) {
            driver.closeCurrentBrowser();
        } else {
            throw new RuntimeException("商城首页超链接重定向错误");
        }
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        System.out.println("商城首页 √√√");

        driver.waitUntilPageContainText("我的消息(", 5);
        driver.findElementClick("link", "我的消息( 0 )");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        if (driver.waitUntilPageContainText("消息列表", 3)) {
            driver.closeCurrentBrowser();
        } else {
            throw new RuntimeException("我的消息超链接重定向错误");
        }
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        System.out.println("我的消息 √√√");

        driver.findElementClick("css", ".nav > li:nth-of-type(3)");
        driver.waitUntilPageContainText("我的订单", 3);
        driver.waitUntilPageContainText("我的报价单", 3);
        driver.waitUntilPageContainText("我的购物车", 3);
        driver.waitUntilPageContainText("我的收藏", 3);
        driver.waitUntilPageContainText("个人中心", 3);
        driver.waitUntilPageContainText("发票管理", 3);
        System.out.println("我的怡合达 √√√");

        driver.findElementClick("css", ".nav > li:nth-of-type(4)");
        driver.waitUntilPageContainText("专属客服", 3);
        driver.waitUntilPageContainText("林杏金", 3);
        driver.waitUntilPageContainText("林杏金", 3);
        driver.waitUntilPageContainText("0769-82886777-", 3);
        driver.waitUntilPageContainText("3003730700", 3);
        driver.waitUntilPageContainText("3003730700", 3);
        driver.waitUntilPageContainText("3003730700@qq.com", 3);
        driver.waitUntilPageContainText("400-900-9050", 3);
        driver.waitUntilPageContainText("全国热线：", 3);
        driver.waitUntilPageContainText("全国热线：", 3);
        driver.waitUntilPageContainText("联系不到客服？", 3);
        System.out.println("专属跟单 √√√");

        driver.findElementClick("css", ".nav > li:nth-of-type(5) > a[target='_blank']");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        if (driver.waitUntilPageContainText("玩游戏抽奖", 3)) {
            driver.closeCurrentBrowser();
        } else {
            throw new RuntimeException("积分商城超链接重定向错误");
        }
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        System.out.println("积分商城 √√√");


        driver.findElementClick("css", ".nav > li:nth-of-type(6) > a");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        if (driver.waitUntilPageContainText("晚上好，欢迎使用怡合达帮助中心", 3)) {
            driver.closeCurrentBrowser();
        } else {
            throw new RuntimeException("帮助中心超链接重定向错误");
        }
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        System.out.println("帮助中心 √√√");

        driver.findElementClick("css", ".nav > li:nth-of-type(7)");
        driver.switchToWindow("怡合达 |工厂自动化零部件一站式采购平台 怡合达电商平台");
        //TODO
        driver.closeCurrentBrowser();
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        System.out.println("关于怡合达 √√√");

        driver.findElementClick("css", ".nav > li:nth-of-type(8)");
        driver.waitUntilPageContainText("垂直品类专区", 3);
        driver.waitUntilPageContainText("紧固件专区", 3);
        driver.waitUntilPageContainText("轴承专区", 3);
        driver.waitUntilPageContainText("医疗行业专区", 3);
        driver.waitUntilPageContainText("设计支持", 3);
        driver.waitUntilPageContainText("计算选型", 3);
        driver.waitUntilPageContainText("产品知识", 3);
        driver.waitUntilPageContainText("采购支持", 3);
        driver.waitUntilPageContainText("企业认证申请表", 3);
        driver.waitUntilPageContainText("积分商城", 3);
        System.out.println("功能精选 √√√");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        System.out.println("测试结束");
//        driver.closeAllBrowser();
    }




}

