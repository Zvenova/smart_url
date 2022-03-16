package com.zvenova.smart_url.api.exception.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserIsAlreadyPresentException extends Exception {

    private String username;
}
