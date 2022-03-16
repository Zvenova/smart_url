package com.zvenova.like_my.api.exception.request;


import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldInRequestCannotBeEmpty extends RuntimeException {

    public FieldInRequestCannotBeEmpty(String error) {
        super(error);
    }
}
