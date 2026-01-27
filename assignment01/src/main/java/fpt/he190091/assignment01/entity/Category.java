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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private Long categoryID;

    @Column(name = "CategoryName", nullable = false)
    private String categoryName;

    @Column(name = "CategoryDescription")
    private String categoryDescription;

    @ManyToOne
    @JoinColumn(name = "ParentCategoryID")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> subCategories;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;
}
