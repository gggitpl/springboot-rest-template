package com.gggitpl.template.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

/**
 * @author gggitpl
 */
public final class RestResp {

    private RestResp() {

    }

    public static <E> ResponseEntity<E> createOk(E entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    public static <E> ResponseEntity<E> deleteOk() {
        return ResponseEntity.noContent().build();
    }

    public static <E> ResponseEntity<E> updateOk(E entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    public static <E> ResponseEntity<E> getOk(E entity) {
        if (Objects.isNull(entity)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

}
