package swm.realworld.domain.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import swm.realworld.domain.user.User;
import swm.realworld.domain.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@EnableJpaAuditing
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    User savedUser;

    @BeforeEach
    void setUp() {
        var userToSave = new User(
                "username",
                "user@email.com",
                "rawPassword");

        savedUser = userRepository.save(userToSave);
    }

    @Test
    void when_save_article_expect_saved() {
        Article article = Article.builder()
                .title("title")
                .slug("slug-1")
                .description("desc")
                .body("body")
                .author(savedUser)
                .build();

        Article save = articleRepository.save(article);

        assertThat(save.getSlug()).isEqualTo("slug-1");
        assertThat(save.getAuthor().getEmail()).isEqualTo(savedUser.getEmail());
    }
}