package com.skm.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

@Schema(
        name = "Cards",
        description = "Schema to hold cards info"
)
@Data
public class CardsDto {
    @Schema(
            description = "Mobile number of owner of the card",
            example = "3875121101"
    )
    @NotEmpty(message = "Mobile number cannot be null")
    @Pattern(regexp ="(^$|[0-9]{10})" ,message = "Number should contain 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "card Number cannot be null")
    @Pattern(regexp="(^$|[0-9]{12})",message = "CardNumber must be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "card Type cannot be null")
    private String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    private Integer totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private Integer amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    private Integer availableAmount;
}
