package com.url.shortner.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClickEventDto {


    private LocalDate clickDate;
    private Long count;

    public LocalDate getClickDate() {
        return clickDate;
    }

    public void setClickDate(LocalDate clickDate) {
        this.clickDate = clickDate;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
