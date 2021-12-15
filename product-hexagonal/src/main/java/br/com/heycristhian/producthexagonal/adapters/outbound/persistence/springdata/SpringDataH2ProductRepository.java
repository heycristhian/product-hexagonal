package br.com.heycristhian.producthexagonal.adapters.outbound.persistence.springdata;

import br.com.heycristhian.producthexagonal.application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataH2ProductRepository extends JpaRepository<Product, Long> {
}
