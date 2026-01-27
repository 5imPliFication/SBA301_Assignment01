package fpt.he190091.assignment01.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TagID")
    private Long tagID;

    @Column(name = "TagName", nullable = false)
    private String tagName;

    @Column(name = "Note")
    private String note;

    @ManyToMany(mappedBy = "tags")
    private Set<NewsArticle> newsArticles;
}
