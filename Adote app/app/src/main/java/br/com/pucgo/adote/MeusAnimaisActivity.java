package br.com.pucgo.adote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import br.com.pucgo.adote.adapter.AdapterAnimais;
import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.util.Formata;

public class MeusAnimaisActivity extends Activity {

    private  static MeusAnimaisActivity meusAnimaisActivity;

    private ImageButton btnVoltar, btnCadastarAnimal;
    private ListView listViewAnimais;
    private AdapterAnimais adapterAnimais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_animais);

        inicializaVariaveis();
        voltarTela();
        irCadastrarAnimal();
        listarAnimais();
    }

    private void inicializaVariaveis(){
        btnVoltar = findViewById(R.id.btnVoltar);
        btnCadastarAnimal = findViewById(R.id.btnCadastrarAnimal);
        listViewAnimais = findViewById(R.id.listViewAnimais);
    }

    private void voltarTela() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void irCadastrarAnimal(){
        btnCadastarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MeusAnimaisActivity.this, CadastrarAnimalActivity.class);
                startActivity(intent);
            }
        });
    }

    private void listarAnimais(){
        Intent idUsuario = getIntent();
        AsyncWS asyncWS = new AsyncWS("animalController/consultar/usuario/"+ idUsuario.getStringExtra("idUsuario") );
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
        adapterAnimais = new AdapterAnimais(MeusAnimaisActivity.this, animais);
        listViewAnimais.setAdapter(adapterAnimais);
        listViewAnimais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Formata formata = new Formata();
                Animal animalSelecionado = (Animal) adapterAnimais.getItem(position);
                Intent animalTela = new Intent(MeusAnimaisActivity.this, AnimalActivity.class);
                animalTela.putExtra("id", animalSelecionado.getId() +"");
                animalTela.putExtra("nome", animalSelecionado.getNome() +"");
                animalTela.putExtra("descricao", animalSelecionado.getDescricao() +"");
                animalTela.putExtra("sexo", animalSelecionado.getSexo() +"");
                animalTela.putExtra("data",  formata.formataDataBanco( animalSelecionado.getDataNascimento() )+"");
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

    @Override
    protected void onResume() {
        super.onResume();
        listarAnimais();
    }
}
