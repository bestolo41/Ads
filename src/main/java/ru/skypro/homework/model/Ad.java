package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ad_pk")
    private int pk;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    private int price;

    private String title;

    private String description;

    private String image;
}
