package com.dc.quotify.Model;

public class DailyModel {
    String quote,category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public DailyModel() {
    }

    public DailyModel(String quote) {
        this.quote = quote;
    }
}
