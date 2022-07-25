package com.example.graphql.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @MutationMapping(name = "fileUpload")
    public FileUploadResult uploadFile(@Argument FilePart file) {

        logger.info("Upload file: name={}", file.filename());

        return new FileUploadResult(UUID.randomUUID());
    }

}

class FileUploadResult {
    UUID id;

    public FileUploadResult(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}