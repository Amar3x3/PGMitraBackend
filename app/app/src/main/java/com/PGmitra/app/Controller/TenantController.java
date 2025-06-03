package com.PGmitra.app.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {
    @GetMapping("/hello")
    public String hello(){
        return "hello from tenant api";
    }


}
