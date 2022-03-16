package com.zvenova.like_my.mapping;

import com.zvenova.like_my.api.links.request.CreateLinkRequest;
import com.zvenova.like_my.domain.entity.Link;
import com.zvenova.like_my.generator.SmartLinkGenerator;
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
