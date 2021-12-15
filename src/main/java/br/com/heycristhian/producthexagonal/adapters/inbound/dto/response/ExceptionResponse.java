package br.com.heycristhian.producthexagonal.adapters.inbound.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {
    private int code;
    private String status;
    private String message;
}
