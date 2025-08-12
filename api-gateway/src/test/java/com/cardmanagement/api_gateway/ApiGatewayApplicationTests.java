package com.cardmanagement.api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = { "spring.cloud.gateway.server.webflux.enabled=false" }
)
@ActiveProfiles("test")
class ApiGatewayApplicationTests {

	@MockitoBean
	private RouteLocator routeLocator;

	@MockitoBean
	private ReactiveDiscoveryClient reactiveDiscoveryClient;

	@Test
	void contextLoads() {
	}
}
