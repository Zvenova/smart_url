package com.zvenova.smart_url.api.links.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLinkRequest {

    private Long userId;
    private String fullLink;
}
