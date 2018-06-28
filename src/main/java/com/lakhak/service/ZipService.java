package com.lakhak.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lakhak.model.Foo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipService {
    private static final Logger log = LoggerFactory.getLogger(ZipService.class);
    private final ObjectMapper objectMapper;

    public ZipService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T extends OutputStream> T zipFoo(final List<Foo> foos, final T os) {
        try (ZipOutputStream zos = new ZipOutputStream(os)) {
            for (Foo foo : foos) {
                String entryName = "foo-" + foo.getId() + ".json";
                log.debug("Adding entry " + entryName);
                ZipEntry ze = new ZipEntry(entryName);
                zos.putNextEntry(ze);
                zos.write(objectMapper.writeValueAsBytes(foo));
                log.debug("Added entry " + entryName);
            }
        } catch (Exception e) {
            log.error("Failed to write zip", e);
        }
        return os;
    }
}
