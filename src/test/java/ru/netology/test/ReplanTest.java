package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGen;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ReplanTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldReschedule() {
        String firstDate = DataGen.generateDate(5);
        String secondDate = DataGen.generateDate(10);
        $("[data-test-id='city'] input").setValue(DataGen.generateCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDate);
        $("[data-test-id='name'] input").setValue(DataGen.generateName("ru"));
        $("[data-test-id='phone'] input").setValue(DataGen.generatePhone("ru"));
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondDate);
        $(".button").click();
        $("div.notification:nth-child(4)").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .button").click();
        $("div.notification:nth-child(3) > div:nth-child(3)").shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondDate));

    }

}
