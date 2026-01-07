package com.example.rastreamento.unit;

import com.example.rastreamento.RastreamentoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.remote.JMXServerErrorException;
import javax.naming.NamingException;

@SpringBootTest
class RastreamentoApplicationTest {

	@Test
	void contextLoads() {

	}

	@Test
	void testMain() throws NamingException, JMXServerErrorException {
		RastreamentoApplication.main(new String[] {"--server.port=0"});
	}
}