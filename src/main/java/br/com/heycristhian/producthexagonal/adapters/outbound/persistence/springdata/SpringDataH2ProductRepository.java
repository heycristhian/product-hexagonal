package br.com.heycristhian.producthexagonal.adapters.outbound.persistence.springdata;

import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataH2ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name=:#{#filter.name} OR p.description=:#{#filter.description}")
    List<Product> search(@Param(value = "filter") SearchFilter filter);
}
