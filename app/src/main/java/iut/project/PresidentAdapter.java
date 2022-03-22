package iut.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/*
    Création de l'adapteur pour notre liste de presidents qui etend 'BaseAdapter'
 */
public class PresidentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<President> listePresidents;
    private LayoutInflater layoutInflater;

    public PresidentAdapter(Context context, ArrayList<President> listePresidents) {
        this.context = context;
        this.listePresidents = listePresidents;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listePresidents.size();
    }

    @Override
    public Object getItem(int position) {
        return listePresidents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;
        if(convertView == null) {
            layoutItem = (LinearLayout) layoutInflater.inflate(R.layout.president, parent, false);
        }
        else {
            layoutItem = (LinearLayout) convertView;
        }

        TextView tvNom = layoutItem.findViewById(R.id.nom);
        TextView tvStatut = layoutItem.findViewById(R.id.statut);
        TextView tvAge = layoutItem.findViewById(R.id.age);
        TextView tvPeriode = layoutItem.findViewById(R.id.periode);

        String nom = listePresidents.get(position).getNom();
        String statut = listePresidents.get(position).getStatut();
        Integer age = (listePresidents.get(position).getAge());
        String periode = listePresidents.get(position).getPeriode();
        tvNom.setText(nom);
        tvStatut.setText(context.getString(R.string.statut) + " : " + statut);
        tvAge.setText(context.getString(R.string.age) + " : " + age + " " + context.getString(R.string.ans));
        tvPeriode.setText(context.getString(R.string.periodeSimple) + " : " + periode);


        return layoutItem;
    }

}
