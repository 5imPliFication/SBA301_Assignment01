package fpt.he190091.assignment01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "systemaccount")
public class SystemAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private Long accountID;

    @Column(name = "AccountName", nullable = false)
    private String accountName;

    @Column(name = "AccountEmail", nullable = false, unique = true)
    private String accountEmail;

    @Column(name = "AccountRole", nullable = false)
    private Integer accountRole; // 1 = Admin, 2 = Staff

    @Column(name = "AccountPassword", nullable = false)
    private String accountPassword;

    @OneToMany(mappedBy = "createdBy")
    private List<NewsArticle> createdNews;

    @OneToMany(mappedBy = "updatedBy")
    private List<NewsArticle> updatedNews;
}
