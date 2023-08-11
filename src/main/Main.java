package main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


public class Main {

    static HashMap<String, Integer> similarities  = new HashMap<>();

    public static void main(String[] args) throws IOException {
        int count=0, total=0;
        int duplicates=0, englDuplicates=0, positiveDups=0;
        for (int s=0; s < 500; s++) {
            String batchNumber = String.format("%03d", s);
            String data = new String(Files.readAllBytes(Paths.get("./reviews/batch-" + batchNumber + ".json")));

            JSONArray jsonArray = new JSONArray(data);
            for (int i=0; i < jsonArray.length(); i++){
                total++;
                String str = jsonArray.get(i).toString();
                JSONObject object1 = new JSONObject(str);

                boolean status = object1.getBoolean("voted_up");
                String review = object1.getString("review");

                if (similarities.containsKey(review.toLowerCase())) {
                    if (status) positiveDups++;
                    if (object1.getString("language").contains("english")) {
                        englDuplicates++;
                    } else {
                        duplicates++;
                    }
                } else {
                    similarities.put(review.toLowerCase(), 0);
                }

                if (object1.getString("language").contains("english")) {
                    System.out.println("Review [" + count + "] " + status + ": \n" + review);
                    count++;
                }
            }
        }
        System.out.println("\nAmount: " + total + "\nEnglish Duplicates: " + englDuplicates + "\nNon-English Duplicates: " + duplicates + "\nTotal Duplicates: " + (englDuplicates+duplicates));

    }
}