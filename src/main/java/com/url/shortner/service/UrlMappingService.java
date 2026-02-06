package com.url.shortner.service;

import com.url.shortner.dtos.ClickEventDto;
import com.url.shortner.dtos.UrlMappingDto;
import com.url.shortner.models.ClickEvent;
import com.url.shortner.models.UrlMapping;
import com.url.shortner.models.User;
import com.url.shortner.repo.ClickEventRepository;
import com.url.shortner.repo.UrlMappingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;

    public UrlMappingService(UrlMappingRepository urlMappingRepository,
                             ClickEventRepository clickEventRepository) {
        this.urlMappingRepository = urlMappingRepository;
        this.clickEventRepository = clickEventRepository;
    }


    public UrlMappingDto createShortUrl(String originalUrl, User user) {

        String shortURl = generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setUser(user);
        urlMapping.setShortUrl(shortURl);

        UrlMapping saved = urlMappingRepository.save(urlMapping);

        return toDto(saved);
    }

    private UrlMappingDto toDto(UrlMapping urlMapping) {

        UrlMappingDto urlMappingDto = new UrlMappingDto();
        urlMappingDto.setId(urlMappingDto.getId());
        urlMappingDto.setOriginalUrl(urlMapping.getOriginalUrl());
        urlMappingDto.setShortUrl(urlMapping.getShortUrl());
        urlMappingDto.setClickCount(urlMapping.getClickCount());
        urlMappingDto.setCreatedDate(urlMapping.getCreatedDate());
        urlMappingDto.setUserName(urlMapping.getUser().getUserName());
        return urlMappingDto;
    }

    private String generateShortUrl() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                            "abcdefghijklmnopqrstuvwxyz" +
                            "0123456789";

        Random random = new Random();

        StringBuilder shortUrl = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));

        }
        return shortUrl.toString();
    }

    public List<UrlMappingDto> getUserUrls(User user) {
        return urlMappingRepository.findByUser(user)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<ClickEventDto> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {

        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingAndClickDateBetween(urlMapping, start, end);

            return clickEvents.stream()
                    .collect(Collectors
                            .groupingBy(
                                    clickEvent -> clickEvent.getClickDate().toLocalDate(),
                                    Collectors.counting()))
                    .entrySet()
                    .stream()
                    .map(entry -> {
                        ClickEventDto clickEventDto = new ClickEventDto();
                        clickEventDto.setClickDate(entry.getKey());
                        clickEventDto.setCount(entry.getValue());
                        return clickEventDto;
                    }).
                    toList();
        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> byUser = urlMappingRepository.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepository.findByUrlMappingInAndClickDateBetween(byUser,
                start.atStartOfDay(), end.plusDays(1).atStartOfDay());

        return clickEvents.stream()
                .collect(
                        Collectors.groupingBy(clickEvent -> clickEvent.getClickDate().toLocalDate(),
                                Collectors.counting()));

    }
}
