package br.com.heycristhian.producthexagonal.adapters.inbound.controller;

import br.com.heycristhian.producthexagonal.adapters.inbound.dto.request.ProductRequest;
import br.com.heycristhian.producthexagonal.adapters.inbound.dto.response.ProductResponse;
import br.com.heycristhian.producthexagonal.adapters.mapper.ProductMapper;
import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;
import br.com.heycristhian.producthexagonal.application.ports.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        log.info("Starting search for all products");

        List<Product> products = service.findAll();
        List<ProductResponse> responses = ProductMapper.INSTANCE.toProductResponse(products);

        log.info("Products found: {}", responses.size());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        log.info("Starting a product search by id: {}", id);

        Product product = service.findById(id);
        var response = ProductMapper.INSTANCE.toProductResponse(product);

        log.info("Product found: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest request, UriComponentsBuilder uriBuilder) {
        log.info("Starting product insertion in the database: {}", request.toString());

        Product product = ProductMapper.INSTANCE.toProduct(request);
        product = service.save(product);
        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        var response = ProductMapper.INSTANCE.toProductResponse(product);

        log.info("Insertion of the product performed successfully: {}", response.toString());
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("Starting to delete product in database with id: {}", id);

        service.deleteById(id);

        log.info("Successful deletion with id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        log.info("Starting to update product in database");
        Product product = ProductMapper.INSTANCE.toProduct(request);
        product = service.update(id, product);
        ProductResponse response = ProductMapper.INSTANCE.toProductResponse(product);

        log.info("Update successfully reformed");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> search(@Valid SearchFilter searchFilter) {
        log.info("Starting a products search");
        List<Product> products = service.search(searchFilter);
        List<ProductResponse> responses = ProductMapper.INSTANCE.toProductResponse(products);

        log.info("Products found: {}", responses);
        return ResponseEntity.ok(responses);
    }
}
