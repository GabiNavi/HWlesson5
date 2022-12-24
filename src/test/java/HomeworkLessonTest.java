import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class HomeworkLessonTest {
    @BeforeAll
    static void beforeAll() {
        Configuration.browser = "edge";
        Configuration.holdBrowserOpen = false;
        Configuration.browserSize = "1440x900";
        Configuration.baseUrl = "https://demoqa.com";
    }

    @Test
    void FillFormTest() {

        //Выносим основные переменные
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String emailAddress = "ivan@ivanov.com";
        String mobileNumber = "1234567890";
        String subject1 = "chem";
        String subject2 = "math";
        String currentAddress = "Random st., app. 12";

        //Прописываем настройки браузера
        open("/automation-practice-form");
        Selenide.executeJavaScript("document.body.style.zoom='100%'");
        Selenide.executeJavaScript("$('#fixedban').remove()");
        Selenide.executeJavaScript("$('footer').remove()");

        //Подтверждаем, что открыли страницу, и заполняем поля имен и адрес почты
        $(".main-header").shouldHave(text("Practice Form"));
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(emailAddress);

        //Выбираем пол
        $("#gender-radio-1").parent().click();
//        $(byText("Male")).click(); //
//        $("#genterWrapper").$(byText("Male")).click(); //
//        $("label[for = gender-radio-1]").click(); //

        //Указываем телефон
        $("#userNumber").setValue(mobileNumber);

        //Задаем дату рождения
        $("#dateOfBirthInput").click(); // Выбираем 30 June (5) 1991
        $(".react-datepicker__month-select").selectOption("June");
        $(".react-datepicker__year-select").selectOption("1991");
//        $(".react-datepicker__month-select").selectOptionByValue("5");
//        $(".react-datepicker__year-select").selectOption("1991");
        $(".react-datepicker__day--030:not(.react-datepicker__day--outside-month)").click();

        //Указываем интересы
        $("#subjectsInput").setValue(subject1).pressEnter();
        $("#subjectsInput").setValue(subject2).pressEnter();

        //Скролим до кнопки submit
        $("#submit").scrollIntoView(false);

        //Выбираем хобби
        $("label[for = hobbies-checkbox-1]").click();
        $("label[for = hobbies-checkbox-3]").click();

        //Загружаем файл
//        $("uploadPicture").uploadFile(new File("src/test/resources/img/1.png"));
        $("#uploadPicture").uploadFromClasspath("img/1.png"); //Предполагает наличие папки resources

        //Указываем текущий адрес
        $("#currentAddress").setValue(currentAddress);

        //Указываем регион и город
        $("#stateCity-wrapper").click();
        $("#react-select-3-option-3").click();
        $("#city").click();
        $("#react-select-4-option-1").click();

        //Отпрвляем все введенные данные
        $("#submit").click();

        //Проверяем результат
        $(".modal-content").should(Condition.appears);
        $(".table-responsive").shouldHave(text(firstName),
                text(lastName),
                text(emailAddress),
                text(mobileNumber),
                text(subject1),
                text(subject2),
                text(currentAddress));
    }
}
