package br.com.heycristhian.producthexagonal.application.ports.repository;

import br.com.heycristhian.producthexagonal.application.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(Long id);

    void deleteById(Long id);
}
