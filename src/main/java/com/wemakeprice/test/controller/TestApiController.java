package com.wemakeprice.test.controller;


import com.wemakeprice.test.param.CrawlingParam;
import com.wemakeprice.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestApiController {

    final private TestService testService;

    @GetMapping("/api/crawling")
    public String getResultData(CrawlingParam param) throws Exception {
        return testService.getResultData(param);
    }
}
