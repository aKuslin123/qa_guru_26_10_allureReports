package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

public class WebSteps {
    @Attachment(value = "Скриншот", type = "image/png", fileExtension = "png")
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Открываем главную страницу")
    public void openMainPage() {
        open("https://github.com");
    }

    @Step("Кликаем на поиск")
    public void searchInputClick() {
        $(".search-input").click();
    }

    @Step("Вводим запрос с названием репозитория {repo}")
    public void searchInputSendKeys(String repo) {
        $("#query-builder-test").sendKeys(repo);
    }

    @Step("Жмем Enter")
    public void searchInputPressEnter() {
        $("#query-builder-test").pressEnter();
    }

    @Step("Кликаем на репозиторий {repo}")
    public void searchOutputClick(String repo) {
        $(linkText(repo)).click();
    }

    @Step("Кликаем на кнопку Issues")
    public void issuesTabClick() {
        $("#issues-tab").click();
        attachScreenshot();
    }

    @Step("Проверяем наличие issue с номером {issue}")
    public void issuesExist(int issue) {
        $(withText("#" + issue)).should(Condition.exist);
        Allure.getLifecycle().addAttachment(
                "Исходники страницы",
                "text/html",
                "html",
                WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)
        );
    }
}
