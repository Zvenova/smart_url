package com.zvenova.smart_url.api.links;


import com.zvenova.smart_url.api.links.request.CreateLinkRequest;
import com.zvenova.smart_url.domain.entity.Link;
import com.zvenova.smart_url.mapping.LinkMapper;
import com.zvenova.smart_url.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequiredArgsConstructor
public class LinksApiController {

    private final LinkService linkService;

    private final LinkMapper linkMapper;

    @SneakyThrows
    @GetMapping(path = {"{smartLink}"})
    public RedirectView connect(@PathVariable String smartLink) {

        String redirectUrl = linkService.findBySmartLink(smartLink).getFullLink();
        return new RedirectView(redirectUrl);
    }

    @SneakyThrows
    @PostMapping(path = "/link")
    public Link createSmartLink(CreateLinkRequest createLinkRequest) {

        if (linkService.isLinkPresentByFullLink(createLinkRequest.getFullLink())) {

            return linkService.findByFullLink(createLinkRequest.getFullLink());
        }

        Link linkToSave = linkMapper.getLinkFromCreateUserRequest(createLinkRequest);
        linkService.saveLink(linkToSave);
        return new Link();
    }
}
