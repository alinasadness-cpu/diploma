package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.db.DbUtils;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Оплата тура по дебетовой карте")
public class PaymentTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.headless = false;
    }

    @BeforeEach
    void cleanUp() {
        DbUtils.cleanDatabase();
    }

    @Test
    @DisplayName("Успешная оплата APPROVED-картой")
    void shouldPayWithApprovedCard() {
        var mainPage = open("/", MainPage.class);
        var paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getApprovedCard());
        paymentPage.verifySuccess();

        assertThat(DbUtils.getPaymentStatus()).isEqualTo("APPROVED");
        assertThat(DbUtils.getOrderCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Отклонение оплаты DECLINED-картой")
    void shouldDeclineWithDeclinedCard() {
        var mainPage = open("/", MainPage.class);
        var paymentPage = mainPage.buy();
        paymentPage.fillForm(DataHelper.getDeclinedCard());
        paymentPage.verifyError();

        assertThat(DbUtils.getPaymentStatus()).isEqualTo("DECLINED");
    }
}