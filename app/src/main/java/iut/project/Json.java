package iut.project;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * CLASSE A SUPPRIMER UNE FOIS REQUETE SUR SERVEUR WEB EFFECTUEE
 */
public class Json extends AppCompatActivity {
    private ArrayList<President> listePresidents;

    public Json() {
        listePresidents = new ArrayList<>();
    }

    public ArrayList<President> getListePresidents() {
        return listePresidents;
    }

    public int size() {
        return listePresidents.size();
    }

    public President get(int pos) {
        return listePresidents.get(pos);
    }



    public void construireListe(Context context) {
        // Création de la liste des présidents
        try {
            // Récupération du json
            JSONArray jsonArray = new JSONArray(getJSONFromAsset(context));
            // Récupération des personnes
            for (int i = 0; i < jsonArray.length(); i++) {
                listePresidents.add(getPresidentFromJSONObject(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //récupération d'un président dans le fichier JSON
    private President getPresidentFromJSONObject(JSONObject jsonObject) throws JSONException {
        //nom du president
        String nom = jsonObject.getString("nom");

        //statut du président : vivant ou décédé
        String statut = jsonObject.getString("statut");

        //age du president
        int age = jsonObject.getInt("age");

        //recuperation de la periode
        String periode = jsonObject.getString("periode");

        //recuperation de la description
        String description = jsonObject.getString("description");

        //recuperation de la description
        String lien = jsonObject.getString("lien");

        return new President(nom, statut, age, periode, description, lien);
    }


    //lecture du fichier json en String
    private static String getJSONFromAsset(Context context){
        //JSONArray jsonArray = new URL("https://unice-my.sharepoint.com/:u:/g/personal/theo_stringat_etu_unice_fr/Ed8fVJL1_zJFq4D3g73BWzoBwOReNVd1tUaCxmduvE73RQ?e=cOC1Aa");
        String json = null;
        try {
            InputStream is = context.getAssets().open("president.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return json;
    }

}
