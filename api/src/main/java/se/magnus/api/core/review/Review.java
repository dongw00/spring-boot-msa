package se.magnus.api.core.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Review {
    private int productId;
    private int reviewId;
    private String author;
    private String subject;
    private String content;
    private String serviceAddress;
}
