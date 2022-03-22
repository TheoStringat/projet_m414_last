package iut.project;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpAsyncGet<T>{
    private static final String TAG = "theo " + HttpAsyncGet.class.getSimpleName();    //Pour affichage en cas d'erreur
    private String urlAddress;
    private List<T> itemList;
    private HttpHandler webService;

    public HttpAsyncGet(String url) {
        super();
        this.urlAddress = url;
        webService = new HttpHandler();
        this.itemList = new ArrayList<T>();
    }

    /**
     * you must change this method
     * don't forget to initialize item with cast (T)yourModel at the end;
     */
    public void doInBackGround(){
        String nom=null;
        String statut=null;
        int age=0;
        String periode=null;
        String description=null;
        String lien=null;

        // get the jsonStr to parse
        String jsonStr = webService.makeServiceCall(urlAddress);
        Log.d(TAG, urlAddress);
        Log.d(TAG, jsonStr);
        //parse jsonStr
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray presidents = jsonObj.getJSONArray("presidents");
                for (int i = 0; i < presidents.length(); i++) {
                    JSONObject pres = presidents.getJSONObject(i);
                    nom = pres.getString("nom");
                    statut = pres.getString("statut");
                    age =pres.getInt("age");
                    periode =pres.getString("periode");
                    description =pres.getString("description");
                    lien =pres.getString("lien");

                    itemList.add((T)new President(nom, statut, age, periode, description, lien));
                }

            } catch (final JSONException e) {
                Log.d(TAG, "Erreur JSON " + e.getMessage());

            }
        } else {
            Log.e(TAG, "Probleme connexion ");
        }
        Log.d(TAG, "president="+itemList);
    }

    public List<T> getItemResult() {
        return itemList;
    }

    class HttpHandler { //innerClass

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
                connection.setRequestMethod("GET");
                // lecture du fichier
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToString(inputStream);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        //Conversion flux en String
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                    Log.e(TAG,line);
                }
            }
            catch (IOException e) {  e.printStackTrace();   }
            finally {
                try { inputStream.close(); } catch (IOException e) { e.printStackTrace();  }
            }
            return stringBuilder.toString();
        }
    }
}
