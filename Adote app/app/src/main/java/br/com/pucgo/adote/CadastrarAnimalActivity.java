package br.com.pucgo.adote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Tipo;
import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.persistencia.UsuarioAppDAOBD;
import br.com.pucgo.adote.util.MaskEditUtil;
import br.com.pucgo.adote.util.Valida;

public class CadastrarAnimalActivity extends Activity {

    private  static CadastrarAnimalActivity cadastrarAnimalActivity;

    private ImageButton btnVoltar, btnAddFoto;
    private Button btnProximo;
    private EditText edtNome, edtDescricao, edtDataNascimento, edtCidade;
    private Spinner spinnerTipo;
    private ImageView imvFoto;
    private RadioButton radioBtnMacho, radioBtnFemea;
    private TextView tvIdAnimal;
    private ArrayList<Tipo> listaTipos = new ArrayList<Tipo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastror_animal);

        inicializaVariaveis();
        voltar();
        carregaDadosSpinnerTipo();
        selecionarImagem();
        preencheCampos();
        irFinalizarCadastro();

    }

    private void inicializaVariaveis() {
        btnVoltar = findViewById(R.id.btnVoltar);
        btnProximo = findViewById(R.id.btnProximo);
        btnAddFoto = findViewById(R.id.btnAddFoto);
        edtNome = findViewById(R.id.edtNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        edtDataNascimento = findViewById(R.id.edtDataNascimento);
        edtCidade = findViewById(R.id.edtCidade);
        radioBtnMacho = findViewById(R.id.radioBtnMacho);
        radioBtnFemea = findViewById(R.id.radioBtnFemea);
        tvIdAnimal = findViewById(R.id.tvIdAnimal);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        imvFoto = findViewById(R.id.imvFoto);
        cadastrarAnimalActivity = this;

        //MASCARA PARA OS CAMPOS
        edtDataNascimento.addTextChangedListener(MaskEditUtil.mask(edtDataNascimento, MaskEditUtil.FORMAT_DATE));
    }

    private void voltar() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarAnimalActivity.this);
                builder.setTitle("CANCELAR EDIÇÂO?");
                builder.setMessage("Deseja sair?");
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("NÂO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
            }
        });
    }

    private void carregaDadosSpinnerTipo() {
        AsyncWS asyncWS = new AsyncWS("tipoController/consultar/");
        try {
            ArrayAdapter adapter = new ArrayAdapter(CadastrarAnimalActivity.this, android.R.layout.simple_spinner_dropdown_item);
            Tipo[] listaTipo = asyncWS.getTranslation(asyncWS.execute().get(), Tipo[].class);
            for (int i = 0; i < listaTipo.length; i++) {
                listaTipos.add(listaTipo[i]);
                adapter.add(listaTipos.get(i).getNome());
            }
            spinnerTipo.setAdapter(adapter);
        } catch (Exception e) {
            asyncWS.cancel(true);
            Log.e("ERRO tipo", e.getMessage());
        }
    }

    private void preencheCampos(){
        Intent intent = getIntent();
        if(intent.getStringExtra("id") != null){
            //Log.e("ID animal putExtra",  i.getStringExtra("id")+"");
            tvIdAnimal.setText(intent.getStringExtra("id"));
            edtNome.setText(intent.getStringExtra("nome"));
            edtDescricao.setText(intent.getStringExtra("descricao"));
            edtDataNascimento.setText(intent.getStringExtra("data"));
            edtCidade.setText( intent.getStringExtra("cidade") );

            if(intent.getStringExtra("sexo").equals("Macho")){
                radioBtnMacho.setChecked(true);
            }else{
                radioBtnFemea.setChecked(true);
            }
            for (int i= 0; i < listaTipos.size(); i++) {
                if( listaTipos.get(i).getNome().equals(intent.getStringExtra("tipo")) ){
                    spinnerTipo.setSelection(i);
                }
            }
        }
    }

    private void irFinalizarCadastro() {
        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Valida valida = new Valida();
                if (valida.validaNome(edtNome.getText().toString()) &&
                        valida.validaDataNascimento(edtDataNascimento.getText().toString()) &&
                        valida.validaNome(edtCidade.getText().toString()) &&
                        valida.validaSpinner(spinnerTipo.getSelectedItemPosition() ) &&
                        radioBtnMacho.isChecked() || radioBtnFemea.isChecked()) {
                    String sexo;
                    if (radioBtnMacho.isChecked()) {
                        sexo = radioBtnMacho.getText().toString();
                    } else {
                        sexo = radioBtnFemea.getText().toString();
                    }

                    Intent intent = new Intent(CadastrarAnimalActivity.this, CadastrarAnimalFinalActivity.class);
                    intent.putExtra("id", tvIdAnimal.getText().toString() );
                    intent.putExtra("nome", edtNome.getText().toString());
                    intent.putExtra("descricao", edtDescricao.getText().toString());
                    intent.putExtra("sexo", sexo);
                    intent.putExtra("data", edtDataNascimento.getText().toString().replace("/", "-"));
                    intent.putExtra("cidade", edtCidade.getText().toString());
                    intent.putExtra("imagem", "http://192.168.1.58/imagem/".replace("/", "-"));
                    int id = 0;
                    for(int i=0; i<listaTipos.size(); i++){
                       if( listaTipos.get(i).getNome().equals( spinnerTipo.getSelectedItem().toString() ) ) {
                           id = listaTipos.get(i).getId();
                       }
                    }
                    intent.putExtra("idtipo", Integer.toString( id ));
                    intent.putExtra("tipo", spinnerTipo.getSelectedItem().toString() );
                    intent.putExtra("idusuario", consultaUsuario() );
                    Intent intentUsuario = getIntent();
                    intent.putExtra("usuarioNome",  intentUsuario.getStringExtra("usuarioNome")+"");
                    intent.putExtra("usuarioEmail",  intentUsuario.getStringExtra("usuarioEmail")+"");
                    intent.putExtra("usuarioTelefone1",  intentUsuario.getStringExtra("usuarioTelefone1")+"");
                    intent.putExtra("usuarioTelefone2", intentUsuario.getStringExtra("usuarioTelefone2") +"");
                    startActivity(intent);
                } else {
                    Toast.makeText(CadastrarAnimalActivity.this, "Dados INVALIDO!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String consultaUsuario(){
        UsuarioAppDAOBD db = new UsuarioAppDAOBD(CadastrarAnimalActivity.this);
        AsyncWS asyncWS = new AsyncWS("usuarioController/consultarinf/"+ db.buscar().get(0).getEmail());
        try{
            Usuario usuario = asyncWS.getTranslation(asyncWS.execute().get(), Usuario.class);
            return Integer.toString(usuario.getId())  ;
        }catch (Exception e){
            asyncWS.cancel(true);
            return null;
        }
    }

    private void selecionarImagem() {
        btnAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPegaFoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intentPegaFoto, "Selecione uma imagem"), 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                Uri imagemSelecionada = data.getData();
                //File imgPerfil = new  File("/sdcard/Images/test_image.jpg");
                imvFoto.setImageURI(imagemSelecionada);
            }
        }
    }

    public static CadastrarAnimalActivity getInstance(){
        return   cadastrarAnimalActivity;
    }
}
