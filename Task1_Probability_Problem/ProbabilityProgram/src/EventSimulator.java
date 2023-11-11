import java.util.List;
import java.util.Random;

public class EventSimulator {
    private List<Outcome> outcomes;
    private Random random;

    public EventSimulator(List<Outcome> outcomes) {
        this.outcomes = outcomes;
        this.random = new Random();
    }

    //Method to simulate a single event occurrence and return the outcome
    public String generateEvent() {

        //Generates random numbers between 0.0-1.0
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0;

        // Iterate through each outcome to determine the result based on probabilities
        for (Outcome event : outcomes) {

            cumulativeProbability += event.getProbability();

            //Check if the random number falls within the current outcome's probability range
            if (randomValue <= cumulativeProbability) {
                return event.getResult();
            }
        }

        // Default case, should not happen if probabilities are correct
        return null;
    }

}
