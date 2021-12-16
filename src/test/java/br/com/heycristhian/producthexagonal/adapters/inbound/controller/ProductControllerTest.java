package br.com.heycristhian.producthexagonal.adapters.inbound.controller;

import br.com.heycristhian.producthexagonal.adapters.inbound.dto.request.ProductRequest;
import br.com.heycristhian.producthexagonal.adapters.mapper.ProductMapper;
import br.com.heycristhian.producthexagonal.application.entity.Product;
import br.com.heycristhian.producthexagonal.application.exception.ProductNotFoundException;
import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;
import br.com.heycristhian.producthexagonal.application.ports.service.ProductService;
import br.com.heycristhian.producthexagonal.mother.ProductMother;
import br.com.heycristhian.producthexagonal.mother.SearchFilterMother;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @MockBean
    private ProductService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private final String urlProduct = "/products";

    @Test
    void mustReturnHttpStatusCode200Ok_whenCallFindAll() throws Exception {
        //given:
        List<Product> products = ProductMother.getProducts();
        when(service.findAll()).thenReturn(products);

        //when:
        mockMvc.perform(get(urlProduct)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(products.get(0).getId()))
                .andExpect(jsonPath("[0].name").value(products.get(0).getName()))
                .andExpect(jsonPath("[0].description").value(products.get(0).getDescription()))
                .andExpect(jsonPath("[0].price").value(products.get(0).getPrice()));

        //then:
        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode500InternalServerError_whenCallFindAll() throws Exception {
        //given:
        when(service.findAll()).thenThrow(RuntimeException.class);

        //when:
        mockMvc.perform(get(urlProduct)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        //then:
        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode200Ok_WhenCallFindById() throws Exception {
        //given:
        var productResponse = ProductMother.getProductResponse();
        var product = ProductMother.getProduct();
        when(service.findById(anyLong())).thenReturn(product);

        //when:
        mockMvc.perform(get(urlProduct + "/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productResponse.getId()))
                .andExpect(jsonPath("$.name").value(productResponse.getName()))
                .andExpect(jsonPath("$.description").value(productResponse.getDescription()))
                .andExpect(jsonPath("$.price").value(productResponse.getPrice()));

        //then:
        verify(service).findById(product.getId());
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode404NotFound_WhenCallFindById() throws Exception {
        //given:
        var id = 1L;
        when(service.findById(anyLong())).thenThrow(ProductNotFoundException.class);

        //when:
        mockMvc.perform(get(urlProduct + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //then:
        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode500InternalServerError_WhenCallFindById() throws Exception {
        //given:
        var id = 1L;
        when(service.findById(anyLong())).thenThrow(RuntimeException.class);

        //when:
        mockMvc.perform(get(urlProduct + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        //then:
        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode201Created_WhenCallSave() throws Exception {
        //given:
        var request = ProductMother.getProductRequest();
        var product = ProductMother.getProduct();
        var response = ProductMother.getProductResponse();
        when(service.save(any(Product.class))).thenReturn(product);

        //when:
        mockMvc.perform(post(urlProduct)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("Location", "http://localhost/products/" + response.getId()))
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.description").value(response.getDescription()))
                .andExpect(jsonPath("$.price").value(response.getPrice()));

        //then:
        verify(service).save(any(Product.class)); //TODO RESOLVER PARA USAR OBJECT
        verifyNoMoreInteractions(service);
    }

    @ParameterizedTest
    @MethodSource("br.com.heycristhian.producthexagonal.mother.ProductMother#getAllProductRequestInvalid")
    void mustReturnHttpStatusCode400BadRequest_WhenCallSave(ProductRequest request) throws Exception {
        //given:

        //when:
        mockMvc.perform(post(urlProduct)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        //then:
        verifyNoInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode500InternalServerError_WhenCallSave() throws Exception {
        //given:
        var request = ProductMother.getProductRequest();
        var product = ProductMapper.INSTANCE.toProduct(request);
        when(service.save(any(Product.class))).thenThrow(RuntimeException.class);

        //when:
        mockMvc.perform(post(urlProduct)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

        //then:
        verify(service).save(product);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mostReturnHttpStatusCode204NoContent_WhenCallDeleteById() throws Exception {
        //given:
        var id = 1L;
        doNothing().when(service).deleteById(anyLong());

        //when:
        mockMvc.perform(delete(urlProduct + "/" + id))
                .andExpect(status().isNoContent());

        //then:
        verify(service).deleteById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mostReturnHttpStatusCode404NotFound_WhenCallDeleteById() throws Exception {
        //given:
        var id = 1L;
        doThrow(ProductNotFoundException.class).when(service).deleteById(anyLong());

        //when:
        mockMvc.perform(delete(urlProduct + "/" + id))
                .andExpect(status().isNotFound());

        //then:
        verify(service).deleteById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mostReturnHttpStatusCode500InternalServerError_WhenCallDeleteById() throws Exception {
        //given:
        var id = 1L;
        doThrow(RuntimeException.class).when(service).deleteById(anyLong());

        //when:
        mockMvc.perform(delete(urlProduct + "/" + id))
                .andExpect(status().isInternalServerError());

        //then:
        verify(service).deleteById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode200NoContent_WhenCallUpdate() throws Exception {
        //given:
        var request = ProductMother.getProductRequest();
        var product = ProductMother.getProduct();
        var response = ProductMother.getProductResponse();
        var id = product.getId();
        when(service.update(anyLong(), any(Product.class))).thenReturn(product);

        //when:
        mockMvc.perform(put(urlProduct + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.description").value(response.getDescription()))
                .andExpect(jsonPath("$.price").value(response.getPrice()));

        //then:
        verify(service).update(anyLong(), any(Product.class)); //TODO RESOLVER PARA USAR OBJECT
        verifyNoMoreInteractions(service);
    }

    @Test
    void mostReturnHttpStatusCode404NotFound_WhenCallUpdate() throws Exception {
        //given:
        var request = ProductMother.getProductRequest();
        var product = ProductMapper.INSTANCE.toProduct(request);
        var id = 1L;
        when(service.update(anyLong(), any(Product.class))).thenThrow(ProductNotFoundException.class);

        //when:
        mockMvc.perform(put(urlProduct + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        //then:
        verify(service).update(id, product);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mostReturnHttpStatusCode500InternalServerERror_WhenCallUpdate() throws Exception {
        //given:
        var request = ProductMother.getProductRequest();
        var product = ProductMapper.INSTANCE.toProduct(request);
        var id = 1L;
        when(service.update(anyLong(), any(Product.class))).thenThrow(RuntimeException.class);

        //when:
        mockMvc.perform(put(urlProduct + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

        //then:
        verify(service).update(id, product);
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode200Ok_WhenCallSearch() throws Exception {
        //given:
        List<Product> products = ProductMother.getProducts();
        var responses = ProductMapper.INSTANCE.toProductResponse(products);
        var filter = SearchFilterMother.getSearchFilter();
        when(service.search(any(SearchFilter.class))).thenReturn(products);

        //when:
        mockMvc.perform(get(urlProduct + "/search")
                .param("name", filter.getName())
                .param("description", filter.getDescription())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(responses.get(0).getId()))
                .andExpect(jsonPath("[0].name").value(responses.get(0).getName()))
                .andExpect(jsonPath("[0].description").value(responses.get(0).getDescription()))
                .andExpect(jsonPath("[0].price").value(responses.get(0).getPrice()));

        //then:
        verify(service).search(any(SearchFilter.class)); //TODO RESOLVER PARA USAR OBJECT
        verifyNoMoreInteractions(service);
    }

    @Test
    void mustReturnHttpStatusCode500InternalServerError_WhenCallSearch() throws Exception {
        //given:
        var filter = SearchFilterMother.getSearchFilter();
        when(service.search(any(SearchFilter.class))).thenThrow(RuntimeException.class);

        //when:
        mockMvc.perform(get(urlProduct + "/search")
                .param("name", filter.getName())
                .param("description", filter.getDescription())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        //then:
        verify(service).search(any(SearchFilter.class)); //TODO RESOLVER PARA USAR OBJECT
        verifyNoMoreInteractions(service);
    }

}