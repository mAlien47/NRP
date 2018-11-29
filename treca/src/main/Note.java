package main;

public enum Note {

	C(10), D(20), E(30), F(40), G(50), A(60), H(70);
	
	int vrijednost;
	
	private Note (int broj) {
		this.vrijednost = broj;
	}

	public int getVrijednost() {
		return vrijednost;
	}
	
}
