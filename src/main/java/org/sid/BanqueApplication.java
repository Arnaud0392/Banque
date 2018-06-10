package org.sid;

import java.util.Date;

import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.CompteEpargne;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BanqueApplication implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BanqueApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Client c1 = clientRepository.save(new Client("Hassan", "hassan@gmail.com"));
		Client c2 = clientRepository.save(new Client("Rachid", "rachid@gmail.com"));
		
		Compte cp1 = compteRepository.save(new CompteCourant("CPT1", new Date(), 5000, c1, 1000));
		Compte cp2 = compteRepository.save(new CompteEpargne("CPT2", new Date(), 7000, c2, 5.5));
		
		operationRepository.save(new Versement(new Date(), 1500, cp1));
		operationRepository.save(new Versement(new Date(), 1200, cp1));
		operationRepository.save(new Versement(new Date(), 4300, cp1));
		operationRepository.save(new Retrait(new Date(), 5400, cp1));
		
		operationRepository.save(new Versement(new Date(), 1000, cp2));
		operationRepository.save(new Versement(new Date(), 3000, cp2));
		operationRepository.save(new Versement(new Date(), 1300, cp2));
		operationRepository.save(new Retrait(new Date(), 6300, cp2));
	}
}
