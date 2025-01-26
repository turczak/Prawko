package pl.turlap.prawko.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "first_name", columnDefinition = "VARCHAR(25)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(50)")
    private String lastName;

    @Column(name = "user_name", columnDefinition = "VARCHAR(25)")
    private String userName;

    @Column(name = "email", columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(60)")
    private String password;

    @Column(name = "enabled", columnDefinition = "BOOLEAN")
    private Boolean enabled;

    @Column(name = "created", columnDefinition = "DATETIME")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated", columnDefinition = "DATETIME")
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Test> tests;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

}
