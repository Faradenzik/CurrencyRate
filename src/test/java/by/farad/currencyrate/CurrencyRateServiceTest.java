package by.farad.currencyrate;

import by.farad.currencyrate.models.CurrencyRate;
import by.farad.currencyrate.repositories.CurrencyRateRepository;
import by.farad.currencyrate.services.CurrencyRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CurrencyRateServiceTest {

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @BeforeEach
    void setUp() {
        currencyRateRepository.deleteAll();
    }

    @Test
    @Transactional
    void getRateByDateAndCurrencyCode_Found() {
        LocalDate date = LocalDate.of(2024, 7, 20);
        CurrencyRate rate = new CurrencyRate(1, date, "USD", 1, "Dollar", 2.5);
        currencyRateRepository.save(rate);

        CurrencyRate result = currencyRateService.getRateByDateAndCurAbb(date, "USD");
        assertNotNull(result);
        assertEquals("USD", result.getCurAbb());
        assertEquals(2.5, result.getCurOfficialRate());
    }

    @Test
    @Transactional
    void getRateByDateAndCurrencyCode_NotFound() {
        LocalDate date = LocalDate.of(2024, 7, 20);
        CurrencyRate result = currencyRateService.getRateByDateAndCurAbb(date, "USD");
        assertNull(result);
    }
}
