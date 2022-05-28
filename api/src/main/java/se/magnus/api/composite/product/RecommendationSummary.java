package se.magnus.api.composite.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendationSummary {

    private int recommendationId;
    private String author;
    private int rate;
    private String content;

    public RecommendationSummary(int recommendationId, String author, int rate, String content) {
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
        this.content = content;
    }
}
