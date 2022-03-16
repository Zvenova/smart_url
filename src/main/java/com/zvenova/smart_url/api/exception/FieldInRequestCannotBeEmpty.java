package com.zvenova.smart_url.api.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FieldInRequestCannotBeEmpty extends Exception{

    private String error;
}
