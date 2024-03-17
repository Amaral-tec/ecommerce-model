package br.com.amaral.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import br.com.amaral.service.AsaasResourcesService;
import junit.framework.TestCase;

@Profile("dev")
@SpringBootTest
class AsaasWSTests extends TestCase {

	@Autowired
	private AsaasResourcesService asaasResourcesService;
	
	@Test
	void testGenerateInvoiceKey() throws Exception {
		
		String key = asaasResourcesService.generateInvoiceKey();
		assertNotNull(key, "The invoice key must not be null");
		System.out.println("Asaas key: " + key);
	}
}
