package by.farad.currencyrate.repositories;

import by.farad.currencyrate.models.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findByDateAndCurAbb(LocalDate date, String cur_abbreviation);

    boolean existsByDateAndCurAbb(LocalDate date, String currencyCode);
}
