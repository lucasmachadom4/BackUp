package br.com.pucgo.adote.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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

//    public void removerAnimal(int position){
//        this.listaAnimais.remove(position);
//        notifyDataSetChanged();
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Valida valida = new Valida();
        View v = View.inflate(this.context, R.layout.adapter_layouy_animal , null);

        //Variaveis da View a ser preenchida
        ImageView imvImagem = v.findViewById(R.id.imvImagem);
        TextView tvIdAnimal = v.findViewById(R.id.tvIdAnimal);
        TextView tvNomeAnimal = v.findViewById(R.id.tvNomeAnimal);
        TextView tvDescricao = v.findViewById(R.id.tvDescricao);
        TextView tvSexo = v.findViewById(R.id.tvSexo);
        TextView tvCidade = v.findViewById(R.id.tvCidade);
        TextView tvDataNascimento = v.findViewById(R.id.tvDataNascimento);
        TextView tvTipoId = v.findViewById(R.id.tvTipoId);
        TextView tvTipoNome = v.findViewById(R.id.tvTipoNome);
        TextView tvUsuarioNome = v.findViewById(R.id.tvUsuarioNome);
        TextView tvUsuarioEmail = v.findViewById(R.id.tvUsuarioEmail);
        TextView tvUsuarioTelefone1 = v.findViewById(R.id.tvUsuarioTelefone1);
        TextView tvUsuarioTelefone2 = v.findViewById(R.id.tvUsuarioTelefone2);

//        Log.e("getView", ""+this.listaAnimais.get(position).getId());
//        Log.e("getView", ""+this.listaAnimais.get(position).getNome());
//        Log.e("getView", ""+this.listaAnimais.get(position).getDescricao());
//        Log.e("getView", ""+this.listaAnimais.get(position).getSexo());
//        Log.e("getView", ""+this.listaAnimais.get(position).getCidade());
//        Log.e("getView", ""+valida.formataDataBanco( this.listaAnimais.get(position).getDataNascimento() ));
//        Log.e("getView", ""+this.listaAnimais.get(position).getTipo().getId());
//        Log.e("getView", ""+this.listaAnimais.get(position).getTipo().getNome());
//        Log.e("getView", ""+this.listaAnimais.get(position).getUsuario().getNome());
//        Log.e("getView", ""+this.listaAnimais.get(position).getUsuario().getEmail());
//        Log.e("getView", ""+this.listaAnimais.get(position).getUsuario().getTelefone1());
//        Log.e("getView", ""+this.listaAnimais.get(position).getUsuario().getTelefone2());

        //imvImagem.setImageURI();
        tvIdAnimal.setText( ""+ this.listaAnimais[position].getId() );
        tvNomeAnimal.setText( ""+this.listaAnimais[position].getNome() );
        tvDescricao.setText( ""+this.listaAnimais[position].getDescricao() );
        tvSexo.setText( ""+this.listaAnimais[position].getSexo() );
        tvCidade.setText( ""+this.listaAnimais[position].getCidade() );
        tvDataNascimento.setText( ""+valida.formataDataBanco( this.listaAnimais[position].getDataNascimento() )  );
        tvTipoId.setText( ""+this.listaAnimais[position].getTipo().getId() );
        tvTipoNome.setText( ""+this.listaAnimais[position].getTipo().getNome() );
        tvUsuarioNome.setText( ""+this.listaAnimais[position].getUsuario().getNome() );
        tvUsuarioEmail.setText( ""+this.listaAnimais[position].getUsuario().getEmail() );
        tvUsuarioTelefone1.setText( ""+this.listaAnimais[position].getUsuario().getTelefone1() );
        tvUsuarioTelefone2.setText( ""+this.listaAnimais[position].getUsuario().getTelefone2() );


        return v;


    }
}
