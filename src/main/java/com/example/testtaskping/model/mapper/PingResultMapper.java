package com.example.testtaskping.model.mapper;

import com.example.testtaskping.model.Ping;
import com.example.testtaskping.model.PingResultDto;
import org.springframework.stereotype.Component;

@Component
public class PingResultMapper {

    public static PingResultDto toDto(Ping entity) {
        return new PingResultDto(
                entity.getIpAddress(),
                entity.getDomainName(),
                entity.getTestDate(),
                entity.getStatus(),
                entity.getPingResult()
        );
    }

    public Ping toEntity(PingResultDto dto, long id) {
        return new Ping(
                id,
                dto.getIpAddress(),
                dto.getDomainName(),
                dto.getTestDate(),
                dto.getStatus(),
                dto.getPingResult()
        );
    }
}