package com.example.buixuanan.moneyconvert.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MoneyDTO implements Serializable {
    private Boolean success;
    private String terms;
    private String privacy;
    private Integer timestamp;
    private String source;
    private Map quotes = new HashMap();

    public Map getQuotes() {
        return quotes;
    }

    public void setQuotes(Map quotes) {
        this.quotes = quotes;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
