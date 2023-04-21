package com.example.testtaskping.model;

import java.time.LocalDateTime;

public class PingResultDto {
    private String ipAddress;

    private String domain;

    private LocalDateTime dateChecked;

    private String status;

    private String pingResult;

    public PingResultDto() {
    }

    public PingResultDto(String ipAddress, String domain, LocalDateTime dateChecked, String status, String pingResult) {
        this.ipAddress = ipAddress;
        this.domain = domain;
        this.dateChecked = dateChecked;
        this.status = status;
        this.pingResult = pingResult;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDomainName() {
        return domain;
    }

    public LocalDateTime getTestDate() {
        return dateChecked;
    }

    public String getStatus() {
        return status;
    }

    public String getPingResult() {
        return pingResult;
    }
}
