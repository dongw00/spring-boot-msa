package se.magnus.api.composite.product;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProductAggregate {
    private final int productId;
    private final String name;
    private final int weight;
    private final List<RecommendationSummary> recommendations;
    private final List<ReviewSummary> reviews;
    private final ServiceAddresses serviceAddresses;

    @Builder
    public ProductAggregate(int productId, String name, int weight,
                            List<RecommendationSummary> recommendations,
                            List<ReviewSummary> reviews,
                            ServiceAddresses serviceAddresses) {

        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.recommendations = recommendations;
        this.reviews = reviews;
        this.serviceAddresses = serviceAddresses;
    }
}
