package com.example.testtaskping.model;

import java.time.LocalDate;

public class SearchForm {
    private String ip;
    private String domain;
    private LocalDate startDate;
    private LocalDate endDate;
    private TestStatus status;

    public SearchForm() {
    }

    public SearchForm(String ip, String domain, LocalDate startDate, LocalDate endDate, TestStatus status) {
        this.ip = ip;
        this.domain = domain;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }
}
