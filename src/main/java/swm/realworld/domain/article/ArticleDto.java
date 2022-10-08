package swm.realworld.domain.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("Article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class ArticleDto {

    private String slug;

    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String body;
    private List<String> tagList;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean favorited;
    private Long favoritesCount;
    private Author author;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {

        private String username;
        private String bio;
        private String image;
        private Boolean following;
    }
}
