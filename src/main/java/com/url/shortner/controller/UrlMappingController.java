package com.url.shortner.controller;

import com.url.shortner.dtos.ClickEventDto;
import com.url.shortner.dtos.UrlMappingDto;
import com.url.shortner.dtos.UrlShortenRequest;
import com.url.shortner.service.UrlMappingService;
import com.url.shortner.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    private final UserService userService;

    public UrlMappingController(UrlMappingService urlMappingService, UserService userService) {
        this.urlMappingService = urlMappingService;
        this.userService = userService;
    }

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDto> createShortUrl(@RequestBody @Valid UrlShortenRequest request,
                                                        Principal principal) {

        return ResponseEntity.ok(urlMappingService.createShortUrl(request.getOriginalUrl(), principal));
    }

    @GetMapping("/my-urls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDto>> getUserUrls(Principal principal) {

        return ResponseEntity.ok(urlMappingService.getUserUrls(principal));
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDto>> getUserUrls(@PathVariable String shortUrl,
                                                           @RequestParam LocalDateTime startDate,
                                                           @RequestParam LocalDateTime endDate) {

        return ResponseEntity.ok(urlMappingService.getClickEventsByDate(shortUrl, startDate, endDate));
    }

    @GetMapping("/analytics/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
                                                                     @RequestParam LocalDate startDate,
                                                                     @RequestParam LocalDate endDate) {

        return ResponseEntity.ok(urlMappingService.getTotalClicksByDate(principal, startDate, endDate));
    }
}
