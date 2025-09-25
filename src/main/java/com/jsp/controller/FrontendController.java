package com.jsp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for handling frontend routes.
 * <p>
 * This controller acts as a catch-all for client-side React routes,
 * forwarding unmatched requests (excluding static file requests) to {@code index.html}.
 * This ensures that React Router can properly handle routing on the frontend.
 * </p>
 */
@Slf4j
@Controller
public class FrontendController {

    /**
     * Redirects all non-static requests to the React {@code index.html}.
     * <p>
     * This method captures all paths that do not contain a dot (e.g., `/home`, `/about`),
     * preventing conflicts with static resources (e.g., `.css`, `.js`, `.png`).
     * </p>
     *
     * @param path The requested path segment (excluding file extensions).
     * @return A forward directive to {@code /index.html}, allowing React Router to handle the route.
     */
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect(@PathVariable("path") String path) {
        log.info("Requested path: {}", path);
        return "forward:/index.html";
    }
}
