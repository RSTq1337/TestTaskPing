package com.example.testtaskping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Ping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ipAddress;
    private String domainName;
    private LocalDateTime checkDate;
    private String status;
    private String pingResult;

    public Ping() {
    }

    public Ping(Long id, String ipAddress, String domainName, LocalDateTime checkDate, String status, String pingResult) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.domainName = domainName;
        this.checkDate = checkDate;
        this.status = status;
        this.pingResult = pingResult;
    }

    public Ping(String ipAddress, String domainName, LocalDateTime checkDate, String status, String pingResult) {
        this.ipAddress = ipAddress;
        this.domainName = domainName;
        this.checkDate = checkDate;
        this.status = status;
        this.pingResult = pingResult;
    }

    public Long getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDomainName() {
        return domainName;
    }

    public LocalDateTime getTestDate() {
        return checkDate;
    }

    public String getStatus() {
        return status;
    }

    public String getPingResult() {
        return pingResult;
    }
}
