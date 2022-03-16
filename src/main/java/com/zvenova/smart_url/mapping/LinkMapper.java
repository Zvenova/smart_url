package com.zvenova.smart_url.mapping;

import com.zvenova.smart_url.api.links.request.CreateLinkRequest;
import com.zvenova.smart_url.domain.entity.Link;
import com.zvenova.smart_url.generator.SmartLinkGenerator;
import org.springframework.stereotype.Service;


@Service
public class LinkMapper {

    private SmartLinkGenerator smartLinkGenerator;


    public Link getLinkFromCreateUserRequest(CreateLinkRequest createLinkRequest) {

        Link linkToCreate = new Link();

        linkToCreate.setFullLink(createLinkRequest.getFullLink());
        linkToCreate.setSmartLink(smartLinkGenerator.generateSmartLink(createLinkRequest.getFullLink()));
        return linkToCreate;
    }
}
