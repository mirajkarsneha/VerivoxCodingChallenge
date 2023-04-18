package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.TariffPage;

public class TariffStepDefinition {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final TariffPage tariffPage;

    public TariffStepDefinition() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 60);
        Actions builder = new Actions(driver);

        tariffPage = new TariffPage(driver, wait, builder);
    }

    @Given("that I can open www.verivox.de")
    public void that_i_can_open_www_verivox_de() {
        tariffPage.loadPageAndAcceptCookies();
    }

    @When("I navigate to Versicherungen and select Privathaftpflicht")
    public void i_navigate_to_versicherungen_and_select_privathaftpflicht() {
        tariffPage.navigateToPrivathaftpflichtPage();
    }

    @And("I enter my age and Single ohne Kinder")
    public void i_enter_my_age_and_single_ohne_kinder() {
        tariffPage.enterAgeAndMaritalStatus();
    }

    @Then("I go to the Privathaftpflicht personal information page")
    public void i_go_to_the_privathaftpflicht_personal_information_page() {
        Assert.assertEquals("Privathaftpflicht – jetzt vergleichen und mit Sicherheit sparen.", driver.getTitle());
    }

    @And("I enter my birthdate")
    public void i_enter_my_birthdate() {
        tariffPage.enterBirthDate();
    }

    @And("I enter my zip code")
    public void i_enter_my_zipcode() {
        tariffPage.enterZipCode();
    }

    @And("I click the Jetzt vergleichen button")
    public void i_click_the_jetzt_vergleichen_button() {
        tariffPage.clickJetztVergleichenButton();
    }

    @Then("I should see a page that lists the available tariffs for my selection")
    public void i_should_see_a_page_that_lists_the_available_tariffs_for_my_selection() {
        // TODO: figure out why it is not returning 5
        tariffPage.verifyTariffsGreaterThan(4);
    }

    @Given("the same tariff calculation criteria from scenario 1")
    public void the_same_tariff_calculation_criteria_from_scenario_1() {
        tariffPage.loadPageAndAcceptCookies();
        tariffPage.navigateToPrivathaftpflichtPage();
        tariffPage.enterAgeAndMaritalStatus();
        tariffPage.enterBirthDate();
        tariffPage.enterZipCode();
        tariffPage.clickJetztVergleichenButton();
    }

    @When("I display the tariff Result List page")
    public void i_display_the_tariff_result_list_page() {
        tariffPage.verifyTariffsGreaterThan(1);
    }

    @Then("I should see the total number of available tariffs listed above all the result list")
    public void i_should_see_the_total_number_of_available_tariffs_listed_above_all_the_result_list() {
        WebElement totalTariffsText = driver.findElement(By.xpath("//*[@id=\"vx-insurance\"]/section/pli-page/pli-page-v2/main/section/div[1]/section/div/div[1]/filtered-products-hint/span"));
        wait.until(ExpectedConditions.visibilityOf(totalTariffsText));
        String[] strings = totalTariffsText.getText().split(" ");
        tariffPage.setTotalNoOfTariffs(Integer.valueOf(strings[0]));
        Assert.assertTrue(tariffPage.getTotalNoOfTariffs() > 0);
    }

    @When("I scroll to the end of the result list page")
    public void i_scroll_to_the_end_of_the_result_list_page() {
        tariffPage.scrollToTheBottom();
    }

    @Then("I should see only the first 20 tariffs displayed")
    public void i_should_see_only_the_first_20_tariffs_displayed() {
        tariffPage.verifyTariffsAreDisplayed(20);
    }

    @When("I click on the button labeled 20 weitere Tarife laden")
    public void i_click_on_the_button_labeled_20_weitere_tarife_laden() {
        tariffPage.clickLoadMoreTariffsButton();
    }

    @Then("I should see the next 20 tariffs displayed")
    public void i_should_see_the_next_20_tariffs_displayed() {
        tariffPage.scrollAndLoadMoreTariffs();
    }

    @And("I can continue to load any additional tariffs until all tariffs have been displayed")
    public void i_can_continue_to_load_any_additional_tariffs_until_all_tariffs_have_been_displayed() {
        tariffPage.clickLoadAllTariffs();
        tariffPage.scrollToTheBottom();
        tariffPage.verifyTariffsAreDisplayed(tariffPage.getTotalNoOfTariffs());
    }

    @And("I display the tariff result list page")
    public void iDisplayTheTariffResultListPage() {
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
    public void iSeeTheZUMONLINEANTRAGButton() {
        tariffPage.zumOnlineButton();
    }

}
