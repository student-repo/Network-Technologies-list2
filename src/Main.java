import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
//        JSONObject obj = new JSONObject(readFile(new File("").getAbsolutePath() + "/json-example.json", StandardCharsets.UTF_8));
//        String pageName = obj.getJSONObject("pageInfo").getString("pageName");
//
//        JSONArray arr = obj.getJSONArray("posts");
//        for (int i = 0; i < arr.length(); i++) {
//            String post_id = arr.getJSONObject(i).getString("post_id");
//            System.out.println(post_id);
//        }
        JSONObject obj = new JSONObject(readFile(new File("").getAbsolutePath() + "/data.json", StandardCharsets.UTF_8));
//        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

//        JSONArray arr = obj.getJSONArray("edge-capacity");
//        for (int i = 0; i < arr.length(); i++) {
//            int post_id = arr.getJSONObject(i).getInt("capacity");
//            System.out.println(arr.getJSONObject(i).keySet());
//            System.out.println(post_id);
//        }

        JSONArray arr1 = obj.getJSONArray("packages-intensity-matrix");
        for (int i = 0; i < arr1.length(); i++) {
//            int post_id = arr1.getJSONObject(i).getInt("capacity");
            System.out.println(arr1.getJSONObject(i).keySet().toArray()[0]);
//            System.out.println(post_id);
        }
    }
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
