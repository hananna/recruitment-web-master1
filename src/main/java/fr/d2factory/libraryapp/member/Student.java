package fr.d2factory.libraryapp.member;

import dr.d2factory.libraryapp.constante.LibraryConstants;

public class Student extends Member{
	
	private Integer idStudent;
	private String anneeEtude;
	
	public Student() {
		super();
	}

	public Student(Integer idStudent, String anneeEtude) {
		super();
		this.idStudent = idStudent;
		this.anneeEtude = anneeEtude;
	}

	@Override
	public void payBook(int numberOfDays)  {
		float amount = 0.0f;
		if (numberOfDays <= LibraryConstants.DAYS_BEFORE_LATE_STUDENT) {
			if (LibraryConstants.ANNEE_STUDENT.equals(anneeEtude)) {
				for (int i = LibraryConstants.PERIODE_TARIF_REDUIT + 1; i <= numberOfDays; i++) {
					amount += LibraryConstants.TARIF_JOUR_STUDENT;
				}
			} else {
				for (int i = 0; i < numberOfDays; i++) {
					amount += LibraryConstants.TARIF_JOUR_STUDENT;
				}
			}
		} else {
			amount+= LibraryConstants.TARIF_BEFORE_LATE_STUDENT;	
			for (int i = 0; i < (numberOfDays - LibraryConstants.DAYS_BEFORE_LATE_STUDENT); i++) {
				amount += LibraryConstants.TARIF_RETARD_STUDENT;
			}

		}
		
		if (this.getWallet() > amount) {
			this.setWallet(this.getWallet() - amount);
		}


	}

	public String getAnneeEtude() {
		return anneeEtude;
	}

	public void setAnneeEtude(String anneeEtude) {
		this.anneeEtude = anneeEtude;
	}

	public Integer getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Integer idStudent) {
		this.idStudent = idStudent;
	}


}
