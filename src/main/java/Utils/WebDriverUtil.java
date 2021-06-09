package Utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 *  基于selenium的二次封装
 * @author s1mple
 * @create 2021/6/9-20:11
 */
public class WebDriverUtil {
    private static WebDriver driver = null;
    private static Select select = null;
    private static Alert alert = null;
    private static WebElement element = null;
    private static List<WebElement> elementList = null;
    private static long timeOutInSeconds = 10;
    //--------------------自定义常量------------------------
    public final String LINE = "\r\n";
    public final String smile = "^_^";
    public final String sad = "*o*";

    public WebDriverUtil(long timeOutInSeconds) {
        WebDriverUtil.timeOutInSeconds = timeOutInSeconds;
    }

    public WebDriverUtil() {}

    /**
     * 指定浏览器打开URL
     * @param url
     * @param browser
     */
    public static void openBrowser(String url, String browser) {
        driver = initBrowser(browser);
        driver.manage().timeouts().implicitlyWait(timeOutInSeconds, TimeUnit.SECONDS);
        driver.get(url);
    }

    /**
     * 指定浏览器打开URL
     * @param url
     */
    public static void openBrowser(String url) {
        driver = initBrowser();
        driver.get(url);
    }

    /**
     * 初始化浏览器，方式1
     * @return
     */
    public static WebDriver initBrowser() {
        /*
         * 谷歌浏览器 System.setProperty("webdriver.chrome.driver",
         * "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
         * WebDriver driver = new ChromeDriver(); driver.get("http://www.baidu.com/");
         */
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.manager.showWhenStarting", false);// 是否显示下载进度框
        profile.setPreference("browser.offline-apps.notify", false);// 网站保存离线数据时不通知我
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);// 应用程序设置不询问
        profile.setPreference("browser.download.folderList", 0);// 设置下载地址0是桌面；1是“我的下载”；2是自定义
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/octet-stream, application/vnd.ms-excel, text/csv, application/zip, application/msword");
        profile.setPreference("dom.webnotifications.enabled", false);// 允许通知
        WebDriver driver = new FirefoxDriver((Capabilities) profile);// 启动火狐浏览器
        driver.manage().window().maximize();// 设置窗口大小
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);// 设置页面加载超时
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// 设置查询组件等待时间
        return driver;
    }

    /**
     * 初始化浏览器，方式2
     * @param browser
     * @return
     */
    private static WebDriver initBrowser(String browser) {
        switch (browser) {
            case "ie":
                System.setProperty("webdriver.ie.driver", ".\\Tools\\IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            case "ff":
            case "firefox":
            case "Firefox":
            case "FireFox":
                /**
                 *     FireFox安装方式为默认安装：
                 *     FireFox版本小于48
                 *         System.setProperty("webdriver.firefox.marionette", ".\\Tools\\geckodriver.exe");
                 *     FireFox版本大于48，默认安装时可以试试，应该可以
                 *         System.setProperty("webdriver.gecko.driver", ".\\Tools\\geckodriver.exe");
                 */
                // FireFox安装方式为自定义安装
                System.setProperty("webdriver.firefox.bin", "C:\\ProgramFiles\\Mozilla Firefox\\firefox.exe");
                driver = new FirefoxDriver();
                break;
            case "chrome":
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
                driver = new ChromeDriver();
                break;
            default:
                try {
                    throw new Exception("浏览器错误!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return driver;
    }

    //----------------------------------------------------元素相关-----------------------------------------------------------------------------
    /**
     * 查找元素
     * @param by        传入一个类型
     * @param byValue   传入一个类型值
     * @return
     */
    public WebElement findElement(String by, String byValue) {
        try {
            switch (by) {
                case "id":
                    element = driver.findElement(By.id(byValue));
                    break;
                case "name":
                    element = driver.findElement(By.name(byValue));
                    break;
                case "class":
                    element = driver.findElement(By.className(byValue));
                    break;
                case "tag":
                    element = driver.findElement(By.tagName(byValue));
                case "link":
                    element = driver.findElement(By.linkText(byValue));
                    break;
                case "partiallinktext":
                    element = driver.findElement(By.partialLinkText(byValue));
                case "css":
                    element = driver.findElement(By.cssSelector(byValue));
                    break;
                case "xpath":
                    element = driver.findElement(By.xpath(byValue));
                    break;
                default:
                    throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
            }
        } catch (Exception e) {
            System.out.println("没有找到元素：" + byValue);
        }
        return element;
    }
    /**
     * 查找一组元素
     * @param by        传入一个类型
     * @param byValue   传入一个类型值
     * @return
     */
    public List<WebElement> findElements(String by, String byValue) {
        try {
            switch (by) {
                case "id":
                    elementList = driver.findElements(By.id(byValue));
                    break;
                case "name":
                    elementList = driver.findElements(By.name(byValue));
                    break;
                case "class":
                    elementList = driver.findElements(By.className(byValue));
                    break;
                case "tag":
                    elementList = driver.findElements(By.tagName(byValue));
                case "link":
                    elementList = driver.findElements(By.linkText(byValue));
                    break;
                case "partiallinktext":
                    elementList = driver.findElements(By.partialLinkText(byValue));
                case "css":
                    elementList = driver.findElements(By.cssSelector(byValue));
                    break;
                case "xpath":
                    elementList = driver.findElements(By.xpath(byValue));
                    break;
                default:
                    throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
            }
        } catch (Exception e) {
            System.out.println("没有找到元素：" + byValue);
        }
        return elementList;
    }

    /**
     * 获取单个元素
     * @param xpath
     * @return
     */
    public WebElement findElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * 获取单个元素
     * @param tag
     * @return
     */
    public WebElement findElementByTag(String tag) {
        return driver.findElement(By.tagName(tag));
    }

    /**
     * 获取单个元素
     * @param id
     * @return
     */
    public WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

    /**
     * 获取单个元素
     * @param name
     * @return
     */
    public WebElement findElementByClassName(String name) {
        return driver.findElement(By.className(name));
    }

    /**
     * 获取单个元素
     * @param text
     * @return
     */
    public WebElement findElementByText(String text) {
        return driver.findElement(By.linkText(text));
    }

    /**
     * 获取单个元素
     * @param text
     * @return
     */
    public WebElement findElementByPartialText(String text) {
        return driver.findElement(By.partialLinkText(text));
    }

    /**
     * 获取单个元素
     * @param name
     * @return
     */
    public WebElement findElementByName(String name) {
        return driver.findElement(By.name(name));
    }

    /**
     * 获取多个元素
     * @param className
     * @return
     */
    public List<WebElement> findElementsByClassName(String className) {
        return driver.findElements(By.className(className));
    }

    /**
     * 获取多个元素
     * @param text
     * @return
     */
    public List<WebElement> findElementsByText(String text) {
        return driver.findElements(By.linkText(text));
    }

    /**
     * 获取多个元素
     * @param text
     * @return
     */
    public List<WebElement> findElementsByPartialText(String text) {
        return driver.findElements(By.partialLinkText(text));
    }

    /**
     * 获取多个元素
     * @param id
     * @return
     */
    public List<WebElement> findElementsById(String id) {
        return driver.findElements(By.id(id));
    }

    /**
     * 获取多个元素
     * @param tag
     * @return
     */
    public List<WebElement> findElementsByTag(String tag) {
        return driver.findElements(By.tagName(tag));
    }

    /**
     * 获取一组元素中的指定元素
     * @param by
     * @param index
     * @return
     */
    public WebElement FindByElements(By by, int index) {
        WebElement element = null;
        if (this.elementsExists(by)) {
            element = driver.findElements(by).get(index);
        }
        return element;
    }

    /**
     * 查找元素并点击
     * @param by        传入一个类型
     * @param byValue   传入一个类型值
     * @return
     */
    public boolean findElementClick(String by, String byValue) {
        try {
            switch (by) {
                case "id":
                    driver.findElement(By.id(byValue)).click();
                    return true;
                case "name":
                    driver.findElement(By.name(byValue)).click();
                    return true;
                case "class":
                    driver.findElement(By.className(byValue)).click();
                    return true;
                case "tag":
                    driver.findElement(By.tagName(byValue)).click();
                case "link":
                    driver.findElement(By.linkText(byValue)).click();
                    return true;
                case "partiallinktext":
                    driver.findElement(By.partialLinkText(byValue)).click();
                case "css":
                    driver.findElement(By.cssSelector(byValue)).click();
                    return true;
                case "xpath":
                    driver.findElement(By.xpath(byValue)).click();
                    return true;
                default:
                    throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
            }
        } catch (Exception e) {
            System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "  的元素或者该元素无法点击****");
            return false;
        }
    }

    /**
     * 定位元素并点击
     * @param id
     */
    public void findElementByIdAndClick(String id) {
        driver.findElement(By.id(id)).click();
    }

    /**
     * 定位元素并点击
     * @param name
     */
    public void findElementByNameAndClick(String name) {
        driver.findElement(By.name(name)).click();
    }

    /**
     * 定位元素并点击
     * @param text
     */
    public void findElementByTextAndClick(String text) {
        driver.findElement(By.linkText(text)).click();
    }

    /**
     * 定位元素并点击
     * @param text
     */
    public void findElementByPartiaTextAndClick(String text) {
        driver.findElement(By.partialLinkText(text)).click();
    }

    /**
     * 定位元素并点击
     * @param xpath
     */
    public void findElementByXpathAndClick(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    /**
     * 定位元素并点击
     * @param name
     */
    public void findElementByClassNameAndClick(String name) {
        driver.findElement(By.className(name)).click();
    }

    /**
     * 查找元素并清除文本内容
     * @param by
     * @param byValue
     * @return
     */
    public boolean findElementClear(String by, String byValue) {
        try {
            switch (by) {
                case "id":
                    driver.findElement(By.id(byValue)).clear();
                    return true;
                case "name":
                    driver.findElement(By.name(byValue)).clear();
                    return true;
                case "class":
                    driver.findElement(By.className(byValue)).clear();
                    return true;
                case "tag":
                    driver.findElement(By.tagName(byValue)).clear();
                    return true;
                case "link":
                    driver.findElement(By.linkText(byValue)).clear();
                    return true;
                case "partiallinktext":
                    driver.findElement(By.partialLinkText(byValue)).clear();
                    return true;
                case "css":
                    driver.findElement(By.cssSelector(byValue)).clear();
                    return true;
                case "xpath":
                    driver.findElement(By.xpath(byValue)).clear();
                    return true;
                default:
                    throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
            }
        } catch (Exception e) {
            System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "  的元素或者该元素没有输入值****");
            return false;
        }
    }

    /**
     * 查找元素并输入值
     * @param by
     * @param byValue
     * @param key
     * @return
     */
    public boolean findElementSendKeys(String by, String byValue, String key) {
        try {
            switch (by) {
                case "id":
                    driver.findElement(By.id(byValue)).sendKeys(key);
                    return true;
                case "name":
                    driver.findElement(By.name(byValue)).sendKeys(key);
                    return true;
                case "class":
                    driver.findElement(By.className(byValue)).sendKeys(key);
                    return true;
                case "tag":
                    driver.findElement(By.tagName(byValue)).sendKeys(key);
                    return true;
                case "link":
                    driver.findElement(By.linkText(byValue)).sendKeys(key);
                    return true;
                case "partiallinktext":
                    driver.findElement(By.partialLinkText(byValue)).sendKeys(key);
                case "css":
                    driver.findElement(By.cssSelector(byValue)).sendKeys(key);
                    return true;
                case "xpath":
                    driver.findElement(By.xpath(byValue)).sendKeys(key);
                    return true;
                default:
                    throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
            }
        } catch (Exception e) {
            System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "    的元素或者该元素无法输入****");
            return false;
        }
    }
    /**
     *     查找元素并输入值
     * @param by      传入一个类型        例如：name
     * @param byValue 传入一个类型值       例如：username
     * @param key     填写要输入的值        例如：zhangsan
     */
    public boolean findElementClearAndSendKeys(String by, String byValue, String key) {
        try {
            switch (by) {
                case "id":
                    findElementClear(by,byValue);
                    driver.findElement(By.id(byValue)).sendKeys(key);
                    return true;
                case "name":
                    findElementClear(by,byValue);
                    driver.findElement(By.name(byValue)).sendKeys(key);
                    return true;
                case "class":
                    findElementClear(by,byValue);
                    driver.findElement(By.className(byValue)).sendKeys(key);
                    return true;
                case "tag":
                    findElementClear(by,byValue);
                    driver.findElement(By.tagName(byValue)).sendKeys(key);
                    return true;
                case "link":
                    findElementClear(by,byValue);
                    driver.findElement(By.linkText(byValue)).sendKeys(key);
                    return true;
                case "partiallinktext":
                    findElementClear(by,byValue);
                    driver.findElement(By.partialLinkText(byValue)).sendKeys(key);
                case "css":
                    findElementClear(by,byValue);
                    driver.findElement(By.cssSelector(byValue)).sendKeys(key);
                    return true;
                case "xpath":
                    findElementClear(by,byValue);
                    driver.findElement(By.xpath(byValue)).sendKeys(key);
                    return true;
                default:
                    throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
            }
        } catch (Exception e) {
            System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "    的元素或者该元素无法输入****");
            return false;
        }
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param id
     * @param text
     */
    public void findElementByIdAndClearSendkeys(String id, String text) {
        driver.findElement(By.id(id)).clear();
        driver.findElement(By.id(id)).sendKeys(text);
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param id
     * @param num
     */
    public void findElementByIdAndClearSendkeys(String id, int num) {
        driver.findElement(By.id(id)).clear();
        driver.findElement(By.id(id)).sendKeys(num + "");
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param name
     * @param text
     */
    public void findElementByNameAndClearSendkeys(String name, String text) {
        driver.findElement(By.name(name)).clear();
        driver.findElement(By.name(name)).sendKeys(text);
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param name
     * @param num
     */
    public void findElementByNameAndClearSendkeys(String name, int num) {
        driver.findElement(By.name(name)).clear();
        driver.findElement(By.name(name)).sendKeys(num + "");
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param xpath
     * @param text
     */
    public void findElementByXpathAndClearSendkeys(String xpath, String text) {
        findElementByXpath(xpath).clear();
        findElementByXpath(xpath).sendKeys(text);
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param xpath
     * @param num
     */
    public void findElementByXpathAndClearSendkeys(String xpath, int num) {
        findElementByXpath(xpath).clear();
        findElementByXpath(xpath).sendKeys(num + "");
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param classname
     * @param text
     */
    public void findElementByClassnameAndClearSendkeys(String classname, String text) {
        driver.findElement(By.className(classname)).clear();
        driver.findElement(By.className(classname)).sendKeys(text);
    }

    /**
     * 定位元素并清空文本内容，输入相应的值
     * @param classname
     * @param num
     */
    public void findElementByClassnameAndClearSendkeys(String classname, int num) {
        driver.findElement(By.className(classname)).clear();
        driver.findElement(By.className(classname)).sendKeys(num + "");
    }

    /**
     * 定位元素，并获取其文本内容
     * @param xpath
     * @return
     */
    public String getTextByXpath(String xpath) {
        return findElementByXpath(xpath).getText();
    }

    /**
     * 定位元素，并获取其文本内容
     * @param name
     * @return
     */
    public String getTextByClassName(String name) {
        return findElementByClassName(name).getText();
    }

    /**
     * 定位元素，并获取其文本内容
     * @param id
     * @return
     */
    public String getTextById(String id) {
        return findElementById(id).getText();
    }

    /**
     * 定位元素，并获取其文本内容
     * @param name
     * @return
     */
    public String getTextByName(String name) {
        return findElementByName(name).getText();
    }

    /**
     * 定位元素，并指定点击次数(连续点击)
     * @param id
     * @param clickCount
     * @return
     */
    public boolean clickById(String id, int clickCount) {
        try {
            for (int i = 0; i < clickCount; i++) {
                driver.findElement(By.id(id)).click();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 定位元素，并指定点击次数(连续点击)
     * @param xpath
     * @param clickCount
     * @return
     */
    public boolean clickByXpath(String xpath, int clickCount) {
        try {
            for (int i = 0; i < clickCount; i++) {
                driver.findElement(By.xpath(xpath)).click();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 定位元素，并指定点击次数(连续点击)
     * @param css
     * @param clickCount
     * @return
     */
    public boolean clickByCss(String css, int clickCount) {
        try {
            for (int i = 0; i < clickCount; i++) {
                driver.findElement(By.cssSelector(css)).click();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 判断元素是否存在
     * @param selector
     * @return
     */
    public boolean exists(By selector) {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);// 设置查询组件等待时间
        try {
            driver.findElement(selector);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// 设置查询组件等待时间
            return true;
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// 设置查询组件等待时间
            return false;
        }
    }

    /**
     * 判断一个元素是否存在
     * @param by
     * @return
     */
    public boolean isElementExist(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * 判断一组元素是否存在
     * @param by
     * @return
     */
    public boolean elementsExists(By by) {
        return (driver.findElements(by).size() > 0) ? true : false;
    }

//---------------------------------------判断页面是否包含指定文本---------------------------------------------------------
    /**
     * 1、指定时间内等待直到页面包含文本字符串
     * @param text      期望出现的文本
     * @param seconds   超时时间
     * @return          检查给定文本是否存在于指定元素中, 超时则捕获抛出异常TimeoutException并返回false
     */
    public static Boolean waitUntilPageContainText(String text, long seconds) {
        try {
            return new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.tagName("body")), text));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 2、默认时间等待直到页面包含文本字符串
     * @param text      期望出现的文本
     * @return Boolean  检查给定文本是否存在于指定元素中, 超时则捕获抛出异常TimeoutException并返回false
     */
    public static Boolean waitUntilPageContainText(String text) {
        try {
            return new WebDriverWait(driver, timeOutInSeconds)
                    .until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.tagName("body")), text));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//---------------------------------------元素判断---------------------------------------------------------
    /**
     * 1、指定时间内等待直到元素存在于页面的DOM上并可见, 可见性意味着该元素不仅被显示, 而且具有大于0的高度和宽度
     * @param locator   元素定位器
     * @param seconds   超时时间
     * @return Boolean  检查给定元素的定位器是否出现, 超时则捕获抛出异常TimeoutException并返回false
     */
    public static Boolean waitUntilElementVisible(By locator, int seconds) {
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 2、默认时间内等待直到元素存在于页面的DOM上并可见, 可见性意味着该元素不仅被显示, 而且具有大于0的高度和宽度
     * @param locator   元素定位器
     * @return Boolean  检查给定元素的定位器是否出现, 超时则捕获抛出异常TimeoutException并返回false
     */
    public static Boolean waitUntilElementVisible(By locator) {
        try {
            new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断元素是否显示
     * @param id
     * @return
     */
    public boolean getDisplayStatById(String id) {
        return driver.findElement(By.id(id)).isDisplayed();
    }

    /**
     * 判断元素是否显示
     * @param xpath
     * @return
     */
    public boolean getDisplayStatByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath)).isDisplayed();
    }

    /**
     * 判断元素是否显示
     * @param css
     * @return
     */
    public boolean getDisplayStatByCss(String css) {
        return driver.findElement(By.cssSelector(css)).isDisplayed();
    }

    /**
     * 判断元素是否可写
     * @param id
     * @return
     */
    public boolean getEnableStatById(String id) {
        return driver.findElement(By.id(id)).isEnabled();
    }

    /**
     * 判断元素是否可写
     * @param xpath
     * @return
     */
    public boolean getEnableStatByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath)).isEnabled();
    }

    /**
     * 判断元素是否可写
     * @param css
     * @return
     */
    public boolean getEnableStatByCss(String css) {
        return driver.findElement(By.cssSelector(css)).isEnabled();
    }

    /**
     * 判断元素是否选中
     * @param id
     * @return
     */
    public boolean getSelectStatById(String id) {
        return driver.findElement(By.id(id)).isSelected();
    }

    /**
     * 判断元素是否选中
     * @param xpath
     * @return
     */
    public boolean getSelectStatByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath)).isSelected();
    }

    /**
     * 判断元素是否选中
     * @param css
     * @return
     */
    public boolean getSelectStatByCss(String css) {
        return driver.findElement(By.cssSelector(css)).isSelected();
    }

    /**
     * 获取当前焦点所在页面元素的属性值(name,value,id,src等等)
     * @param attribute
     * @return
     */
    public String getFocusAttributeValue(String attribute) {
        String value = "";
        try {
            Thread.sleep(333);
        } catch (Exception e) {
            e.printStackTrace();
        }
        value = driver.switchTo().activeElement().getAttribute(attribute);
        System.out.println("The focus Element's " + attribute + "attribute value is>>" + value);
        return value;
    }

    /**
     * 等待元素可用再点击
     * @param xpath
     * @throws InterruptedException
     */
    public void waitForEnabledByXpathAndClick(String xpath) throws InterruptedException {
        boolean key = true;
        while (key) {
            if (findElementByXpath(xpath).isEnabled() && findElementByXpath(xpath).isDisplayed()) {
                clickByJsByXpath(xpath);
                key = false;
            } else {
                sleep(0);
            }
        }
    }

    /**
     * 自定义等待时间
     * @param key
     * @throws InterruptedException
     */
    public static void sleep(int key) throws InterruptedException {
        switch (key) {
            case 0:
                Thread.sleep(500);
                break;
            case 1:
                Thread.sleep(2000);
                break;
            case 2:
                Thread.sleep(5000);
                break;
            default:
                System.out.println("错误");
                break;
        }
    }

    //---------------------------------------下拉列表操作---------------------------------------------------------

    /**
     * 根据id获取下拉框，根据index选择选项
     * @param id
     * @param index
     */
    public void findSelectByIdAndSelectByIndex(String id, int index) {
        Select select = new Select(findElementById(id));
        select.selectByIndex(index);
    }

    /**
     * 根据id获取下拉框，根据value选择选项
     * @param id
     * @param value
     */
    public void findSelectByIdAndSelectByValue(String id, String value) {
        Select select = new Select(findElementById(id));
        select.selectByValue(value);
    }

    /**
     * 根据id获取下拉框，根据text选择选项
     * @param id
     * @param text
     */
    public void findSelectByIdAndSelectByText(String id, String text) {
        Select select = new Select(findElementById(id));
        select.selectByVisibleText(text);
    }

    /**
     * 根据classname获取下拉框，根据text选择选项
     * @param name
     * @param text
     */
    public void findSelectByClassNameAndSelectByText(String name, String text) {
        Select select = new Select(findElementByClassName(name));
        select.selectByVisibleText(text);
    }

    /**
     * 根据classname获取下拉框，根据Value选择选项
     * @param name
     * @param value
     */
    public void findSelectByClassNameAndSelectByValue(String name, String value) {
        Select select = new Select(findElementByClassName(name));
        select.selectByValue(value);
    }

    /**
     * 根据classname获取下拉框，根据index选择选项
     * @param name
     * @param index
     */
    public void findSelectByClassNameAndSelectByIndex(String name, int index) {
        Select select = new Select(findElementByClassName(name));
        select.selectByIndex(index);
    }

    /**
     * 根据name获取下拉框，根据text选择选项
     * @param name
     * @param text
     */
    public void findSelectByNameAndSelectByText(String name, String text) {
        Select select = new Select(findElementByName(name));
        select.selectByVisibleText(text);
    }

    /**
     * 根据name获取下拉框，根据Value选择选项
     * @param name
     * @param value
     */
    public void findSelectByNameAndSelectByValue(String name, String value) {
        Select select = new Select(findElementByName(name));
        select.selectByValue(value);
    }

    /**
     * 根据name获取下拉框，根据index选择选项
     * @param name
     * @param index
     */
    public void findSelectByNameAndSelectByIndex(String name, int index) {
        Select select = new Select(findElementByName(name));
        select.selectByIndex(index);
    }

    /**
     * 定位select并选中对应text的option
     * @param locator
     * @param text
     */
    public static void selectByText(By locator, String text) {
        select = new Select(driver.findElement(locator));
        select.selectByVisibleText(text);
    }

    /**
     * 定位select并选中对应index的option
     * @param locator
     * @param index
     */
    public static void selectByIndex(By locator, int index) {
        select = new Select(driver.findElement(locator));
        select.selectByIndex(index);
    }

    /**
     * 定位select并选中对应value值的option
     * @param locator  定位select的选择器
     * @param value    option 中的value值
     */
    public static void selectByValue(By locator, String value) {
        select = new Select(driver.findElement(locator));
        select.selectByValue(value);
    }

    //---------------------------------------弹框操作---------------------------------------------------------

    /**
     * 判断是否有弹框
     * @return
     */
    public boolean isAlertPresent() {
        try {
            alert = driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    /**
     * 接受弹出框
     */
    public void acceptAlert() {
        if (this.isAlertPresent()) {
            alert.accept();
        }
    }

    /**
     * 取消弹出框
     */
    public void dimissAlert() {
        if (this.isAlertPresent()) {
            alert.dismiss();
        }
    }

    /**
     * 获取弹出内容
     * @return
     */
    public String getAlertText() {
        String text = null;
        if (this.isAlertPresent()) {
            text = alert.getText();
        } else {
            // todo:log;
        }
        return text;
    }

    /**
     * 弹出对话框输入文本字符串
     * @param text
     */
    public void inputTextToAlert(String text) {
        if (this.isAlertPresent()) {
            alert.sendKeys(text);
        } else {
            // todo:log;
        }
    }

//---------------------------------------窗口和iframe---------------------------------------------------------

    /**
     * 切换到当前页面
     */
    public static void switchToCurrentPage() {
        String handle = driver.getWindowHandle();
        for (String tempHandle : driver.getWindowHandles()) {
            if (tempHandle.equals(handle)) {
                driver.close();
            } else {
                driver.switchTo().window(tempHandle);
            }
        }
    }

    /**
     * 切换到指定title的窗口
     * @param windowTtitle
     */
    public void switchToWindow(String windowTtitle) {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handler : windowHandles) {
            driver.switchTo().window(handler);
            String title = driver.getTitle();
            if (windowTtitle.equals(title)) {
                break;
            }
        }
    }

    /**
     * 切换至父级frame
     */
    public static void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }

    /**
     * 切换默认最外层frame或者窗口
     * @return 这个驱动程序聚焦在顶部窗口/第一个frame上
     */
    public static void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    /**
     * 切换到指定iframe
     * @param frameId
     */
    public void switchToFrameById(String frameId) {
        driver.switchTo().frame(frameId);
    }

    /**
     * 切换到指定iframe
     * @param index
     */
    public void switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
    }

    /**
     * 切换到指定iframe
     * @param locator
     */
    public void switchToframeByElement(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    /**
     * 提交表单
     * @param locator
     */
    public static void submitForm(By locator) {
        driver.findElement(locator).submit();
    }

    /**
     * 上传文件
     * @param locator
     * @param filePath
     */
    public static void uploadFile(By locator, String filePath) {
        driver.findElement(locator).sendKeys(filePath);
    }

    //---------------------------------------JS操作---------------------------------------------------------

    /**
     * JS点击指定元素
     * @param element
     */
    public void clickByJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", element);
    }

    /**
     * 定位元素触发JS点击事件
     * @param xpath
     */
    public void clickByJsByXpath(String xpath) {
        clickByJs(driver.findElement(By.xpath(xpath)));
    }

    /**
     * 定位元素触发JS点击事件
     * @param text
     */
    public void clickByJsByText(String text) {
        clickByJs(findElementByText(text));
    }

    /**
     * 定位元素触发JS点击事件
     * @param id
     */
    public void clickByJsById(String id) {
        clickByJs(findElementById(id));
    }

    /**
     * 定位元素触发JS点击事件
     * @param name
     */
    public void clickByJsByClassName(String name) {
        clickByJs(findElementByClassName(name));
    }

    /**
     * 定位元素触发JS点击事件
     * @param name
     */
    public void clickByJsByName(String name) {
        clickByJs(findElementByName(name));
    }

    /**
     * 滚动到窗口最上方
     */
    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);");
    }

    /**
     * 滚动到页面底部
     * @param id
     */
    public void scrollToBottom(String id) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,10000);");
    }

    /**
     * 滚动到某个元素
     * @param element
     */
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * js给指定元素value赋值
     * @param text
     * @param element
     */
    public void inputTextByJs(String text, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value=" + text + "\"", element);
    }

    /**
     * js使元素隐藏元素显示
     * @param element
     */
    public void makeElementDisplay(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style=arguments[1]", element, "display: block;");
    }


//---------------------------------------浏览器操作---------------------------------------------------------

    /**
     * 关闭当前浏览器
     */
    public static void closeCurrentBrowser() {
        driver.close();
    }

    /**
     * 关闭所有selenium驱动打开的浏览器
     */
    public static void closeAllBrowser() {
        driver.quit();
    }

    /**
     * 最大化浏览器
     */
    public static void maxBrowser() {
        driver.manage().window().maximize();
    }

    /**
     * 自定义设置浏览器尺寸
     * @param width
     * @param heigth
     */
    public static void setBrowserSize(int width, int heigth) {
        driver.manage().window().setSize(new Dimension(width, heigth));
    }

    /**
     * 获取网页的title值
     * @return
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * 获取当前url字符串
     * @return
     */
    public static String getURL() {
        return driver.getCurrentUrl();
    }

    /**
     * 上一个页面(点击浏览器返回)
     */
    public static void returnToPreviousPage() {
        driver.navigate().back();
    }

    /**
     * 下一个页面(如果没有下一个页面则什么都不做)
     * 浏览器上的前进
     */
    public static void forwardToNextPage() {
        driver.navigate().forward();
    }

    /**
     * 刷新页面
     */
    public static void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * 强制刷新页面
     */
    public void refresh() {
        Actions ctrl = new Actions(driver);
        ctrl.keyDown(Keys.CONTROL).perform();
        try {
            pressKeyEvent(KeyEvent.VK_F5);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        ctrl.keyUp(Keys.CONTROL).perform();
    }

    /**
     * 判断是否加载有JQuery
     * @return
     */
    public Boolean JQueryLoaded() {
        Boolean loaded;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            loaded = (Boolean) js.executeScript("return" + "JQuery()!=null");
        } catch (WebDriverException e) {
            loaded = false;
        }
        return loaded;
    }
    //---------------------------------------屏幕截图---------------------------------------------------------

    /**
     * 屏幕截图
     * @param driver
     */
    public void screenShot(WebDriver driver) {
        String dir_name = "screenshot";
        if (!(new File(dir_name).isDirectory())) {
            new File(dir_name).mkdir();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time = sdf.format(new Date());
        try {
            File source_file = (((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));// 执行截屏
            FileUtils.copyFile(source_file, new File(dir_name + File.separator + time + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 截图命名为当前时间保存桌面
     * @throws IOException
     */
    public void takeScreenshotByNow() throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time = sdf.format(new Date());
        String file = "C:\\Users\\Administrator\\Desktop\\截屏\\" + time + ".png";
        FileUtils.copyFile(srcFile, new File(file));
    }

    /**
     * 截图重命名保存至桌面
     * @param name
     * @throws IOException
     */
    public void takeScreenshotByName(String name) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String file = "C:\\Users\\Administrator\\Desktop\\截屏\\" + name + ".png";
        FileUtils.copyFile(srcFile, new File(file));
    }

    //---------------------------------------键盘操作---------------------------------------------------------

    /**
     * 获取键盘
     * @return
     */
    public Keyboard getKeyboard() {
        return ((HasInputDevices) driver).getKeyboard();
    }

    /**
     * 模拟crtrl+F5
     */
    public void refreshWithCtrlF5() {
        getKeyboard().sendKeys(Keys.CONTROL, Keys.F5);
    }
    /**
     *     按物理按键(KeyEvent类中查找相关的常量)
     *     例子：
     *        Robot robot = new Robot();
     *         robot.keyPress(KeyEvent.VK_ENTER);//按下enter键
     */
    /**
     * 按物理按键(KeyEvent类中查找相关的常量)
     *  例子：
     *      Robot robot = new Robot();
     *      robot.keyPress(KeyEvent.VK_ENTER);//按下enter键
     * @param keycode
     * @throws AWTException
     */
    public void pressKeyEvent(int keycode) throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(keycode);
    }

    //---------------------------------------鼠标操作---------------------------------------------------------

    /**
     * 鼠标悬浮指定元素并点击
     * @param id
     */
    public void moveToElementById(String id) {
        Actions actions = new Actions(driver);
        actions.moveToElement(findElementById(id)).perform();
    }

    /**
     * 鼠标悬浮指定元素并点击
     * @param name
     */
    public void moveToElementByClassName(String name) {
        Actions actions = new Actions(driver);
        actions.moveToElement(findElementByClassName(name)).perform();
    }

    /**
     * 鼠标右键点击
     * @param id
     */
    public void RightClickWebElement(String id) {
        Actions actions = new Actions(driver);
        actions.contextClick(findElementById(id)).perform();
    }

    /**
     * 鼠标双击
     * @param id
     */
    public void DoubleClickWebElement(String id) {
        Actions actions = new Actions(driver);
        actions.doubleClick(findElementById(id)).perform();
    }

    /**
     *    模拟点击键盘上的键：
     *        keyDown()按下
     *        keyUp()抬起,松开
     *
     *    常见的键：
     *        Keys.SHIFT    Keys.ALT   Keys.Tab
     */
    public void ClickCtrl(String id) {
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL);//按下control键
        actions.keyUp(Keys.CONTROL);//松开control键
    }

    /**
     *    模拟键盘输入关键字到输入框
     */
    public void sendText(By by,String text) {
        Actions actions = new Actions(driver);
        actions.sendKeys(driver.findElement(by),text).perform();
    }

    /**
     *    模拟鼠标移动到指定元素,并点击
     */
    public void moveToElementAndClick(By by,String text) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(by)).click().perform();
    }

    /**
     *    模拟鼠标点击和释放
     */
    public void clickHoldAndRelease(By by) {
        Actions actions = new Actions(driver);
        actions.clickAndHold(driver.findElement(by)).perform();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actions.release(driver.findElement(by)).perform();
    }

//---------------------------------------Cookie操作---------------------------------------------------------

    /**
     * 获取当前域所有的cookies
     * @return
     */
    public static Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    /**
     * 输出cookies信息
     */
    public void outputCookie() {
        Set<Cookie> cookie = driver.manage().getCookies();
        System.out.println(cookie);
    }

    /**
     * 添加cookie信息
     * @param args
     */
    public void addCookie(Map<String, String> args) {
        Set<String> keys = args.keySet();
        for (String key : keys) {
            driver.manage().addCookie(new Cookie(key, args.get(key)));
        }
    }

    /**
     * 用给定的name和value创建默认路径的Cookie并添加, 永久有效
     * @param name
     * @param value
     */
    public static void addCookie(String name, String value) {
        driver.manage().addCookie(new Cookie(name, value));
    }

    /**
     * 用给定的name和value创建指定路径的Cookie并添加, 永久有效
     * @param name  cookie名称
     * @param value cookie值
     * @param path  cookie路径
     */
    public static void addCookie(String name, String value, String path) {
        driver.manage().addCookie(new Cookie(name, value, path));
    }
    /**
     * 根据cookie名称删除cookie
     * @param name cookie的name值
     */
    public static void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
    }
    /**
     * 删除当前域的所有Cookie
     */
    public static void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    /**
     * 根据名称获取指定cookie
     * @param name cookie名称
     * @return Map<String, String>, 如果没有cookie则返回空, 返回的Map的key值如下:
     *         <ul>
     *         <li><tt>name</tt> <tt>cookie名称</tt>
     *         <li><tt>value</tt> <tt>cookie值</tt>
     *         <li><tt>path</tt> <tt>cookie路径</tt>
     *         <li><tt>domain</tt> <tt>cookie域</tt>
     *         <li><tt>expiry</tt> <tt>cookie有效期</tt>
     *         </ul>
     */
    public static Map<String, String> getCookieByName(String name) {
        Cookie cookie = driver.manage().getCookieNamed(name);
        if (cookie != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", cookie.getName());
            map.put("value", cookie.getValue());
            map.put("path", cookie.getPath());
            map.put("domain", cookie.getDomain());
            map.put("expiry", cookie.getExpiry().toString());
            return map;
        }
        return null;
    }

//---------------------------------------远程---------------------------------------------------------
    /**
     * 进入测试，打开浏览器，输入网址，打开网页
     *
     * @param remoteUrl 远程服务器地址
     * @param pageUrl   测试页面地址
     */
    public boolean startTest(String remoteUrl, String pageUrl) {
        try {
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), DesiredCapabilities.firefox());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            driver.get(pageUrl);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 进入测试，打开浏览器，输入网址，打开网页
     * @param explore   调用的浏览器，需要启动不同的server
     *                     如：firefox，需要运行selenium-server-standalone-2.33.0.jar。
     *                        IE，则需运行IEDriverServer.exe
     * @param remoteUrl 远程服务器地址
     * @param pageUrl   测试页面地址
     */
    public boolean startTest(String explore, String remoteUrl, String pageUrl) {
        try {
            try {
                if ("f".equals(explore)) {
                    System.out.println("firefox");
                    driver = new RemoteWebDriver(new URL(remoteUrl), DesiredCapabilities.firefox());
                } else if ("ie".equals(explore)) {
                    System.out.println("internet explorer");
                    DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
                    driver = new RemoteWebDriver(new URL(remoteUrl), cap);
                } else {
                    System.out.println("firefox");
                    driver = new RemoteWebDriver(new URL(remoteUrl), DesiredCapabilities.firefox());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            driver.get(pageUrl);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
