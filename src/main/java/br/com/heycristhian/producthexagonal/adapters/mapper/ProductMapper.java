package br.com.heycristhian.producthexagonal.adapters.mapper;

import br.com.heycristhian.producthexagonal.adapters.inbound.dto.request.ProductRequest;
import br.com.heycristhian.producthexagonal.adapters.inbound.dto.response.ProductResponse;
import br.com.heycristhian.producthexagonal.application.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponse(List<Product> product);

    Product toProduct(ProductRequest request);
}
