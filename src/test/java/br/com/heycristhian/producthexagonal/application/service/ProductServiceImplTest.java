package br.com.heycristhian.producthexagonal.application.service;

import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.exception.ProductNotFoundException;
import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;
import br.com.heycristhian.producthexagonal.application.ports.repository.ProductRepository;
import br.com.heycristhian.producthexagonal.application.ports.service.ProductService;
import br.com.heycristhian.producthexagonal.application.service.mother.ProductMother;
import br.com.heycristhian.producthexagonal.application.service.mother.SearchFilterMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {


    private ProductService fixture;

    @Mock
    private ProductRepository repository;

    private final Product productExpected = ProductMother.getProduct();
    private final List<Product> productsExpected = ProductMother.getProducts();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        fixture = new ProductServiceImpl(repository);
    }

    @Test
    void mustReturnAllProducts_WhenCallFindAll() {
        //given:
        when(repository.findAll()).thenReturn(productsExpected);

        //when:
        List<Product> productActual = fixture.findAll();

        //then:
        assertTrue(productActual.size() > 0);
        assertEquals(productsExpected, productActual);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustThrowException_WhenCallFindAll() {
        //given:
        when(repository.findAll()).thenThrow(RuntimeException.class);

        //when:
        assertThrows(RuntimeException.class, () -> fixture.findAll());

        //then:
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustReturnProduct_WhenCallSave() {
        //given:
        when(repository.save(any(Product.class))).thenReturn(productExpected);

        //when:
        Product productActual = fixture.save(productExpected);

        //then:
        assertEquals(productExpected, productActual);
        verify(repository).save(productExpected);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustThrowException_WhenCallSave() {
        //given:
        when(repository.save(any(Product.class))).thenThrow(RuntimeException.class);

        //when:
        assertThrows(RuntimeException.class, () -> fixture.save(productExpected));

        //then:
        verify(repository).save(productExpected);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustReturnProduct_WhenCallFindById() {
        //given:
        when(repository.findById(anyLong())).thenReturn(Optional.of(productExpected));

        //when:
        Product productActual = fixture.findById(productExpected.getId());

        //then:
        assertEquals(productExpected, productActual);
        verify(repository).findById(productExpected.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustThrowProductNotFoundException_WhenCallFindById() {
        //given:
        when(repository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        //when:
        assertThrows(ProductNotFoundException.class, () -> fixture.findById(productExpected.getId()));

        //then:
        verify(repository).findById(productExpected.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustDeleteProduct_WhenCallDeleteById() {
        //given:
        when(repository.findById(anyLong())).thenReturn(Optional.of(productExpected));
        doNothing().when(repository).deleteById(anyLong());

        //when:
        fixture.deleteById(productExpected.getId());

        //then:
        verify(repository).findById(productExpected.getId());
        verify(repository).deleteById(productExpected.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustThrowProductNotFoundException_WhenCallDeleteById() {
        //given:
        when(repository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        //when:
        assertThrows(ProductNotFoundException.class, () -> fixture.deleteById(productExpected.getId()));

        //then:
        verify(repository).findById(productExpected.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustReturnProductUpdated_WhenCallUpdate() {
        //given:
        when(repository.update(any(Product.class))).thenReturn(productExpected);
        when(repository.findById(anyLong())).thenReturn(Optional.of(productExpected));

        //when:
        Product productActual = fixture.update(productExpected.getId(), productExpected);

        //then:
        assertEquals(productExpected, productActual);
        verify(repository).update(productExpected);
        verify(repository).findById(productExpected.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustThrowProductNotFoundException_WhenCallUpdate() {
        //given:
        when(repository.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        //when:
        assertThrows(ProductNotFoundException.class, () -> fixture.update(productExpected.getId(), productExpected));

        //then:
        verify(repository).findById(productExpected.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustReturnProducts_WhenCallSearch() {
        //given:
        SearchFilter filter = SearchFilterMother.getSearchFilter();
        when(repository.search(any(SearchFilter.class))).thenReturn(productsExpected);

        //when:
        List<Product> productsActual = fixture.search(filter);

        //then:
        assertEquals(productsExpected, productsActual);
        verify(repository).search(filter);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustThrowException_WhenCallSearch() {
        //given:
        SearchFilter filter = SearchFilterMother.getSearchFilter();
        when(repository.search(any(SearchFilter.class))).thenThrow(RuntimeException.class);

        //when:
        assertThrows(RuntimeException.class, () -> fixture.search(filter));

        //then:
        verify(repository).search(filter);
        verifyNoMoreInteractions(repository);
    }
}