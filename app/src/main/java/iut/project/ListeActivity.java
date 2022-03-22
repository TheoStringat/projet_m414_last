package iut.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ListeActivity extends AppCompatActivity {
    private ListView liste_presidents;
    private ProgressDialog progressDialog;
    private static String URL = "https://api.npoint.io/7b1ab2b71aa57130e37a";

    //private SearchView mySearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_liste);

        //mySearchView = (SearchView)findViewById(R.id.searchView);
        liste_presidents = (ListView) findViewById(R.id.liste_presidents);

        //La listePresidents est remplie grâce au fichier 'president.json' dans la classe Json
        executeInThread();



        /*mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s){
                adapter.getFilter().filter(s);
                return false;
            }
        });*/
    }





    /**
     * call onPreExecute() then start a new thread witch execute a  HttpAsyncGet<YourModel>
     * when job is finished, the onPostExecute(YourModel item) is automatically called
     *
     *     - write onPreExecute() method
     *     - write onPostExecute(YourModel item) method
     *     - change HttpAsyncGet<YourModel>.doInBackGround() method
     */
    private void executeInThread(){
        onPreExecute();
        HttpAsyncGet<President> getPresident = new HttpAsyncGet(URL);
        Runnable runnable = ()->{
            getPresident.doInBackGround();
            runOnUiThread( ()-> onPostExecute( getPresident.getItemResult()) );
        };
        Executors.newSingleThreadExecutor().execute( runnable );
    }

    /**
     * This method is called before the asynchronous webConnexion start
     */
    private void onPreExecute() {
        progressDialog = new ProgressDialog(ListeActivity.this);
        progressDialog.setMessage("Accès aux données de la Maison Blanche...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    /**
     * asynchrone callBack task
     * this is HttpAsyncGet instance is ready
     * @param presidentList is the return item from webService
     */
    private void onPostExecute(ArrayList<President> presidentList) {
        progressDialog.dismiss();
        ArrayList<String> namesOfPres = new ArrayList<String>();
        presidentList.forEach( president -> namesOfPres.add(president.toString()));

        //On crée l'adapteur pour associer la listeView 'liste-presidents' à notre liste de présidents 'listeAdapter'
        PresidentAdapter adapter = new PresidentAdapter(this, presidentList);

        //On passe nos données au composant ListView
        liste_presidents.setAdapter(adapter);

        liste_presidents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent PresidentDetailsActivity = new Intent(ListeActivity.this, PresidentDetailsActivity.class);
                PresidentDetailsActivity.putExtra(getString(R.string.president_selectionne), presidentList.get(position));
                startActivity(PresidentDetailsActivity);
            }
        });
    }
}
