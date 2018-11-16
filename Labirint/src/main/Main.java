package main;

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StringGene;

import fitness.FitnessLabirint;
import labirint.Celija;

public class Main {

	public static int VELICINA_LABIRINTA = 25;

	//public static final int[] najboljeRjesenje = new int[] { 1, 0, 1, 0, 3, 0, 1, 1, 2, 1, 0, 0, 2 };

	public static final int BROJ_EVOLUCIJA = 100;
	public static final int VELICINA_POPULACIJE = 3000;

	public static void main(String[] args) {

		FitnessFunction fitness = new FitnessLabirint();
		DefaultConfiguration conf = new DefaultConfiguration();

		try {
			
			conf.addGeneticOperator(new CrossoverOperator(conf));
			// conf.setPreservFittestIndividual(true);
			// conf.setKeepPopulationSizeConstant(false); 
			conf.setFitnessFunction(fitness);

			Gene[] sampleGenes = new Gene[VELICINA_LABIRINTA];
			
			for (int i = 0; i < VELICINA_LABIRINTA; i++) {
				sampleGenes[i] = new StringGene(conf, 0, 1, "UDLR");
			}

			IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);

			conf.setPopulationSize(VELICINA_POPULACIJE);
			conf.setSampleChromosome(sampleChromosome);

			Genotype population = Genotype.randomInitialGenotype(conf);

			for (int i = 0; i < BROJ_EVOLUCIJA; i++) {

				IChromosome najboljiKromosom = population.getFittestChromosome();
				String kromosom = FitnessLabirint.getStringRezultat(najboljiKromosom);
				
				String output = "\nNajbolje rješenje nakon" + i + " evolucije: " + najboljiKromosom.getFitnessValue() +". " + (
						FitnessLabirint.isTocno(kromosom) ? 
								isNajbolje(najboljiKromosom) ? "Pronađeno najbolje rješenje" : "Pronađeno točno rješenje" :
								"Nije točno rješenje"
								);
				
				System.out.println(output);
				
				printRjesenje(najboljiKromosom);

				population.evolve();

			}

		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}

	}

	public static Celija[][] getLabirint() {

		Celija[][] labirint = new Celija[5][5];

		labirint[0][0] = new Celija(0, 0, false, true, false, false);
		labirint[0][1] = new Celija(0, 1, true, false, false, true);
		labirint[0][2] = new Celija(0, 2, false, true, false, false);
		labirint[0][3] = new Celija(0, 3, true, true, false, true);
		labirint[0][4] = new Celija(0, 4, false, false, false, true);

		labirint[1][0] = new Celija(1, 0, true, false, false, false);
		labirint[1][1] = new Celija(1, 1, false, true, true, false);
		labirint[1][2] = new Celija(1, 2, true, false, false, true);
		labirint[1][3] = new Celija(1, 3, false, true, true, false);
		labirint[1][4] = new Celija(1, 4, true, false, false, true);

		labirint[2][0] = new Celija(2, 0, false, true, true, false);
		labirint[2][1] = new Celija(2, 1, true, true, false, true);
		labirint[2][2] = new Celija(2, 2, false, false, true, true);
		labirint[2][3] = new Celija(2, 3, true, true, false, false);
		labirint[2][4] = new Celija(2, 4, true, false, true, true);

		labirint[3][0] = new Celija(3, 0, true, false, false, false);
		labirint[3][1] = new Celija(3, 1, false, true, true, false);
		labirint[3][2] = new Celija(3, 2, true, true, false, true);
		labirint[3][3] = new Celija(3, 3, true, false, true, true);
		labirint[3][4] = new Celija(3, 4, true, false, true, false);

		labirint[4][0] = new Celija(4, 0, false, true, true, false);
		labirint[4][1] = new Celija(4, 1, false, true, false, true);
		labirint[4][2] = new Celija(4, 2, false, false, true, true);
		labirint[4][3] = new Celija(4, 3, false, false, true, false);
		labirint[4][4] = new Celija(4, 4, false, true, true, false);

		return labirint;
	}

	private static void printRjesenje(IChromosome kromosom) {

		for (int i = 0; i < kromosom.size(); i++) {
			System.out.print((String)kromosom.getGene(i).getAllele());
		}
		System.out.println();
	}
	
	private static boolean isNajbolje(IChromosome chromosome) {
		if (FitnessLabirint.getStringRezultat(chromosome).equals(FitnessLabirint.najboljeRjesenje))
			return true;
		return false;
	}
	
}
