package br.com.heycristhian.producthexagonal.application.ports.service;

import br.com.heycristhian.producthexagonal.application.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product save(Product product);

    Product findById(Long id);

    void deleteById(Long id);
}
