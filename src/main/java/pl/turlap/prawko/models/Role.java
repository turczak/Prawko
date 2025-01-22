package pl.turlap.prawko.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity(name = "role")
@Data
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(10)")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<User> users;
}