package br.com.heycristhian.producthexagonal.application.filter;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class SearchFilter {
    private String name;
    private String description;
}
