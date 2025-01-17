package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(10)")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}