package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement heading = $("h2");
    private final SelenideElement buyButton = $("button:has-text('Купить')");
    private final SelenideElement creditButton = $("button:has-text('Купить в кредит')");

    public MainPage() {
        heading.shouldBe(visible, Duration.ofSeconds(20))
                .shouldHave(text("Путешествие дня"));
    }

    public PaymentPage buy() {
        buyButton.click();
        return new PaymentPage();
    }

    public CreditPage buyInCredit() {
        creditButton.click();
        return new CreditPage();
    }
}
