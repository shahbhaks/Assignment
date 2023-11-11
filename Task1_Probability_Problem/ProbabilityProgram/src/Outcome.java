//// Class to represent an outcome with a result and its probability
public class Outcome {
    String result;
    double probability;

    //Generating constructor
    public Outcome(String result, double probability) {
        this.result = result;
        this.probability = probability;
    }

    public String getResult() {
        return result;
    }

    public double getProbability() {
        return probability;
    }
}
