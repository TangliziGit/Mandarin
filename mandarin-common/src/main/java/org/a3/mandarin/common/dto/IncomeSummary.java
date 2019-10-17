package org.a3.mandarin.common.dto;

import org.a3.mandarin.common.entity.Income;

public class IncomeSummary {
    private String date;
    private String type;
    private Double amount;

    public IncomeSummary(){}

    public IncomeSummary(String date, String type, Double amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
