package by.farad.currencyrate.controllers;

import by.farad.currencyrate.models.CurrencyRate;
import by.farad.currencyrate.services.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CurrencyRateController {

    @Autowired
    private CurrencyRateService currencyRateService;

    @GetMapping("/loadRates")
    public ResponseEntity<String> loadRates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        boolean success = currencyRateService.fetchAndSaveRatesByDate(date);
        return success ? ResponseEntity.ok("Курсы валют загружены") : ResponseEntity.status(500).body("Ошибка при загрузке курсов");
    }
}
