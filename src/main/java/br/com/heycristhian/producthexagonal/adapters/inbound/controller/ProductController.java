package br.com.heycristhian.producthexagonal.adapters.inbound.controller;

import br.com.heycristhian.producthexagonal.adapters.inbound.dto.request.ProductRequest;
import br.com.heycristhian.producthexagonal.adapters.inbound.dto.response.ProductResponse;
import br.com.heycristhian.producthexagonal.adapters.mapper.ProductMapper;
import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.ports.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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

        service.findById(id);
        service.deleteById(id);

        log.info("Successful deletion with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
