package UI.fastener;

public class Fastener {

    /*@BeforeTest(alwaysRun = true)
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
//        driver.sleep(2);
//        driver.takeScreenshotByNow();
    }*/

    /*@Test(description = "顶部导航栏")
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
        if (driver.waitUntilPageContainText("欢迎使用怡合达帮助中心", 3)) {
            driver.closeCurrentBrowser();
        } else {
            throw new RuntimeException("帮助中心超链接重定向错误");
        }
        driver.switchToWindow("怡合达| 工厂自动化零部件一站式采购平台 紧固件");
        System.out.println("帮助中心 √√√");

        driver.findElementClick("css", ".nav > li:nth-of-type(7)");
        driver.switchToWindow("怡合达丨工厂自动化零部件一站式采购平台 怡合达官网");
        driver.waitUntilPageContainText("系统性降低自动化设备的使用成本，推动自动化行业的技术进步", 3);
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
    }*/

    /*@Test(description = "中午导航栏")
    public void testMidlleNavigationBar() {
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
    }*/

   /* @Test(description = "轮播图")
    public void testBanner(){
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
    }*/
    /*@AfterTest(alwaysRun = true)
    public void tearDown() throws Exception {
        System.out.println("测试结束");
        driver.closeAllBrowser();
    }*/




}

