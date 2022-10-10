package swm.realworld.domain.article;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @EntityGraph(attributePaths = {"author", "tags"})
    List<Article> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "tags"})
    List<Article> findByAuthorUsername(String username, Pageable pageable);
}
