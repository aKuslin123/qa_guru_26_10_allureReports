package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Condition;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class SelenideTest extends TestBase {

    private static final String REPOSITORY =  "eroshenkoam/allure-example";
    private static final int ISSUE = 80;

    @Test
    void testIssueSearch() {
        open("https://github.com");
        $(".search-input").click();
        $("#query-builder-test").sendKeys(REPOSITORY);
        $("#query-builder-test").pressEnter();
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $(withText("#" + ISSUE)).should(Condition.exist);
    }

    @Test
    void testIssueSearchLambdaSteps() {
        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Кликаем на поиск", () -> {
            $(".search-input").click();
        });
        step("Вводим запрос с названием репозитория " + REPOSITORY, () -> {
            $("#query-builder-test").sendKeys(REPOSITORY);
        });
        step("Жмем Enter", () -> {
            $("#query-builder-test").pressEnter();
        });
        step("Кликаем на репозиторий " + REPOSITORY, () -> {
            $(linkText("eroshenkoam/allure-example")).click();
        });
        step("Кликаем на кнопку Issues", () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие issue с номером " + ISSUE, () -> {
            $(withText("#" + ISSUE)).should(Condition.exist);
            Allure.getLifecycle().addAttachment(
                    "Исходники страницы",
                    "text/html",
                    "html",
                    WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)
            );
        });
    }

    @Test
    @Feature("Issue")
    @Story("Наличие Issue в репозитории")
    @DisplayName("Проверяем наличие issue")
    @Owner("Kuslin")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://testing.github.com")
    void testAnnotatedSteps() {
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchInputClick();
        steps.searchInputSendKeys(REPOSITORY);
        steps.searchInputPressEnter();
        steps.searchOutputClick(REPOSITORY);
        steps.issuesTabClick();
        steps.issuesExist(ISSUE);
    }
}