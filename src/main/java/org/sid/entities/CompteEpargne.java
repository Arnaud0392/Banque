package org.sid.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CPTE_EPG")
public class CompteEpargne extends Compte{
	private double taux;

	public CompteEpargne() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompteEpargne(String numeroCompte, Date dateCreation, double solde, Client client, double taux) {
		super(numeroCompte, dateCreation, solde, client);
		// TODO Auto-generated constructor stub
		this.taux = taux;
	}

	public double getTaux() {
		return taux;
	}

	public void setTaux(double taux) {
		this.taux = taux;
	}
	
	
}
