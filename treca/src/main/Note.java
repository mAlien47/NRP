package main;

public enum Note {

	C(1), D(2), E(3), F(4), G(5), A(6), H(7);
	
	int vrijednost;
	
	private Note (int broj) {
		this.vrijednost = broj;
	}

	public int getVrijednost() {
		return vrijednost;
	}
	
}
