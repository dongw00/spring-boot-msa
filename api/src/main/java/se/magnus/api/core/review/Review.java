package se.magnus.api.core.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class Review {
    private int productId;
    private int reviewId;
    private String author;
    private String subject;
    private String content;

    @Setter
    private String serviceAddress;

    @Builder
    public Review(int productId, int reviewId, String author, String subject, String content, String serviceAddress) {
        this.productId = productId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }
}
