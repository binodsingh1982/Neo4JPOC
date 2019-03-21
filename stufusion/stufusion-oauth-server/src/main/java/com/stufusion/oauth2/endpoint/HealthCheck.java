package com.stufusion.oauth2.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthCheck {

    @RequestMapping(method = RequestMethod.GET)
    public String healthy() {
        return "ok";
    }

}
