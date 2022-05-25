package se.magnus.api.composite.product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ServiceAddresses {
    private final String cmp;
    private final String pro;
    private final String rev;
    private final String rec;

    public ServiceAddresses() {
        cmp = null;
        pro = null;
        rev = null;
        rec = null;
    }

    @Builder
    public ServiceAddresses(String compositeAddress, String productAddress, String reviewAddress, String recommendationAddress) {
        this.cmp = compositeAddress;
        this.pro = productAddress;
        this.rev = reviewAddress;
        this.rec = recommendationAddress;
    }
}
