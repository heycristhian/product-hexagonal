package br.com.heycristhian.producthexagonal.mother;

import br.com.heycristhian.producthexagonal.application.filter.SearchFilter;

public class SearchFilterMother {
    public static SearchFilter getSearchFilter() {
        return SearchFilter.builder()
                .name("Keyboard")
                .description("Keyboard Razer")
                .build();
    }
}
