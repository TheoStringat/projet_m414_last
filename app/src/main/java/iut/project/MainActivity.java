package iut.project;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button bouton_accueil;
    private ImageView imageFranceDrapeau;
    private ImageView imageFranceUe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        imageFranceDrapeau = findViewById(R.id.imageFranceDrapeau);
        imageFranceUe = findViewById(R.id.imageFranceUe);

        imageFranceUe.setVisibility(View.INVISIBLE);
        rotation_image(getImageView(imageFranceDrapeau));
        imageFranceDrapeau.setVisibility(View.INVISIBLE);
        imageFranceUe.setVisibility(View.VISIBLE);
        rotation_image(getImageView(imageFranceUe));


        /*
            Action sur le widget bouton
         */
        bouton_accueil = (Button) findViewById(R.id.cliquer_accueil);
        getBouton().setOnClickListener((View v) -> {
            Intent ActiviteListe = new Intent(MainActivity.this, ListeActivity.class);
            startActivity(ActiviteListe);
        });
    }

    public Button getBouton () {
        return bouton_accueil;
    }

    public ImageView getImageView(ImageView imageView) {
        return imageView;
    }

    /*
        Mise en place de l'animation de l'image
     */
    public void rotation_image(ImageView imageView) {
        imageView.animate().rotationYBy(360f).setDuration(6000);
    }
}