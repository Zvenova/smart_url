package com.zvenova.like_my.exception.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LinkIsAlreadyPresentException extends Exception{

    private String fullLink;
}
