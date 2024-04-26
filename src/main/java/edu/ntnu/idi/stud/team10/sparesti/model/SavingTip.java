package edu.ntnu.idi.stud.team10.sparesti.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "saving_tips")
/** Simple data entity representing a random daily saving tip that user can see */
public class SavingTip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String message;
}
