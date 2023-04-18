package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.TariffPage;

public class TariffStepDefinition {

    private final WebDriver driver;
    private final TariffPage tariffPage;

    public TariffStepDefinition() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(new ChromeOptions());
        driver.manage().window().maximize();
        tariffPage = new TariffPage(driver, new WebDriverWait(driver, 60), new Actions(driver));
    }

    @Given("that I can open www.verivox.de")
    public void thatICanOpenTheUrl() {
        tariffPage.loadPageAndAcceptCookies();
    }

    @When("I navigate to Versicherungen and select Privathaftpflicht")
    public void iNavigateToVersicherungenAndSelectPrivathaftpflicht() {
        tariffPage.navigateToPrivathaftpflichtPage();
    }

    @And("I enter my age and Single ohne Kinder")
    public void iEnterMyAgeAndSingleOhneKinder() {
        tariffPage.enterAgeAndMaritalStatus();
    }

    @Then("I go to the Privathaftpflicht personal information page")
    public void iGoToThePrivathaftpflichtPersonalInformationPage() {
        Assert.assertEquals("Privathaftpflicht – jetzt vergleichen und mit Sicherheit sparen.", driver.getTitle());
    }

    @And("I enter my birthdate")
    public void iEnterMyBirthdate() {
        tariffPage.enterBirthDate();
    }

    @And("I enter my zip code")
    public void iEnterMyZipcode() {
        tariffPage.enterZipCode();
    }

    @And("I click the Jetzt vergleichen button")
    public void iClickTheJetztVergleichenButton() {
        tariffPage.clickJetztVergleichenButton();
    }

    @Then("I should see a page that lists the available tariffs for my selection")
    public void iShouldSeeAPageThatListsTheAvailableTariffsForMySelection() {
        tariffPage.verifyTariffsGreaterThan(5);
    }

    @Given("the same tariff calculation criteria from scenario 1")
    public void theSameTariffCalculationCriteriaFromScenario1() {
        tariffPage.loadPageAndAcceptCookies();
        tariffPage.navigateToPrivathaftpflichtPage();
        tariffPage.enterAgeAndMaritalStatus();
        tariffPage.enterBirthDate();
        tariffPage.enterZipCode();
        tariffPage.clickJetztVergleichenButton();
    }

    @When("I display the tariff Result List page")
    public void iDisplayTheTariffResultListPage() {
        tariffPage.verifyTariffsGreaterThan(1);
    }

    @Then("I should see the total number of available tariffs listed above all the result list")
    public void iShouldSeeTheTotalNumberOfAvailableTariffsListedAboveAllTheResultList() {
        tariffPage.verifyTotalTariffs();
    }

    @When("I scroll to the end of the result list page")
    public void iScrollToTheEndOfTheResultListPage() {
        tariffPage.scrollToTheBottom();
    }

    @Then("I should see only the first 20 tariffs displayed")
    public void iShouldSeeOnlyTheFirst20TariffsDisplayed() {
        tariffPage.verifyTariffsAreDisplayed(20);
    }

    @When("I click on the button labeled 20 weitere Tarife laden")
    public void iClickOnTheButtonLabeled20WeitereTarifeLaden() {
        tariffPage.clickLoadMoreTariffsButton();
    }

    @Then("I should see the next 20 tariffs displayed")
    public void iShouldSeeTheNext20TariffsDisplayed() {
        tariffPage.scrollAndLoadMoreTariffs();
    }

    @And("I can continue to load any additional tariffs until all tariffs have been displayed")
    public void ICcanContinueToLoadAnyAdditionalTariffsUntilAllTariffsHaveBeenDisplayed() {
        tariffPage.clickLoadAllTariffs();
        tariffPage.scrollToTheBottom();
        tariffPage.verifyTariffsAreDisplayed(tariffPage.getTotalNoOfTariffs());
    }

    @And("I display the tariff result list page")
    public void andIDisplayTheTariffResultListPage() {
        tariffPage.verifyTariffsGreaterThan(1);
    }

    @Then("I should see the tariff price of the first tariff")
    public void iShouldSeeTheTariffPriceOfTheFirstTariff() {
        tariffPage.tariffPrice();
    }

    @When("I open tariff details")
    public void iOpenTariffDetails() {
        tariffPage.clickButtonTariffDetails();
    }

    @Then("I see tariff details sections: “Weitere Leistungen”, “Allgemein“, „ Tätigkeiten und Hobbys“")
    public void iSeeTariffDetailsSectionsWeitereLeistungenAllgemeinTatigkeitenUndHobbys() {
        tariffPage.loadTariffDetails();
    }

    @And("I see tariff details sections: “Miete & Immobilien” and “Dokumente”")
    public void iSeeTariffDetailsSectionsMieteImmobilienAndDokumente() {
        tariffPage.loadMoreTariffDetails();
    }

    @And("I see the ZUM ONLINE-ANTRAG button")
    public void iSeeTheZumonilneAntragButton() {
        tariffPage.zumOnlineButton();
    }
}
