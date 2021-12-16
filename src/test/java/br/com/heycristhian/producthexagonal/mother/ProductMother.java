package br.com.heycristhian.producthexagonal.mother;

import br.com.heycristhian.producthexagonal.adapters.inbound.dto.request.ProductRequest;
import br.com.heycristhian.producthexagonal.adapters.inbound.dto.response.ProductResponse;
import br.com.heycristhian.producthexagonal.adapters.mapper.ProductMapper;
import br.com.heycristhian.producthexagonal.application.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

public class ProductMother {
    public static List<Product> getProducts() {
        var product = Product.builder()
                .id(1L)
                .name("Mouse")
                .description("Mouse Razer")
                .price(BigDecimal.valueOf(200.99))
                .build();

        return List.of(product);
    }

    public static Product getProduct() {
        return Product.builder()
                .id(1L)
                .name("Keyboard")
                .description("Keyboard Razer")
                .price(BigDecimal.valueOf(600.80))
                .build();
    }

    public static ProductResponse getProductResponse() {
        return ProductMapper.INSTANCE.toProductResponse(getProduct());
    }

    public static ProductRequest getProductRequest() {
        return ProductMapper.INSTANCE.toProductRequest(getProduct());
    }

    public static Stream<ProductRequest> getAllProductRequestInvalid() {
        return Stream.of(
                getProductRequestNameIsNull(),
                getProductRequestNameIsEmpty(),
                getProductRequestDescriptionIsNull(),
                getProductRequestDescriptionIsEmpty(),
                getProductRequestPriceIsNull(),
                getProductRequestDescriptionIsNegative()
        );
    }

    private static ProductRequest getProductRequestNameIsNull() {
        var product = getProduct();
        product.setName(null);
        return ProductMapper.INSTANCE.toProductRequest(product);
    }

    private static ProductRequest getProductRequestNameIsEmpty() {
        var product = getProduct();
        product.setName("");
        return ProductMapper.INSTANCE.toProductRequest(product);
    }

    private static ProductRequest getProductRequestDescriptionIsNull() {
        var product = getProduct();
        product.setDescription(null);
        return ProductMapper.INSTANCE.toProductRequest(product);
    }

    private static ProductRequest getProductRequestDescriptionIsEmpty() {
        var product = getProduct();
        product.setDescription("");
        return ProductMapper.INSTANCE.toProductRequest(product);
    }

    private static ProductRequest getProductRequestPriceIsNull() {
        var product = getProduct();
        product.setPrice(null);
        return ProductMapper.INSTANCE.toProductRequest(product);
    }

    private static ProductRequest getProductRequestDescriptionIsNegative() {
        var product = getProduct();
        product.setPrice(BigDecimal.valueOf(-19.00));
        return ProductMapper.INSTANCE.toProductRequest(product);
    }


}
