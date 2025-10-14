
package com.example.busapp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role; // ROLE_USER or ROLE_ADMIN

    @Column(nullable = false, scale = 2, precision = 19)
    private BigDecimal credit = BigDecimal.ZERO;

    public User() {}

    public User(String email, String passwordHash, String role, BigDecimal credit) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.credit = credit == null ? BigDecimal.ZERO : credit;
    }

    // getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public BigDecimal getCredit() { return credit; }
    public void setCredit(BigDecimal credit) { this.credit = credit; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
