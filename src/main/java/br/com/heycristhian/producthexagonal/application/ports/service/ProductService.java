package br.com.heycristhian.producthexagonal.application.ports.service;

import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product save(Product product);

    Product findById(Long id);

    void deleteById(Long id);

    Product update(Long id, Product product);

    List<Product> search(SearchFilter filter);
}
