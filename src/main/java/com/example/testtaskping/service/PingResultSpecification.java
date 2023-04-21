package com.example.testtaskping.service;

import com.example.testtaskping.model.Ping;
import com.example.testtaskping.model.TestStatus;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class PingResultSpecification implements Specification<Ping> {
    private final String searchTerm;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final TestStatus testStatus;

    public PingResultSpecification(String searchTerm, LocalDateTime startDate, LocalDateTime endDate, TestStatus testStatus) {
        this.searchTerm = searchTerm;
        this.startDate = startDate;
        this.endDate = endDate;
        this.testStatus = testStatus;
    }

    @Override
    public Predicate toPredicate(Root<Ping> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(root.get("domainName"), "%" + searchTerm + "%"),
                    criteriaBuilder.like(root.get("ipAddress"), "%" + searchTerm + "%")
            );
            predicates.add(searchPredicate);
        }

        if (startDate != null) {
            Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("testDate"), startDate);
            predicates.add(startDatePredicate);
        }

        if (endDate != null) {
            Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("testDate"), endDate);
            predicates.add(endDatePredicate);
        }

        if (testStatus != null) {
            Predicate testStatusPredicate = criteriaBuilder.equal(root.get("testStatus"), testStatus);
            predicates.add(testStatusPredicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public static Specification<Ping> matchStatus(TestStatus status) {
        return (root, query, builder) -> {
            if (status == null) {
                return null;
            }
            return builder.equal(root.get("status"), status.getName());
        };
    }

    public static Specification<Ping> containsIp(String ip) {
        return (root, query, builder) -> {
            if (ip == null) {
                return null;
            }
            return builder.like(root.get("ipAddress"), "%" + ip + "%");
        };
    }

    public static Specification<Ping> containsDomain(String domain) {
        if (StringUtils.isEmpty(domain)) {
            return null;
        }
        return (root, query, builder) ->
                builder.like(root.get("domainName"), "%" + domain + "%");
    }

    public static Specification<Ping> greaterThanOrEqualToStartDate(LocalDate startDate) {
        return (root, query, builder) -> {
            if (startDate == null) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("checkDate"), startDate.atStartOfDay());
        };
    }

    public static Specification<Ping> lessThanOrEqualToEndDate(LocalDate endDate) {
        if (endDate == null) {
            return null;
        }
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("checkDate"), endDate.atTime(LocalTime.MAX));
    }
}
