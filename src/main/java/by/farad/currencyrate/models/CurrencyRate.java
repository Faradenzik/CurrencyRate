package by.farad.currencyrate.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Cur_ID")
    private Integer curId;

    @JsonProperty("Date")
    private LocalDate date;

    @JsonProperty("Cur_Abbreviation")
    private String curAbb;

    @JsonProperty("Cur_Scale")
    private Integer curScale;

    @JsonProperty("Cur_Name")
    private String curName;

    @JsonProperty("Cur_OfficialRate")
    private Double curOfficialRate;
}
