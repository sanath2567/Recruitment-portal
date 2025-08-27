package com.project.zidio.dto;

import java.time.LocalDateTime;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.project.zidio.Enum.PaymentStatus;
import com.project.zidio.Enum.PaymentType;

public class PaymentDTO {
    public Long id;
    public Long userId;
    public Long planId;
    public BigDecimal amount;
    public String currency;
    public PaymentStatus paymentStatus;
    public String transactionId;
    public PaymentType paymentType;
    public LocalDateTime paymentDate;
}


