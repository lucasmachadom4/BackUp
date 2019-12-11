package br.com.pucgo.adote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import br.com.pucgo.adote.adapter.AdapterAnimais;
import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Animal;

public class PrincipalActivity extends Activity {

    private  static PrincipalActivity principalActivity;

    private ImageButton btnPerfil, btnCadastarAnimal, btnPesquisar, btnPesquisarIr;
    private EditText edtPesquisa;
    private LinearLayout layoutPesquisa;
    private ListView listViewAnimais;
    private AdapterAnimais adapterAnimais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializaVariaveis();
        layoutPesquisa();
        irPerfil();
        irCadastrarAnimal();
        listarAnimais();
    }

    private void inicializaVariaveis(){
        btnPesquisar = findViewById(R.id.btnPesquisar);
        btnCadastarAnimal = findViewById(R.id.btnCadastrarAnimal);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnPesquisarIr = findViewById(R.id.btnPesquisarIr);

        edtPesquisa = findViewById(R.id.edtPesquisa);
        layoutPesquisa = findViewById(R.id.layoutPesquisa);
        listViewAnimais = findViewById(R.id.listViewAnimais);

        principalActivity = this;
    }

    private void irCadastrarAnimal(){
        btnCadastarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PrincipalActivity.this, CadastrarAnimalActivity.class);
                startActivity(intent);
            }
        });
    }

    private void irPerfil(){
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PrincipalActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });
    }

    private void layoutPesquisa(){
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutPesquisa.getVisibility() == View.VISIBLE){
                    layoutPesquisa.setVisibility(View.GONE);
                    edtPesquisa.setText("");
                }else{
                    layoutPesquisa.setVisibility(View.VISIBLE);
                    pesquisarAnimal();
                }
            }
        });
    }

    private void pesquisarAnimal(){
        btnPesquisarIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncWS asyncWS = new AsyncWS("animalController/consultar/pesquisa/"+ edtPesquisa.getText().toString());
                try{
                    Animal[] animais = asyncWS.getTranslation(asyncWS.execute().get(), Animal[].class);
                    for (int i = 0; i < animais.length; i++) {
                        layoutAdapterAnimais(animais);
                    }
                }catch (Exception e){
                    Log.e("Erro ao consultar", e.getMessage());
                }
            }
        });
    }

    private void listarAnimais(){
        AsyncWS asyncWS = new AsyncWS("animalController/consultar/");
        try{
            Animal[] animais = asyncWS.getTranslation(asyncWS.execute().get(), Animal[].class);
            for (int i = 0; i < animais.length; i++) {
                layoutAdapterAnimais(animais);
            }
        }catch (Exception e){
            Log.e("Erro ao consultar", e.getMessage());
            asyncWS.cancel(true);
        }
    }

    private void layoutAdapterAnimais(Animal[] animais){
        //adapterAnimais.notifyDataSetChanged();
        adapterAnimais = new AdapterAnimais(PrincipalActivity.this, animais);
        listViewAnimais.setAdapter(adapterAnimais);
        listViewAnimais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Animal animalSelecionado = (Animal) adapterAnimais.getItem(position);
                Intent animalTela = new Intent(PrincipalActivity.this, AnimalActivity.class);
                animalTela.putExtra("id", animalSelecionado.getId() +"");
                animalTela.putExtra("nome", animalSelecionado.getNome() +"");
                animalTela.putExtra("descricao", animalSelecionado.getDescricao() +"");
                animalTela.putExtra("sexo", animalSelecionado.getSexo() +"");
                animalTela.putExtra("data", animalSelecionado.getDataNascimento() +"");
                animalTela.putExtra("cidade", animalSelecionado.getCidade() +"");
                animalTela.putExtra("imagem", animalSelecionado.getCaminhoFoto() +"");
                animalTela.putExtra("idTipo", animalSelecionado.getTipo().getId() +"");
                animalTela.putExtra("tipo", animalSelecionado.getTipo().getNome() +"");
                animalTela.putExtra("usuarioNome", animalSelecionado.getUsuario().getNome() +"");
                animalTela.putExtra("usuarioEmail", animalSelecionado.getUsuario().getEmail() +"");
                animalTela.putExtra("usuarioTelefone1", animalSelecionado.getUsuario().getTelefone1() +"");
                animalTela.putExtra("usuarioTelefone2", animalSelecionado.getUsuario().getTelefone2() +"");

                startActivity(animalTela);
            }
        });

    }

    public static PrincipalActivity getInstance(){
        return   principalActivity;
    }
}
