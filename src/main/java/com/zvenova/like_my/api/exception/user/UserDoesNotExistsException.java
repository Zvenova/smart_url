package com.zvenova.like_my.api.exception.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDoesNotExistsException extends Exception {

    private Long id;
}
