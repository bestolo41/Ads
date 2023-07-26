package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
public class Comment {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_pk")
    private int pk;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    private long createAt;
    private int adId;
    private String text;

    public Comment() {
        this.createAt = new Date().getTime();
    }

    public Comment(User author, int adId, String text) {
        this.author = author;
        this.createAt = System.currentTimeMillis();
        this.adId = adId;
        this.text = text;
    }
}
