package com.url.shortner.service;

import com.url.shortner.dtos.UrlMappingDto;
import com.url.shortner.models.UrlMapping;
import com.url.shortner.models.User;
import com.url.shortner.repo.UrlMappingRepository;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
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

        for (int i = 0; i <8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));

        }
        return shortUrl.toString();
    }
}
