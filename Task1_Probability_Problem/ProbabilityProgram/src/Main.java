import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Example: Flipping of a coin
        List<Outcome> outcomes= new ArrayList<>();

        //Adding some values else output will be zero forever
        outcomes.add(new Outcome("Head", 0.35));
        outcomes.add(new Outcome("Tail", 0.65));

        EventSimulator coinFlipStimulator=new EventSimulator(outcomes);

        // Simulate the event 1000 times
        int totalSimulations = 1000;

        int[] counts = new int[outcomes.size()];

        // Perform the simulations
        for (int i = 0; i < totalSimulations; i++) {
            String result = coinFlipStimulator.generateEvent();

            // Update the counts based on the simulated outcome
            for (int j = 0; j < outcomes.size(); j++) {
                if (result.equals(outcomes.get(j).result)) {
                    counts[j]++;
                    break;
                }
            }
        }

        // Display results
        System.out.println("Outcome Distribution:");
        System.out.print("On triggering the event " + totalSimulations + " times,");
        for (int i = 0; i < outcomes.size(); i++) {
            System.out.print(outcomes.get(i).result + " appeared " + counts[i] + " times ");
        }
        System.out.println("which is roughly inline with the biasness given.");
        System.out.println("This is just one of the possibilities.");


    }
}