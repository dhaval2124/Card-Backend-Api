package com.cardmanagement.service_registry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"eureka.client.register-with-eureka=false",
		"eureka.client.fetch-registry=false"
})
class ServiceRegistryApplicationTests {

	@Test
	void contextLoads() {
	}

}
