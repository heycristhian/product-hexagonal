package br.com.heycristhian.producthexagonal.application.service.mother;

import br.com.heycristhian.producthexagonal.application.entity.Product;

import java.math.BigDecimal;
import java.util.List;

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
}
