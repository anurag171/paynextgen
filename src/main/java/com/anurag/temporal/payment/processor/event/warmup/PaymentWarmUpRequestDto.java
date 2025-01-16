package com.anurag.temporal.payment.processor.event.warmup;



import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PaymentWarmUpRequestDto {
    @NotBlank
    @Pattern(regexp = "warm me up")
    private String warmUpString;

    @Min(10)
    @Max(20)
    private int warmUpNumber;

    @Valid
    private PaymentWarmUpEnumDto warmUpEnumDto;

    @NotNull
    private BigDecimal warmUpBigDecimal;

    public String getWarmUpString() {
        return warmUpString;
    }

    public void setWarmUpString(String warmUpString) {
        this.warmUpString = warmUpString;
    }

    public int getWarmUpNumber() {
        return warmUpNumber;
    }

    public void setWarmUpNumber(int warmUpNumber) {
        this.warmUpNumber = warmUpNumber;
    }

    public PaymentWarmUpEnumDto getWarmUpEnumDto() {
        return warmUpEnumDto;
    }

    public void setWarmUpEnumDto(PaymentWarmUpEnumDto warmUpEnumDto) {
        this.warmUpEnumDto = warmUpEnumDto;
    }

    public BigDecimal getWarmUpBigDecimal() {
        return warmUpBigDecimal;
    }

    public void setWarmUpBigDecimal(BigDecimal warmUpBigDecimal) {
        this.warmUpBigDecimal = warmUpBigDecimal;
    }

    @Override
    public String toString() {
        return "PaymentWarmUpRequestDto{" +
                "warmUpString='" + warmUpString + '\'' +
                ", warmUpNumber=" + warmUpNumber +
                ", warmUpEnumDto=" + warmUpEnumDto +
                ", warmUpBigDecimal=" + warmUpBigDecimal +
                '}';
    }
}