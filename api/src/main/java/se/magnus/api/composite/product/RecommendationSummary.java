package se.magnus.api.composite.product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendationSummary {

    private final int recommendationId;
    private final String author;
    private final int rate;

    @Builder
    public RecommendationSummary(int recommendationId, String author, int rate) {
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
    }
}
