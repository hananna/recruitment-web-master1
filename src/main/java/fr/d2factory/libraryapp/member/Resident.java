package fr.d2factory.libraryapp.member;

import dr.d2factory.libraryapp.constante.LibraryConstants;
import fr.d2factory.libraryapp.libraryException.HasLateBooksException;

public class Resident extends Member{
	
	public Resident() {
		super();
	}

	@Override
	public void payBook(int numberOfDays) throws HasLateBooksException {
		float amount = 0.0f;
		if (numberOfDays <= LibraryConstants.DAYS_BEFORE_LATE_RESIDENT) {
			for (int i = 0; i < numberOfDays; i++) {
					amount += LibraryConstants.TARIF_JOUR_RESIDENT;
			}
		} else {
			amount+= LibraryConstants.TARIF_BEFORE_LATE_RESIDENT;	
			for (int i = 0; i < (numberOfDays - LibraryConstants.DAYS_BEFORE_LATE_RESIDENT); i++) {
				amount += LibraryConstants.TARIF_RETARD_RESIDENT;
			}

		}
		
		if (this.getWallet() > amount) {
			this.setWallet(this.getWallet() - amount);
		}


	}

}
