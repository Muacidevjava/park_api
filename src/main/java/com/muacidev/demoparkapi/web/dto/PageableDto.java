package com.muacidev.demoparkapi.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageableDto {

    private List content = new ArrayList<>();

    private Boolean first;

    private Boolean last;

    @JsonProperty("page")
    private Integer number;

    private int size;

    @JsonProperty("pageElements")
    private int numberOfElements;

    private int totalPages;

    private int totalElements;


}
