package com.zvenova.smart_url.generator;

import com.zvenova.smart_url.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;


@Service
public class SmartLinkGenerator {

    @Autowired
    private LinkService linkService;

    Random random = new Random();

    public String generateSmartLink(String fullLink) {

        StringBuilder result = new StringBuilder();

        boolean isPresent = true;
        while (isPresent) {

            String fullLinkHash = Integer.toString(fullLink.hashCode());

            String[] fullLinkBytes = UUID.nameUUIDFromBytes(
                            fullLinkHash.getBytes(StandardCharsets.UTF_8))
                    .toString().split("-");

            for (String string:fullLinkBytes) {

                int index = random.nextInt(string.length());
                result.append(string.charAt(index));
            }

            isPresent = linkService.isLinkPresentBySmartLink(result.toString());
        }

        return result.toString();
    }
}
