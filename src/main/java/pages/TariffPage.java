package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TariffPage {

    public static final String WWW_VERIVOX_DE = "https://www.verivox.de/";

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions builder;
    private Integer totalNoOfTariffs = 0;

    public Integer getTotalNoOfTariffs() {
        return totalNoOfTariffs;
    }

    public void setTotalNoOfTariffs(Integer totalNoOfTariffs) {
        this.totalNoOfTariffs = totalNoOfTariffs;
    }

    public TariffPage(WebDriver driver, WebDriverWait wait, Actions builder) {
        this.driver = driver;
        this.wait = wait;
        this.builder = builder;
    }

    public void loadPageAndAcceptCookies() {
        driver.get(WWW_VERIVOX_DE);
        WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='uc-btn-accept-banner']")));
        sleep();
        builder.moveToElement(acceptCookiesButton).click().perform();
    }

    public void navigateToPrivathaftpflichtPage() {
        WebElement versicherungen = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Versicherungen")));
        builder.moveToElement(versicherungen).build().perform();

        WebElement privathaftpflicht = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Privathaftpflicht")));
        builder.moveToElement(privathaftpflicht).click().perform();
    }

    public void enterAgeAndMaritalStatus() {
        WebElement situationGroup = driver.findElement(By.name("situationGroup"));
        Select familienstand = new Select(situationGroup);
        familienstand.selectByVisibleText("Single ohne Kinder");
        familienstand.selectByIndex(0);

        driver.findElement(By.xpath("/html/body/div[1]/main/div[1]/div/section/div[1]/form/div[3]"));
        builder.sendKeys("31").perform();

        WebElement jetztVergleichenButton = driver.findElement(By.xpath("/html/body/div[1]/main/div[1]/div/section/div[1]/form/button"));
        jetztVergleichenButton.submit();
    }

    public void enterBirthDate() {
        WebElement birthdate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-prefilter-page/prefilter-page/main/section/su-signup-form/form/form-factory/section/form-type/section/fieldset[1]/form-factory/section/form-group-type/section/su-signup-field/div/div/value/fieldset[2]/form-factory/section/birthday-type/element-scaffold/section/div[2]/su-signup-field/div/div/value/su-date-picker/div/input")));
        builder.moveToElement(birthdate).click().perform();
        builder.sendKeys("15.07.1991").perform();
    }

    public void enterZipCode() {
        WebElement zipcode = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"prestep_postcode\"]")));
        builder.moveToElement(zipcode).click().perform();
        builder.sendKeys("13088").perform();
    }

    public void clickJetztVergleichenButton() {
        WebElement jetztVergleichenButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-prefilter-page/prefilter-page/main/section/div/div[2]/button")));
        sleep();
        builder.moveToElement(jetztVergleichenButton).click().perform();
    }

    public void verifyTariffsGreaterThan(int noOfTariffs) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]")));
        List<WebElement> elements = driver.findElements(By.className("product-container"));
        Assert.assertTrue(elements.size() >= noOfTariffs);
    }

    public void scrollToTheBottom() {
        try {
            long lastHeight = getPageHeight();
            while (true) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(2000);
                long newHeight = getPageHeight();
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void verifyTariffsAreDisplayed(int noOfTariffs) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[" + noOfTariffs + "]")));
    }

    public void clickLoadMoreTariffsButton() {
        WebElement loadMoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/more-products-button/div/a[1]")));
        sleep();
        builder.moveToElement(loadMoreButton).click().perform();
    }

    public void clickLoadAllTariffs() {
        WebElement loadAllButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/more-products-button/div/a[2]")));
        sleep();
        builder.moveToElement(loadAllButton).click().perform();
    }

    public void scrollAndLoadMoreTariffs() {
        scrollToTheBottom();
        int offset = 20;
        int remainingTariffs = totalNoOfTariffs - offset;
        int totalDisplayedTariffsSoFar;
        if (remainingTariffs >= offset) {
            totalDisplayedTariffsSoFar = 40;
        } else {
            totalDisplayedTariffsSoFar = remainingTariffs + offset;
        }
        verifyTariffsAreDisplayed(totalDisplayedTariffsSoFar);
    }

    public void tariffPrice() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div/div[3]/div[1]/div[2]/div[1]\n")));
    }

    public void clickButtonTariffDetails() {
        WebElement tariffDetailsButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div/div[3]/div[2]/button[1]")));
        sleep();
        builder.moveToElement(tariffDetailsButton).click().perform();
    }

    public void loadTariffDetails() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div[2]/ul/li[1]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div[2]/ul/li[2]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div[2]/ul/li[3]")));
    }

    public void loadMoreTariffDetails() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div[2]/ul/li[4]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div[2]/ul/li[5]")));
    }

    public void zumOnlineButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/product-list/div[1]/product[1]/section/div/div[1]/div[3]/div[2]/button[2]")));
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getPageHeight() {
        return (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
    }
}
