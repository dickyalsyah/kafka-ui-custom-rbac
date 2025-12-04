package com.provectus.kafka.ui.config.auth;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("auth.basic")
@Data
public class BasicAuthProperties {

  private List<BasicAuthUser> users = new ArrayList<>();

  @Data
  public static class BasicAuthUser {
    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
  }
}
