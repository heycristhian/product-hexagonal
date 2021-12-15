package br.com.heycristhian.producthexagonal.adapters.mapper;

import br.com.heycristhian.producthexagonal.adapters.inbound.dto.request.ProductRequest;
import br.com.heycristhian.producthexagonal.adapters.inbound.dto.response.ProductResponse;
import br.com.heycristhian.producthexagonal.adapters.inbound.dto.response.ProductResponse.ProductResponseBuilder;
import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.entity.Product.ProductBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-12-15T16:07:49-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.description( product.getDescription() );
        productResponse.price( product.getPrice() );

        return productResponse.build();
    }

    @Override
    public List<ProductResponse> toProductResponse(List<Product> product) {
        if ( product == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( product.size() );
        for ( Product product1 : product ) {
            list.add( toProductResponse( product1 ) );
        }

        return list;
    }

    @Override
    public Product toProduct(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.description( request.getDescription() );
        product.price( request.getPrice() );

        return product.build();
    }
}
