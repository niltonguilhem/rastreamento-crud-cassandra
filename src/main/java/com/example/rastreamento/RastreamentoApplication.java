package com.example.rastreamento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.management.remote.JMXServerErrorException;
import javax.naming.NamingException;

@SpringBootApplication
public class RastreamentoApplication {

	private static Logger logger = LoggerFactory.getLogger(RastreamentoApplication.class);

	public static void main(String[] args)throws NamingException, JMXServerErrorException {
		logger.info("Iniciando a api controle de vagas");
		SpringApplication.run(RastreamentoApplication.class, args);
		logger.info("API de Rastreamento de Clientes iniciada e pronta para receber requisições");
	}

}
