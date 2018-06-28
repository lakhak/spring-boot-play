package com.lakhak.controller;


import com.lakhak.model.Foo;
import com.lakhak.service.FooService;
import com.lakhak.service.ZipService;
import io.vavr.control.Try;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping(path = "/foo")
public class ZipController {

    private final FooService fooService;
    private final ZipService zipService;

    public ZipController(FooService fooService, ZipService zipService) {
        this.fooService = fooService;
        this.zipService = zipService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Foo>> allFoo() {
        return ResponseEntity.ok(fooService.allFoos());
    }

    @GetMapping(path = "/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity exportFile() {
        List<Foo> foos = fooService.allFoos();

        return Try.withResources(ByteArrayOutputStream::new)
                .of(baos -> zipService.zipFoo(foos, baos))
                .map(baos -> new ByteArrayResource(baos.toByteArray()))
                .map(this::successResponse)
                .getOrElseGet(this::internalServerError);
    }

    private ResponseEntity successResponse(final Resource resource) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=foos.zip")
                .body(resource);
    }

    private ResponseEntity internalServerError(Throwable throwable) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setMessage(throwable.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
