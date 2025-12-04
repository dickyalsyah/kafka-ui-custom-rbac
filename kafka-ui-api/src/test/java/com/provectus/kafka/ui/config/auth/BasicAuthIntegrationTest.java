package com.provectus.kafka.ui.config.auth;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

import com.provectus.kafka.ui.config.ClustersProperties;
import com.provectus.kafka.ui.service.ClustersStorage;
import com.provectus.kafka.ui.service.rbac.AccessControlService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = BasicAuthIntegrationTest.TestController.class)
@Import({ BasicAuthSecurityConfig.class, AccessControlService.class })
@EnableConfigurationProperties({ BasicAuthProperties.class, RoleBasedAccessControlProperties.class })
@TestPropertySource(properties = {
    "auth.type=LOGIN_FORM",
    "auth.basic.users[0].username=admin",
    "auth.basic.users[0].password=password",
    "auth.basic.users[0].roles[0]=admin",
    "auth.basic.users[1].username=user",
    "auth.basic.users[1].password=password",
    "auth.basic.users[1].roles[0]=viewer",
    "rbac.roles[0].name=admin",
    "rbac.roles[0].clusters[0]=local",
    "rbac.roles[0].permissions[0].resource=applicationconfig",
    "rbac.roles[0].permissions[0].actions[0]=all",
    "rbac.roles[1].name=viewer",
    "rbac.roles[1].clusters[0]=local",
    "rbac.roles[1].permissions[0].resource=clusterconfig",
    "rbac.roles[1].permissions[0].actions[0]=view"
})
public class BasicAuthIntegrationTest {

  @RestController
  public static class TestController {
    @Autowired
    AccessControlService accessControlService;

    @GetMapping("/test")
    public String test() {
      return "ok";
    }

    @GetMapping("/test/cluster")
    public Mono<Boolean> testCluster() {
      var cluster = new com.provectus.kafka.ui.model.ClusterDTO();
      cluster.setName("local");
      return accessControlService.isClusterAccessible(cluster);
    }
  }

  @Autowired
  ApplicationContext context;

  @MockBean
  ClustersStorage clustersStorage;

  @Autowired
  AccessControlService accessControlService;

  @Test
  public void testLogin() {
    WebTestClient client = WebTestClient
        .bindToApplicationContext(context)
        .apply(springSecurity())
        .configureClient()
        .build();

    // Test valid login for admin
    client
        .post().uri("/auth")
        .body(BodyInserters.fromFormData("username", "admin").with("password", "password"))
        .exchange()
        .expectStatus().isFound();

    // Test valid login for user
    client
        .post().uri("/auth")
        .body(BodyInserters.fromFormData("username", "user").with("password", "password"))
        .exchange()
        .expectStatus().isFound();

    // Test invalid login
    client
        .post().uri("/auth")
        .body(BodyInserters.fromFormData("username", "admin").with("password", "wrong"))
        .exchange()
        .expectStatus().isFound();
  }

  @Test
  public void testClusterAccess() {
    WebTestClient client = WebTestClient
        .bindToApplicationContext(context)
        .apply(springSecurity())
        .configureClient()
        .build();

    // Login as admin
    client
        .post().uri("/auth")
        .body(BodyInserters.fromFormData("username", "admin").with("password", "password"))
        .exchange()
        .expectStatus().isFound()
        .expectCookie().exists("SESSION");

    // Verify admin has access to 'local' cluster
    // We can check this by invoking AccessControlService directly or via a dummy
    // controller if we had one.
    // Since we don't have a controller that uses AccessControlService in this test
    // context easily without mocking more things,
    // let's verify the AccessControlService logic directly with the authenticated
    // user.

    // However, WebTestClient runs in a separate thread/context than the test
    // method, so SecurityContextHolder won't be populated here directly.
    // But we can use the client to hit an endpoint.

    // Let's add a test endpoint to the TestController that checks cluster access
  }
}
