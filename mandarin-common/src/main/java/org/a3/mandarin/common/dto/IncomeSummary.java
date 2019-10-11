package org.a3.mandarin.common.dto;

import org.a3.mandarin.common.entity.Income;

public class IncomeSummary {
    private String date;
    private Double amount;

    public IncomeSummary(){}

    public IncomeSummary(String date, Double amount) {
        this.date = date;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
