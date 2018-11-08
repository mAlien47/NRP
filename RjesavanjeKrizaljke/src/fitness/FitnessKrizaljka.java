package fitness;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import main.Main;

public class FitnessKrizaljka extends FitnessFunction {
	
	private int[] rjesenje;
	
	public static final int KOEF = 1;
	public static final int MAX_VRIJEDNOST_GENA = 26; // broj slova eng abc
	
	public FitnessKrizaljka(int[] rjesenjeASCII) {
		this.rjesenje = rjesenjeASCII;
	}

	
	@Override
	protected double evaluate(IChromosome chromosome) {
		
		double fitness = 0;

		for (int i = 0; i < rjesenje.length; i++) {
			int val = (Integer)chromosome.getGene(i).getAllele();
			
			fitness += dohvatiFitnessVrijednostGena(val, rjesenje[i]);
		}
		
		if (fitness < 0) fitness = 0;
		
		return fitness;
	}
	
	
	private double dohvatiFitnessVrijednostGena(int val, int trazenaVrijednost) {
		
		double udaljenostOdTrazeneVrijednosti;
		
		udaljenostOdTrazeneVrijednosti =  Math.abs(trazenaVrijednost - val);
		
		udaljenostOdTrazeneVrijednosti = MAX_VRIJEDNOST_GENA - udaljenostOdTrazeneVrijednosti;
		
		return udaljenostOdTrazeneVrijednosti * KOEF;
	}

}
