package com.url.shortner.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.sql.results.spi.LoadContexts;

import java.time.LocalDateTime;

@Entity
@Table(name = "click_events")
public class ClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime clickDate;

    @ManyToOne
    @JoinColumn(name = "url_mapping_id")
    private URLMapping urlMapping;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getClickDate() {
        return clickDate;
    }

    public void setClickDate(LocalDateTime clickDate) {
        this.clickDate = clickDate;
    }

    public URLMapping getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(URLMapping urlMapping) {
        this.urlMapping = urlMapping;
    }
}
