package fpt.he190091.assignment01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="newsarticle")
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NewsArticleID")
    private Long newsArticleID;

    @Column(name = "NewsTitle", nullable = false)
    private String newsTitle;

    @Column(name = "Headline")
    private String headline;

    @Column(name = "CreatedDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "NewsContent", nullable = false)
    private String newsContent;

    @Column(name = "NewsSource")
    private String newsSource;

    @Column(name = "NewsStatus", nullable = false)
    private Boolean newsStatus;

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "CreatedByID", nullable = false)
    private SystemAccount createdBy;

    @ManyToOne
    @JoinColumn(name = "UpdatedByID")
    private SystemAccount updatedBy;

    @Column(name = "ModifiedDate")
    private LocalDateTime modifiedDate;

    @ManyToMany
    @JoinTable(
            name = "News_Tag",
            joinColumns = @JoinColumn(name = "NewsArticle_Id"),
            inverseJoinColumns = @JoinColumn(name = "Tag_Id")
    )
    private Set<Tag> tags = new HashSet<>();
}
