package se.magnus.api.composite.product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewSummary {

    private final int reviewId;
    private final String author;
    private final String subject;

    @Builder
    public ReviewSummary(int reviewId, String author, String subject) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
    }
}
