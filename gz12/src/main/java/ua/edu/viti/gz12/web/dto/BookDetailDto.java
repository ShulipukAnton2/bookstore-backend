package ua.edu.viti.gz12.web.dto;

import java.math.BigDecimal;

public record BookDetailDto(Long id, String title, String author, BigDecimal price,
                            Integer year, String genre, String coverUrl, String description) {}
