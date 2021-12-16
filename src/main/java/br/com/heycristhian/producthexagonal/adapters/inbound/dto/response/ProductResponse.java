package br.com.heycristhian.producthexagonal.adapters.inbound.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
