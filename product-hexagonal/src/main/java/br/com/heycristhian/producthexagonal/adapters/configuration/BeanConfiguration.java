package br.com.heycristhian.producthexagonal.adapters.configuration;

import br.com.heycristhian.producthexagonal.ProductHexagonalApplication;
import br.com.heycristhian.producthexagonal.application.ports.repository.ProductRepository;
import br.com.heycristhian.producthexagonal.application.ports.service.ProductService;
import br.com.heycristhian.producthexagonal.application.service.ProductServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = ProductHexagonalApplication.class)
public class BeanConfiguration {

    @Bean
    public ProductService productService(ProductRepository repository) {
        return new ProductServiceImpl(repository);
    }
}
