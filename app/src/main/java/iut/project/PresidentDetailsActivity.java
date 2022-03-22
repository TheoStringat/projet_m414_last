package iut.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details_presidents);

        if (getIntent().hasExtra(getString(R.string.president_selectionne))) {
            president = getIntent().getParcelableExtra(getString(R.string.president_selectionne));
        }

        nom = findViewById(R.id.nom);
        statut = findViewById(R.id.statut);
        age = findViewById(R.id.age);
        periode = findViewById(R.id.periode);
        description = findViewById(R.id.description);

        nom.setText(getString(R.string.name).toUpperCase() + " : " + president.getNom());
        statut.setText(getString(R.string.statut).toUpperCase() + " : " + president.getStatut());
        age.setText(getString(R.string.age).toUpperCase() + " : " + president.getAge());
        periode.setText(getString(R.string.periode).toUpperCase() + " : " + president.getPeriode());
        description.setText(getString(R.string.description).toUpperCase() + " : " + president.getDescription());
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
        //Nouvelle clé à garder dans le doute : AIzaSyBPtBm0KZl9dFPSS2BYuReLdtldAJofKXo
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
        String url = getString(R.string.wikipedia) + nom;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }




    public void affichePhotos(){
        String nomPhoto = tabPhotos[0].toLowerCase();
        if(nomPhoto.equals("françois"))
            nomPhoto = "francois";
        for(int i=1; i<tabPhotos.length; i++) {
            nomPhoto += "_" + tabPhotos[i].toLowerCase();
        }
        int res = getResources().getIdentifier(nomPhoto, "drawable", this.getPackageName());
        iv = findViewById(R.id.imageView);
        iv.setImageResource(res);
    }
}