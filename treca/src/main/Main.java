package main;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class Main {
	
	public static final Note[][] PJESME_INPUT = {
			{
				Note.A, Note.H, Note.C, Note.D,
				Note.A, Note.F, Note.G, Note.D,
				Note.E, Note.E, Note.E, Note.H
			},
			{
				Note.A, Note.H, Note.C, Note.D,
				Note.A, Note.F, Note.G, Note.D,
				Note.E, Note.E, Note.E, Note.H
			},
			{
				Note.A, Note.H, Note.C, Note.D,
				Note.A, Note.F, Note.G, Note.D,
				Note.E, Note.E, Note.E, Note.H
			},
			{
				Note.A, Note.H, Note.C, Note.D,
				Note.A, Note.F, Note.G, Note.D,
				Note.E, Note.E, Note.E, Note.H
			},
			{
				Note.A, Note.H, Note.C, Note.D,
				Note.A, Note.F, Note.G, Note.D,
				Note.E, Note.E, Note.E, Note.H
			},
			{
				Note.A, Note.H, Note.C, Note.D,
				Note.A, Note.F, Note.G, Note.D,
				Note.E, Note.E, Note.E, Note.H
			},
			{
				Note.A, Note.H, Note.C, Note.D,
				Note.A, Note.F, Note.G, Note.D,
				Note.E, Note.E, Note.E, Note.H
			}
	};
	
	public static final double[][] PJESME_OUTPUT = {
			{
				
			},
			{
				
			},
			{
				
			},
			{
				
			},
			{
				
			},
			{
				
			},
			{
				
			}
	};

	public static void main(String[] args) {

		// create a neural network, without using a factory
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null,true,2));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,3));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),false,1));
		network.getStructure().finalizeStructure();
		network.reset();
		
		// create training data
		MLDataSet trainingSet = new BasicMLDataSet();
		
		// train the neural network
		final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
		
		int epoch = 1;
		 
		do {
			train.iteration();
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
		} while(train.getError() > 0.01);
		train.finishTraining();
 
		// test the neural network
		System.out.println("Neural Network Results:");
		for(MLDataPair pair: trainingSet ) {
			final MLData output = network.compute(pair.getInput());
			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1)
					+ ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
		}
 
		Encog.getInstance().shutdown();

	}

}
