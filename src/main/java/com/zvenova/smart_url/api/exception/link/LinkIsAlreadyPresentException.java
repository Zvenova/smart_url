package com.zvenova.smart_url.api.exception.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LinkIsAlreadyPresentException extends Exception{

    private String error;
}
