package swm.realworld.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findFirstByUsername(String username);

    Optional<Article> findByUsernameOrEmail(String username, String email);

    Optional<Article> findFirstByEmail(String email);
}
