package me.jonasxpx.filetool;

import me.jonasxpx.filetool.config.DropboxConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DropboxConfiguration.class)
public class FileToolMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileToolMainApplication.class, args);
    }
}