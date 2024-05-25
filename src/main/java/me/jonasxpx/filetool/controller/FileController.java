package me.jonasxpx.filetool.controller;

import me.jonasxpx.filetool.service.FileResourceManagement;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
public class FileController {

    private final FileResourceManagement fileResourceManagement;

    public FileController(@Qualifier("dropBoxFileResource") FileResourceManagement fileResourceManagement) {
        this.fileResourceManagement = fileResourceManagement;
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable final String fileId) {
        final Resource resource = fileResourceManagement.getFileById(fileId);
        if (Objects.isNull(resource)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
        fileResourceManagement.upload(file);
        return ResponseEntity.ok().build();
    }
}
