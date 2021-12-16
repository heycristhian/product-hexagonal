package br.com.heycristhian.producthexagonal.application.service.mother;

import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;

public class SearchFilterMother {
    public static SearchFilter getSearchFilter() {
        return SearchFilter.builder()
                .name("Mouse")
                .description("Mouse Razer")
                .build();
    }
}
