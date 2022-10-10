package swm.realworld.domain.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import swm.realworld.domain.article.tag.Tag;
import swm.realworld.domain.user.User;
import swm.realworld.domain.user.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void when_save_article_with_tags_expect_saved() {
        Article article = Article.builder()
                .title("title")
                .slug("slug-1")
                .description("desc")
                .body("body")
                .author(savedUser)
                .build();

        Set<Tag> tags = new HashSet<>();
        tags.add(Tag.builder().name("tag-1").build());
        tags.add(Tag.builder().name("tag-2").build());
        article.setTags(tags);

        Article save = articleRepository.save(article);

        assertThat(save.getTags().size()).isEqualTo(tags.size());
    }

    @Test
    void when_list_all_expect_articles() {
        Article article1 = Article.builder()
                .title("title")
                .slug("slug-1")
                .description("desc")
                .body("body")
                .author(savedUser).build();

        var otherUser = new User(
                "otherUser",
                "otherUser@email.com",
                "rawPassword");
        savedUser = userRepository.save(otherUser);

        Article article2 = Article.builder()
                .title("title")
                .slug("slug-2")
                .description("desc")
                .body("body")
                .author(otherUser).build();
        articleRepository.saveAll(List.of(article1, article2));

        List<Article> actual = articleRepository.findAll(PageRequest.of(0, 2));

        assertEquals(2, actual.size());
    }

    @Test
    void when_list_by_authorName_expect_articles_by_authorName() {
        Article article1 = Article.builder()
                .title("title")
                .slug("slug-1")
                .description("desc")
                .body("body")
                .author(savedUser).build();

        var otherUser = new User(
                "otherUser",
                "otherUser@email.com",
                "rawPassword");
        savedUser = userRepository.save(otherUser);

        Article article2 = Article.builder()
                .title("title")
                .slug("slug-2")
                .description("desc")
                .body("body")
                .author(otherUser).build();
        articleRepository.saveAll(List.of(article1, article2));

        List<Article> actual = articleRepository.findByAuthorUsername(otherUser.getUsername(), PageRequest.of(0, 3));

        assertEquals(1, actual.size());
        assertThat(actual.get(0).getAuthor().getUsername()).isEqualTo(otherUser.getUsername());
    }
}