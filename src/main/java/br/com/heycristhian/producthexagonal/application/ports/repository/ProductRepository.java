package br.com.heycristhian.producthexagonal.application.ports.repository;

import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(Long id);

    void deleteById(Long id);

    Product update(Product product);

    List<Product> search(SearchFilter filter);
}
