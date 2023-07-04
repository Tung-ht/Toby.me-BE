package tunght.toby.be.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ArticleDto {
    private String slug;

    @NotBlank(message = "bạn chưa điền tiêu đề")
    private String title;
    @NotBlank(message = "bạn chưa điền mô tả")
    private String description;
    @NotBlank(message = "bạn chưa điền nội dung")
    private String body;
    private List<String> tagList;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean favorited;
    private Long favoritesCount;
    private ProfileDto author;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SingleArticle<T> {
        private T article;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MultipleArticle {
        private List<ArticleDto> articles;
        private Integer articlesCount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        private String title;
        private String description;
        private String body;
        private List<String> tagList;
    }
}
