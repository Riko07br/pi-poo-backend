package com.monza96.backend.resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class QueryParams implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer page = 0;
    private Integer size = 10;

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
