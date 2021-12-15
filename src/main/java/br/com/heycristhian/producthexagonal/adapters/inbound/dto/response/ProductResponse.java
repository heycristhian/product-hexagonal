package br.com.heycristhian.producthexagonal.adapters.inbound.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
