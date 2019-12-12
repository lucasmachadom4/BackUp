package br.com.pucgo.adote.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.pucgo.adote.R;
import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.util.Valida;

public class AdapterAnimais extends BaseAdapter {

    private Context context;
    //private ArrayList<Animal> listaAnimais;
    private Animal[] listaAnimais;

    public AdapterAnimais(Context context, Animal[] listaAnimais) {
        this.context = context;
        this.listaAnimais = listaAnimais;
    }

    @Override
    public int getCount() {
        return this.listaAnimais.length;
    }

    @Override
    public Object getItem(int position) {
        return this.listaAnimais[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Valida valida = new Valida();
        View v = View.inflate(this.context, R.layout.adapter_layouy_animal , null);

        //Variaveis da View a ser preenchida
        ImageView imvImagem = v.findViewById(R.id.imvImagem);
        TextView tvIdAnimal = v.findViewById(R.id.tvIdAnimal);
        TextView tvNomeAnimal = v.findViewById(R.id.tvNomeAnimal);
        //TextView tvSexo = v.findViewById(R.id.tvSexo);
        TextView tvCidade = v.findViewById(R.id.tvCidade);


        //imvImagem.setImageURI();
        tvIdAnimal.setText( ""+ this.listaAnimais[position].getId() );
        tvNomeAnimal.setText( ""+this.listaAnimais[position].getNome() );
        //tvDescricao.setText( ""+this.listaAnimais[position].getDescricao() );
        //tvSexo.setText( ""+this.listaAnimais[position].getSexo() );
        tvCidade.setText( ""+this.listaAnimais[position].getCidade() );

         return v;
    }
}
