package edu.ntnu.idi.stud.team10.sparesti.model;

import jakarta.persistence.*;
import lombok.*;


/**
 * Badge entity for storage in database
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "badges")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String title; //this and/or imageurl could theoretically be unique?

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;
}
