package fpt.he190091.assignment01.repository;

import fpt.he190091.assignment01.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByTagName(String tagName);
}
