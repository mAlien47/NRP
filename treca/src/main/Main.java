package main;

import org.encog.Encog;

import java.util.Arrays;
import java.util.stream.*;

import org.encog.engine.network.activation.ActivationCompetitive;
import org.encog.engine.network.activation.ActivationElliott;
import org.encog.engine.network.activation.ActivationElliottSymmetric;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class Main {

	public static final Note[][] NOTE_INPUT = { 
			{ Note.C, Note.H, Note.A, Note.G, Note.F, Note.F, Note.E },
			//{ Note.D, Note.C, Note.H, Note.A, Note.G, Note.A, Note.E },
			{ Note.E, Note.D, Note.C, Note.H, Note.A, Note.A, Note.H },
			{ Note.F, Note.E, Note.D, Note.C, Note.H, Note.D, Note.H },
			{ Note.G, Note.F, Note.E, Note.D, Note.C, Note.D, Note.G },
			{ Note.A, Note.G, Note.F, Note.E, Note.C, Note.F, Note.G } };

	//public static final double[][] PJESME_OUTPUT = { { 0.0 }, { 0.2 }, { 0.4 }, { 0.6 }, { 0.8 }, { 1.0 } };
	public static final double[][] PJESME_OUTPUT = { { 0.0 }, { 0.25 }, { 0.5 }, { 0.75 }, { 1.0 } };

	public static void main(String[] args) {

		// create a neural network, without using a factory
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null, true, 7));
		network.addLayer(new BasicLayer(new ActivationElliott(), true, 9));
		network.addLayer(new BasicLayer(new ActivationElliott(), true, 11));
		network.addLayer(new BasicLayer(new ActivationElliottSymmetric(), true, 15));
		network.addLayer(new BasicLayer(new ActivationElliott(), false, 8));
		network.addLayer(new BasicLayer(new ActivationElliottSymmetric(), false, 2));
		network.addLayer(new BasicLayer(new ActivationElliott(), false, 1));
		network.getStructure().finalizeStructure();
		network.reset();

		// create training data
		final double[][] PJESME_INPUT = new double[NOTE_INPUT.length][NOTE_INPUT[0].length];
		
		for (int i = 0; i < NOTE_INPUT.length; i++) {
			for (int j = 0; j < NOTE_INPUT[i].length; j++) {
				PJESME_INPUT[i][j] = (double)NOTE_INPUT[i][j].getVrijednost();
			}
		}
		
		MLDataSet trainingSet = new BasicMLDataSet(PJESME_INPUT, PJESME_OUTPUT);

		// train the neural network
		final ResilientPropagation train = new ResilientPropagation(network, trainingSet);

		int epoch = 1;

		do {
			train.iteration();
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
		} while (train.getError() > 0.001);
		train.finishTraining();

		// test the neural network
		System.out.println("Neural Network Results:");
		for (MLDataPair pair : trainingSet) {
			final MLData output = network.compute(pair.getInput());
			double[] inputArr = pair.getInputArray();
			double[] idealArr = pair.getIdealArray();
			
			System.out.print("Input: ");
			Arrays.stream(inputArr).forEach( in -> System.out.print(in + " "));
			
			System.out.print("Output: ");
			System.out.print(output.getData(0));
			
			System.out.print(" Ideal: ");
			System.out.print(idealArr[0] + "\n");
			
			//System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) + ", actual="
				//	+ output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
		}

		Encog.getInstance().shutdown();

	}

}
