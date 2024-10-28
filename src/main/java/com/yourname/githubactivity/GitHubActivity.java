package src.main.java.com.yourname.githubactivity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;          // Helps us work with arrays of JSON data
import org.json.JSONObject;

public class GitHubActivity {

    public static void main(String[] args) {
        String username = "alimukhammad";
        String url = "https://api.github.com/users/" + username + "/events";
   
        System.out.println("Hello, GitHub Activity!");

        try{
            //Step1: Create a client to make requests
            HttpClient client = HttpClient.newHttpClient();

            //Step2: Creae request for Github Api
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            //Step3 send request and get response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Step4: Request status
            if(response.statusCode() == 200){
                
                //Step5: Parse the JSON response
                JSONArray events = new JSONArray(response.body());

                //Step6: Loop through recent events (top 5) and print them
                for(int i=0; i<Math.min(events.length(), 5); i++){
                    JSONObject event = events.getJSONObject(i);
                    String type = event.getString("type");
                    String repoName = event.getJSONObject("repo").getString("name");
                    String createdAt = event.getString("created_at");

                    System.out.println("Event: " +  type);
                    System.out.println("Repository: " + repoName);
                    System.out.println("Date: " + createdAt);
                    System.out.println("---------------------------------");
                }
            }else{
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
