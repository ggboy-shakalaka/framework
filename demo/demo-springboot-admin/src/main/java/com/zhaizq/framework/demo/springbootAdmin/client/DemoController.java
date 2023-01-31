package com.zhaizq.framework.demo.springbootAdmin.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;

@Controller
public class DemoController {
    @Value("${logging.file}")
    private String path;

    @RequestMapping("/log/{fileName}")
    public ResponseEntity<Resource> log() {
        return ResponseEntity.ok().contentType(MediaType.valueOf("text/plain; charset=utf-8"))
                .body(new FileSystemResource(path));
    }

    @ResponseBody
    @RequestMapping("file")
    public Object file() {
        return Arrays.stream(new File(path).getParentFile().listFiles()).map(File::getName).filter(v -> v.endsWith(".log")).toArray(String[]::new);
    }
}
