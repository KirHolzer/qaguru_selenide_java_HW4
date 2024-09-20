import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SoftAssertionsTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://github.com";
        Configuration.pageLoadStrategy = "eager";

    }
    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver(); // Закрывает браузер после каждого теста
    }
    String snippetOfJUnit5 =  """
                        @ExtendWith({SoftAssertsExtension.class})
                        class Tests {
                          @Test
                          void test() {
                            Configuration.assertionMode = SOFT;
                            open("page.html");
                                                
                            $("#first").should(visible).click();
                            $("#second").should(visible).click();
                          }
                        }
                """;
    @Test
    void jUnitSoftAssertions() {
        open("/selenide/selenide"); //Откройте страницу Selenide в Github
        $("#wiki-tab").click();
        // проверка -  что страница SoftAssertions есть в списке Pages
        $(".markdown-body").shouldHave(text("Soft assertions"));
        $(byText("Soft assertions")).click();
        sleep(5000);
        $("#wiki-body").shouldHave(text(snippetOfJUnit5));
        //sleep(5000);
    }

    // добавлена проверка с правого сайдбара
    @Test
    void jUnitSoftAssertionsSidebar(){
        open("/selenide/selenide");
        $("#wiki-tab").click();
        $(".wiki-rightbar").$(byText("Show 3 more pages…")).click();
        $(".wiki-rightbar").shouldHave(text("SoftAssertions"));
        $(byText("SoftAssertions")).click();
        $("#wiki-body").shouldHave(text(snippetOfJUnit5));
    }
}