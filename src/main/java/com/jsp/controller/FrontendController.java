package com.jsp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class FrontendController {

    // Catch-all mapping for React routes (except static files)
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect(@PathVariable("path") String path) {
        // You can now use the 'path' variable if needed
        log.info("Requested path: {}", path);
        return "forward:/index.html";
    }
}
