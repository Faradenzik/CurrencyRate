package by.farad.currencyrate.services;

import by.farad.currencyrate.models.CurrencyRate;
import by.farad.currencyrate.repositories.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

@Service
public class CurrencyRateService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;
    private final WebClient webClient = WebClient.create("https://www.nbrb.by/api/exrates");

    //check fo duplicates
    public boolean fetchAndSaveRatesByDate(LocalDate date) {
        List<CurrencyRate> rates = getData(date);

        for (CurrencyRate rate : rates) {
            boolean exists = currencyRateRepository.existsByDateAndCurAbb(date, rate.getCurAbb());

            if (!exists) {
                currencyRateRepository.save(rate);
            }
        }
        return true;
    }

    // getting list of currencies
    public List<CurrencyRate> getData(LocalDate date) {
        try {
            List<CurrencyRate> period0Rates = webClient.get()
                    .uri("/rates?ondate=" + date + "&periodicity=0")
                    .retrieve()
                    .bodyToFlux(CurrencyRate.class)
                    .collectList().block();

            List<CurrencyRate> period1Rates = webClient.get()
                    .uri("/rates?ondate=" + date + "&periodicity=1")
                    .retrieve()
                    .bodyToFlux(CurrencyRate.class)
                    .collectList().block();

            period0Rates.addAll(period1Rates);
            return period0Rates;

        } catch (Exception e) {
            return List.of();
        }
    }


    // getting currency info by date and code
    public CurrencyRate getRateByDateAndCurAbb(LocalDate date, String currencyCode) {
        return currencyRateRepository.findByDateAndCurAbb(date, currencyCode).orElse(null);
    }
}
