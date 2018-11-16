package fitness;

import java.io.InvalidClassException;
import java.util.EmptyStackException;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;

import labirint.Celija;
import main.Main;

public class FitnessLabirint extends FitnessFunction {

	public static final int EVO_TI_KEKS = 50;
	public static final int MAX_VRIJEDNOST = 1000;
	public static final int KAZNA_KORACI = 100;
	public static final int NAGRADA_BLIZINA = 100;

	public static String najboljeRjesenje = "RURULURRDRUUR";

	@Override
	protected double evaluate(IChromosome chromos) {

		double rez = 0;

		boolean isPronadenoRjesenje = false;

		String micanja = getStringRezultat(chromos);

		if (micanja.equals(najboljeRjesenje))
			return (double) 10000;

		Celija[][] labirint = Main.getLabirint();

		int xKoord = 0;
		int yKoord = 0;
		int brojKoraka = micanja.length();

		for (int i = 0; i < brojKoraka; i++) {

			String korak = String.valueOf(micanja.charAt(i));

			if (napraviKorak(korak, labirint[xKoord][yKoord])) {

				micanja += korak;

				switch (korak) {
					case "U":
						xKoord++;
						break;
					case "D":
						xKoord--;
						break;
					case "L":
						yKoord--;
						break;
					case "R":
						yKoord++;
						break;
					default:
						break;
				}

				rez += 50;

			} else {
				rez -= 100;
			}

			if (xKoord == 4 && yKoord == 4) {
				isPronadenoRjesenje = true;
				break;
			}
		}
		// if (true) throw new EmptyStackException();

		double kaznaZaBrKoraka = KAZNA_KORACI * Math.abs(najboljeRjesenje.length() - brojKoraka);
		double nagradaZaBlizinu = xKoord * NAGRADA_BLIZINA + yKoord * NAGRADA_BLIZINA;

		rez -= kaznaZaBrKoraka;
		rez += nagradaZaBlizinu;

		if (isPronadenoRjesenje)
			// throw new EmptyStackException();
			rez += EVO_TI_KEKS;

		if (rez < 0)
			rez = 0;

		// System.out.println("Fitness:" + rez);
		// System.out.println(micanja);

		return rez;
	}

	private static boolean napraviKorak(String smjer, Celija celija) {

		boolean rezultat = false;

		switch (smjer) {
			case "U":
				if (celija.isMoguceKretanjeGore())
					rezultat = true;
				break;
			case "D":
				if (celija.isMoguceKretanjeDolje())
					rezultat = true;
				break;
			case "L":
				if (celija.isMoguceKretanjeLijevo())
					rezultat = true;
				break;
			case "R":
				if (celija.isMoguceKretanjeDesno())
					rezultat = true;
				break;
			default:
				break;
		}

		return rezultat;
	}

	public static String getStringRezultat(IChromosome chromos) {

		String[] temp = new String[chromos.size()];

		for (int i = 0; i < chromos.size(); i++) {
			temp[i] = (String) chromos.getGene(i).getAllele();
		}

		String rezultat = String.join("", temp);

		return rezultat;

	}

	public static boolean isTocno(String rjesenje) {
		
		int xKoord = 0, yKoord = 0;
		boolean isPronadenoRjesenje = false;

		for (int i = 0; i < rjesenje.length(); i++) {
			
			String korak = String.valueOf(rjesenje.charAt(i));
			
			Celija[][] labirint = Main.getLabirint();

			if (napraviKorak(korak, labirint[xKoord][yKoord])) {

				switch (korak) {
					case "U":
						xKoord++;
						break;
					case "D":
						xKoord--;
						break;
					case "L":
						yKoord--;
						break;
					case "R":
						yKoord++;
						break;
					default:
						break;
				}
			}

			if (xKoord == 4 && yKoord == 4) {
				isPronadenoRjesenje = true;
				break;
			}	
		}
		
		return isPronadenoRjesenje;
	}

}
