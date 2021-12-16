package br.com.heycristhian.producthexagonal.adapters.inbound.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductRequest {

    @NotBlank(message = "Name must not be empty or null")
    private String name;

    @NotBlank(message = "Description must not be empty or null")
    private String description;

    @NotNull(message = "Price must not be empty or null")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price;
}
