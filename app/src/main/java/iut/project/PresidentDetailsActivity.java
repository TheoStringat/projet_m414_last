package iut.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

import static android.text.TextUtils.replace;
import static android.text.TextUtils.split;

public class PresidentDetailsActivity extends AppCompatActivity {
    private President president;
    private String [] tabPhotos;
    private ImageView iv;
    private TextView nom;
    private TextView statut;
    private TextView age;
    private TextView periode;
    private TextView description;
    private String leNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_presidents);

        nom = (TextView) findViewById(R.id.nom);
        statut = (TextView) findViewById(R.id.statut);
        age = (TextView) findViewById(R.id.age);
        periode = (TextView) findViewById(R.id.periode);
        description = (TextView) findViewById(R.id.description);


        if (getIntent().hasExtra("president_selectionne")) {
            president = getIntent().getParcelableExtra("president_selectionne");
        }

        nom.setText("Nom".toUpperCase() + " : " + president.getNom());
        statut.setText("Statut".toUpperCase() + " : " + president.getStatut());
        age.setText("Age".toUpperCase() + " : " + president.getAge());
        periode.setText("Période en tant que président de la République : ".toUpperCase() + president.getPeriode());
        description.setText("Description".toUpperCase() + " : " + president.getDescription());
        description.setMovementMethod(new ScrollingMovementMethod());

        leNom = getPresident().getNom();
        tabPhotos = leNom.split("[' ]");

        this.affichePhotos();
    }

    public President getPresident() {
        return president;
    }




    /*
        Utilisation de web services publics
     */
    public void lancer(View view) {
        //Nouvellesclé a garder dans le doute : AIzaSyBPtBm0KZl9dFPSS2BYuReLdtldAJofKXo
        Log.d("lien", president.getLien());
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this, "AIzaSyALoiu6US_Ra4bMq7hNErI1lvxOStEOJX0", president.getLien());
        startActivity(intent);
    }
    public void enSavoirPlus(View view) {
        String nom = tabPhotos[0];
        for(int i=1; i<tabPhotos.length; i++) {
            nom += "_" + tabPhotos[i];
        }
        if(tabPhotos.length > 3){
            nom = "Valéry_Giscard_d%27Estaing";
        }
        String url = "https://fr.wikipedia.org/wiki/" + nom;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }




    public void affichePhotos(){
        String nomPhoto = tabPhotos[0].toLowerCase();
        Log.d("theo", nomPhoto);
        if(nomPhoto.equals("françois"))
            nomPhoto = "francois";
        for(int i=1; i<tabPhotos.length; i++) {
            nomPhoto += "_" + tabPhotos[i].toLowerCase();
        }
        Log.d("monTag", nomPhoto);
        int res = getResources().getIdentifier(nomPhoto, "drawable", this.getPackageName());
        iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageResource(res);
    }
}