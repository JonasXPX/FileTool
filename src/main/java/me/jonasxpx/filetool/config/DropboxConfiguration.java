package me.jonasxpx.filetool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dropbox")
public class DropboxConfiguration {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
