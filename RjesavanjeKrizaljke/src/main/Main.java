package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

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

import fitness.FitnessKrizaljka;

public class Main {

	private static final String[] rjesenje = ("KLUB" + "RADA" + "ARAK" + "JAVA").split("");
	private static final int duljinaRedaKrizaljke = 4;

	// A = 65, Z = 90
	public static final int najmanjiASCII = 65;
	public static final int najveciASCII = 90;

	public static final int BROJ_EVOLUCIJA = 50;
	public static final int VELICINA_POPULACIJE = 1000;
	
	static boolean isSetPreservFittest = false, isSetKeepPopulationSizeConstant = false;

	public static void main(String[] args) {

		int[] rjesenjeASCII = Arrays.stream(rjesenje).mapToInt(x -> (char) x.charAt(0)).toArray();


		FitnessFunction fitness = new FitnessKrizaljka(rjesenjeASCII);
		DefaultConfiguration conf = new DefaultConfiguration();

		// dodati operatore za rekombinaciju i mutaciju
		try {

			conf.addGeneticOperator(new CrossoverOperator(conf));
			conf.addGeneticOperator(new MutationOperator(conf));
			//conf.setPreservFittestIndividual(true); isSetPreservFittest = true;
			//conf.setKeepPopulationSizeConstant(false); isSetKeepPopulationSizeConstant = true;
			conf.setFitnessFunction(fitness);

			int chromosomeSize = rjesenjeASCII.length;

			/*
			 * Kreirati gene cjelobrojnog tipa u obliku jednodimenzionalnog polja koji će
			 * sadržavati ASCII vrijednosti slova unutar križaljke.
			 */
			Gene[] sampleGenes = new Gene[chromosomeSize];

			for (int i = 0; i < chromosomeSize; i++) {
				sampleGenes[i] = new IntegerGene(conf, najmanjiASCII, najveciASCII); // inclusive oboje
			}

			IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);

			conf.setPopulationSize(VELICINA_POPULACIJE);
			conf.setSampleChromosome(sampleChromosome);

			Genotype population = Genotype.randomInitialGenotype(conf);

			for (int i = 0; i < BROJ_EVOLUCIJA; i++) {

				IChromosome najboljiKromosom = population.getFittestChromosome();

				System.out.println("\nNajbolje rješenje nakon " + i + ". evolucije:");

				ispisKromosoma(najboljiKromosom);

				if (isTocnoRjesenje(najboljiKromosom)) {
					try {
						spremiNaDisk(i);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}

				population.evolve();

			}

		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void ispisKromosoma(IChromosome kromosom) {

		int indeksOd = 0, indeksDo = duljinaRedaKrizaljke;

		for (int i = 0; i < rjesenje.length / duljinaRedaKrizaljke; i++) {

			for (int j = indeksOd; j < indeksDo; j++) {
				System.out.print((char) ((Integer) kromosom.getGene(j).getAllele()).intValue());
			}

			indeksOd = indeksDo;
			indeksDo += duljinaRedaKrizaljke;
			System.out.println();
		}

		System.out.println("\nFitness: " + kromosom.getFitnessValue());
	}

	private static void spremiNaDisk(int i) throws IOException {
		
		String rez = System.lineSeparator() + "Rješenje pronađeno nakon " + i + ". evolucije" + System.lineSeparator();
		rez += "Broj evolucija: " + BROJ_EVOLUCIJA + System.lineSeparator();
		rez += "Veličina populacije: " + VELICINA_POPULACIJE + System.lineSeparator();
		rez += "Koeficijent: " + FitnessKrizaljka.KOEF + System.lineSeparator();
		rez += "Maksimalna vrijednost gena: " + FitnessKrizaljka.MAX_VRIJEDNOST_GENA + System.lineSeparator();
		rez += "PreservFittest: " + isSetPreservFittest + System.lineSeparator();
		rez += "KeepPopulationConstant: " + isSetKeepPopulationSizeConstant + System.lineSeparator();
		
		
		Files.write(Paths.get("rezosi.txt"), rez.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}
	
	private static boolean isTocnoRjesenje(IChromosome kromosom) {
		
		int indeksOd = 0, indeksDo = duljinaRedaKrizaljke;

		for (int i = 0; i < rjesenje.length / duljinaRedaKrizaljke; i++) {

			for (int j = indeksOd; j < indeksDo; j++) {
				if (!String.valueOf( (char)((Integer) kromosom.getGene(j).getAllele()).intValue()).equals(rjesenje[j]))
				{
					return false;
				};
			}
			indeksOd = indeksDo;
			indeksDo += duljinaRedaKrizaljke;
		}
		return true;
	}

}
