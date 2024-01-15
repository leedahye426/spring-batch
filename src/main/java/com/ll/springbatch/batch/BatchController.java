package com.ll.springbatch.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchController {
    private final BatchService batchService;

    @GetMapping("/hello")
    @ResponseBody
    public String runHelloJob() {
        batchService.runHelloJob();

        return "helloJob OK";
    }
}
