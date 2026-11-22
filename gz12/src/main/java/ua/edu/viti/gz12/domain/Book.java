package ua.edu.viti.gz12.domain;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer year;
    private String genre;
    private String coverUrl;
    private String description;

    public Book() {}

    public Book(Long id, String title, String author, BigDecimal price, Integer year,
                String genre, String coverUrl, String description) {
        this.id = id; this.title = title; this.author = author; this.price = price;
        this.year = year; this.genre = genre; this.coverUrl = coverUrl; this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
