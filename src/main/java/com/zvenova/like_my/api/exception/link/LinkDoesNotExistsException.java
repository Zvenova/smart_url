package com.zvenova.like_my.api.exception.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LinkDoesNotExistsException extends Exception {

    private String error;
}
