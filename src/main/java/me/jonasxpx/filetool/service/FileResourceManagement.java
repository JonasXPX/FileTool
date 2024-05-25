package me.jonasxpx.filetool.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileResourceManagement {

    /**
     * Get file by id.
     *
     * @param id file ID generated during upload
     * @return the resource with file contents
     */
    Resource getFileById(String id);

    void upload(MultipartFile multipartFile);
}
