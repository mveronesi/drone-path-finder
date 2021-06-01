package org.example.controller;

import org.example.prediction.Client;
import org.example.prediction.NeuralNetworkClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private final Client neuralNetwork;
    private static final String PREDICTION_URI = "https://127.0.0.1:5000/predict";
    private final List<Point> vegetation, other;

    private Controller(Client c) {
        this.neuralNetwork = c;
        this.vegetation = new ArrayList<>();
        this.other = new ArrayList<>();
    }

    public static Controller buildController() throws IOException {
        Client c = new NeuralNetworkClient(PREDICTION_URI);
        return new Controller(c);
    }

    public void updatePoints(List<Double> latitudes,
                             List<Double> longitudes,
                             List<Reflectance> reflectances)
            throws IllegalArgumentException {

        if(latitudes.size() != longitudes.size() ||
                longitudes.size() != reflectances.size()){
            throw new IllegalArgumentException("Bad sizes");
        }
        int size = latitudes.size();
        Point.Factory factory = Point.newFactory();
        for (int i=0; i<size; ++i) {
            int prediction = neuralNetwork.predict(reflectances.get(i).getReflectanceList());
            factory.withLatitude(latitudes.get(i).intValue());
            factory.withLongitude(longitudes.get(i).intValue());
            factory.withPrediction(prediction);
            Point p = factory.build();
            factory.reset();
            if(p.getPredictionName().equals("vegetation"))
                vegetation.add(p);
            else
                other.add(p);
        }
    }

    public List<Point> getTrack(int latitude, int longitude) {
        if(vegetation.isEmpty()){
            // percorso a serpentina
            return null;
        }
        /*
        * TODO: trasformare le coordinate in un grafo, in cui i punti in vegetation
        *       sono i nodi e gli archi hanno un costo proporzionale alla distanza tra i due punti.
        *       Il punto di partenza è dato dai parametri della funzione, bisogna ritornare lì.
        *       NB: Il grafo è fully connected
        * */
        return null;
    }

}