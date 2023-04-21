package com.example.testtaskping.controller;

import com.example.testtaskping.exception.PingResultNotFoundException;
import com.example.testtaskping.model.PingResultDto;
import com.example.testtaskping.model.attribute.SearchForm;
import com.example.testtaskping.model.attribute.SearchIp;
import com.example.testtaskping.service.PingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class PingController {
    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/all")
    public String getAllPingResults(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        Page<PingResultDto> pingResults = pingService.getAllPingResults(page, size);
        model.addAttribute("pingResults", pingResults.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", pingResults.getTotalPages());
        return "ping-results";
    }

    @GetMapping("/search")
    public String searchPingResults(
            @ModelAttribute("searchForm") SearchForm searchForm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model,
            HttpServletRequest request) {
        Page<PingResultDto> pingResultDtos = pingService.search(
                searchForm.getIp(),
                searchForm.getDomain(),
                searchForm.getStartDate(),
                searchForm.getEndDate(),
                searchForm.getStatus(),
                page,
                size);
        model.addAttribute("pingResults", pingResultDtos);
        model.addAttribute("param", request.getParameterMap());
        model.addAttribute("searchForm", new SearchForm());
        return "ping-search";
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("searchIp", new SearchIp());
        return "ping-search-without-id";
    }
    @GetMapping("/ping")
    public String getPingResult(@ModelAttribute("searchIp") SearchIp id, Model model) {
        PingResultDto pingResultDto;
        try {
            Long idForSearch = Long.valueOf(id.getIp());
            pingResultDto = pingService.getPingResultById(idForSearch);
        } catch (PingResultNotFoundException exception) {
            return "404";
        }
        model.addAttribute("pingResult", pingResultDto);
        return "ping-search-id";
    }
}
