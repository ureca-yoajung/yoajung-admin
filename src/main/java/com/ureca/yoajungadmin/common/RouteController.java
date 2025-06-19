package com.ureca.yoajungadmin.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

    @GetMapping("/")
    public String home() {
        return "/plan/planList.html";
    }
}
