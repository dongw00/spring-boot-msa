package se.magnus.microservices.composite.product.services;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import se.magnus.api.composite.product.*;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.review.Review;
import se.magnus.util.exceptions.NotFoundException;
import se.magnus.util.http.ServiceUtil;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class productCompositeServiceImpl implements ProductCompositeService {

    private final ServiceUtil serviceUtil;
    private final ProductCompositeIntegration integration;

    @Override
    public ProductAggregate getProduct(int productId) {
        Product product = integration.getProduct(productId);
        if (product == null) {
            throw new NotFoundException("No product found for productId: " + productId);
        }

        List<Recommendation> recommendations = integration.getRecommendations(productId);
        List<Review> reviews = integration.getReviews(productId);
        return createProductAggregate(product, recommendations, reviews, serviceUtil.getServiceAddress());
    }

    private ProductAggregate createProductAggregate(Product product, List<Recommendation> recommendations, List<Review> reviews, String serviceAddress) {

        // 1. copy summary recommendation info
        List<RecommendationSummary> recommendationSummaries = (recommendations == null) ? null :
                recommendations.stream()
                        .map(r -> RecommendationSummary.builder()
                                .recommendationId(r.getRecommendationId())
                                .author(r.getAuthor())
                                .rate(r.getRate())
                                .build())
                        .collect(Collectors.toList());

        // 2. copy summary review info
        List<ReviewSummary> reviewSummaries = (reviews == null) ? null :
                reviews.stream()
                        .map(r -> ReviewSummary.builder()
                                .reviewId(r.getReviewId())
                                .author(r.getAuthor())
                                .subject(r.getSubject()).build())
                        .collect(Collectors.toList());

        // 3. create info regarding the involved msa address
        String productAddress = product.getServiceAddress();
        String reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";
        String recommendationAddress = (recommendations != null && recommendations.size() > 0) ? recommendations.get(0).getServiceAddress() : "";
        ServiceAddresses serviceAddresses = ServiceAddresses.builder()
                .compositeAddress(serviceAddress)
                .productAddress(productAddress)
                .reviewAddress(reviewAddress)
                .recommendationAddress(recommendationAddress)
                .build();

        return ProductAggregate.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .weight(product.getWeight())
                .recommendations(recommendationSummaries)
                .reviews(reviewSummaries)
                .serviceAddresses(serviceAddresses)
                .build();
    }
}
