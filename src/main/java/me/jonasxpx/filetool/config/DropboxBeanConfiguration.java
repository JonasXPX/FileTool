package me.jonasxpx.filetool.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxBeanConfiguration {

    private final DropboxConfiguration dropboxConfiguration;

    public DropboxBeanConfiguration(DropboxConfiguration dropboxConfiguration) {
        this.dropboxConfiguration = dropboxConfiguration;
    }

    @Bean
    public DbxClientV2 configureDropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("jonasxpx/filetool")
                .build();
        return new DbxClientV2(config, dropboxConfiguration.getAccessToken());
    }

}
