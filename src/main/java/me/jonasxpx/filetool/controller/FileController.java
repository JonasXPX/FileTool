package me.jonasxpx.filetool.controller;

import me.jonasxpx.filetool.service.FileResourceManagement;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class FileController {

    private final FileResourceManagement fileResourceManagement;

    public FileController(FileResourceManagement fileResourceManagement) {
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
}
