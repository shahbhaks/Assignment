import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
public class Main {
    private static final String API_URL = "http://api.mathjs.org/v4/";
    public static void main(String[] args) {

        //Read inputs and save it in arraylist
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> expressions = new ArrayList<>();

        try {
            System.out.println("Enter mathematical expressions (one per line) and 'end' to finish:");
            while (true) {
                String input = reader.readLine();
                if (input.equals("end")) {
                    break;
                }
                expressions.add(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //pass all things to api
        evaluateAndDisplayResults(expressions);
    }

    private static void evaluateAndDisplayResults(List<String> expressions) {

        int rateLimit = 50; // API rate limit per second
        int numThreads = Math.min(expressions.size(), rateLimit);   //we are checking this for deciding thread pool size
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads); //

        try {
            List<Future<String>> results = new ArrayList<>();

            //making seperate threads for each expression
            for (String expression : expressions) {

                Callable<String> evaluationTask = () -> evaluateExpression(expression);
                Future<String> result = executorService.submit(evaluationTask);
                results.add(result);
                Thread.sleep(1000 / rateLimit); // Throttle requests to respect rate limit
            }

            // Wait for all tasks to complete
            for (Future<String> result : results) {
                try {
                    System.out.println(result.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static String evaluateExpression(String expression) {
        try {
            // Construct the URL with the expression
            String encodedExpression = java.net.URLEncoder.encode(expression, StandardCharsets.UTF_8);
            URL url = new URL(API_URL + "?expr=" + encodedExpression);

            //We are opening URL connection and setting HTTP method
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String result = reader.readLine();
                reader.close();
                return expression + " => " + result;
            } else {
                return expression + " => Error: HTTP " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return expression + " => Error: " + e.getMessage();
        }

    }
}