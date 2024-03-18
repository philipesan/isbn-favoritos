package com.ancora.teste.isbn.dto.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivroApiResponseDTO {
    private String isbn;
    private String title;
    private String subtitle;
    private List<String> authors;
    private String publisher;
    private String synopsis;
    private String dimensions;
    private Integer year;
    private String format;
    private Integer page_count;
    private List<String> subjects;
    private String location;
    private Double retail_price;
    private String cover_url;
    private String provider;
	
}
