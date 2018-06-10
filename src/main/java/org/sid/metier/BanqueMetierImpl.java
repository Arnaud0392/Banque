package org.sid.metier;

import java.util.Date;

import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.Operation;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier{

	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Override
	public Compte consulterCompte(String codeCompte) {
		Compte cp = compteRepository.findOne(codeCompte);
		
		if (cp==null)
			throw new RuntimeException("Le compte est introuvable");
		
		return cp;
	}

	@Override
	public void verser(String codeCompte, double montant) {
		Compte cp = consulterCompte(codeCompte);
		Versement vsr = new Versement(new Date(), montant, cp);
		operationRepository.save(vsr);
		cp.setSolde(cp.getSolde()+montant);
		compteRepository.save(cp);
	}

	@Override
	public void retrait(String codeCompte, double montant) {
		Compte cp = consulterCompte(codeCompte);
		double faciliteCaisse = 0;
		
		if (cp instanceof CompteCourant)
			faciliteCaisse = ((CompteCourant) cp).getDecouvert();
		
		if (cp.getSolde()+faciliteCaisse < montant)
			throw new RuntimeException("Solde insuffisant");
		
		Retrait retrait = new Retrait(new Date(), montant, cp);
		operationRepository.save(retrait);
		cp.setSolde(cp.getSolde()-montant);
		compteRepository.save(cp);
	}

	@Override
	public void virement(String codeCpte1, String codeCpte2, double montant) {
		retrait(codeCpte1, montant);
		verser(codeCpte2, montant);
	}

	@Override
	public Page<Operation> listeOperation(String codeCompte, int page, int size) {
		
		return operationRepository.listeOperation(codeCompte, new PageRequest(page, size));
	}

}
