package com.pettoyou.server.scrap.controller;

import com.pettoyou.server.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapController {
    private ScrapService scrapService;
}
