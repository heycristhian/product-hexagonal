package br.com.heycristhian.producthexagonal.application.service;

import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.exception.ProductNotFoundException;
import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;
import br.com.heycristhian.producthexagonal.application.ports.repository.ProductRepository;
import br.com.heycristhian.producthexagonal.application.ports.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    @Override
    public Product update(Long id, Product product) {
        findById(id);
        product.setId(id);
        return repository.update(product);
    }

    @Override
    public List<Product> search(SearchFilter filter) {
        return repository.search(filter);
    }
}
