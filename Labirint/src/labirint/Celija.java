package labirint;

public class Celija {
	
	private int koordinataX;
	private int koordinataY;

	private boolean moguceKretanjeGore;
	private boolean moguceKretanjeDesno;
	private boolean moguceKretanjeDolje;
	private boolean moguceKretanjeLijevo;
	
	public Celija(int x, int y, boolean moguceKretanjeGore, boolean moguceKretanjeDesno, boolean moguceKretanjeDolje,
			boolean moguceKretanjeLijevo) {
		
		this.koordinataX = x;
		this.koordinataY = y;
		
		this.moguceKretanjeGore = moguceKretanjeGore;
		this.moguceKretanjeDesno = moguceKretanjeDesno;
		this.moguceKretanjeDolje = moguceKretanjeDolje;
		this.moguceKretanjeLijevo = moguceKretanjeLijevo;
	}

	public Celija() {
		super();
		this.moguceKretanjeGore = false;
		this.moguceKretanjeDesno = false;
		this.moguceKretanjeDolje = false;
		this.moguceKretanjeLijevo = false;
	}
	
	public Celija(int x, int y) {
		super();
		
		this.koordinataX = x;
		this.koordinataY = y;
		
		this.moguceKretanjeGore = false;
		this.moguceKretanjeDesno = false;
		this.moguceKretanjeDolje = false;
		this.moguceKretanjeLijevo = false;
	}
	
	public void setKoordinataX(int koordinataX) {
		this.koordinataX = koordinataX;
	}

	public void setKoordinataY(int koordinataY) {
		this.koordinataY = koordinataY;
	}

	public void setMoguceKretanjeGore(boolean moguceKretanjeGore) {
		this.moguceKretanjeGore = moguceKretanjeGore;
	}

	public void setMoguceKretanjeDesno(boolean moguceKretanjeDesno) {
		this.moguceKretanjeDesno = moguceKretanjeDesno;
	}

	public void setMoguceKretanjeDolje(boolean moguceKretanjeDolje) {
		this.moguceKretanjeDolje = moguceKretanjeDolje;
	}

	public void setMoguceKretanjeLijevo(boolean moguceKretanjeLijevo) {
		this.moguceKretanjeLijevo = moguceKretanjeLijevo;
	}

	public int getKoordinataX() {
		return koordinataX;
	}

	public int getKoordinataY() {
		return koordinataY;
	}

	public boolean isMoguceKretanjeGore() {
		return moguceKretanjeGore;
	}

	public boolean isMoguceKretanjeDesno() {
		return moguceKretanjeDesno;
	}

	public boolean isMoguceKretanjeDolje() {
		return moguceKretanjeDolje;
	}

	public boolean isMoguceKretanjeLijevo() {
		return moguceKretanjeLijevo;
	}
	
	
	
}
