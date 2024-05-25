package me.jonasxpx.filetool.service.localresource;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jonasxpx.filetool.exception.FileToolException;
import me.jonasxpx.filetool.service.FileResourceManagement;
import me.jonasxpx.filetool.service.localresource.filemap.File;
import me.jonasxpx.filetool.service.localresource.filemap.FileInfo;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

@Service
public class LocalFileResource implements FileResourceManagement {

    public static final String DEFAULT_FOLDER_LOCATION = System.getProperty("user.home") + "/filetool";
    private static final String RESOURCE_MAP_FILE = "map.json";

    private final ObjectMapper objectMapper;

    public LocalFileResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Resource getFileById(final String id) {
        final String fileLocation = getFileLocation(id);
        if (Objects.isNull(fileLocation)) {
            return null;
        }

        return new PathResource(fileLocation);
    }

    /**
     * Find the PATH of the file in the resource file by ID.
     *
     * @param id The file ID.
     * @return return the path of the file.
     */
    private String getFileLocation(final String id) {
        try {
            createDirectoryAndMapFile();
        } catch (IOException e) {
            throw new FileToolException(e);
        }

        try (FileInputStream file = new FileInputStream(Paths.get(DEFAULT_FOLDER_LOCATION, RESOURCE_MAP_FILE).toFile())) {
            final Map<String, FileInfo> values = objectMapper.readValue(file, File.class);
            if (!values.containsKey(id)) {
                return null;
            }
            return values.get(id).path();
        } catch (IOException e) {
            throw new FileToolException(e);
        }
    }

    private void createDirectoryAndMapFile() throws IOException {
        final Path fileToolDirectoryLocation = Paths.get(DEFAULT_FOLDER_LOCATION);

        if (!Files.exists(fileToolDirectoryLocation)) {
            Files.createDirectory(fileToolDirectoryLocation);
        }

        final Path resourceMapFile = Paths.get(fileToolDirectoryLocation.toString(), RESOURCE_MAP_FILE);
        if (!Files.exists(resourceMapFile)) {
            Files.createFile(resourceMapFile);
        }
    }
}
