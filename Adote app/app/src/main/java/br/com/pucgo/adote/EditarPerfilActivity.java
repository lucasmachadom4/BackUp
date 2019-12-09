package br.com.pucgo.adote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.persistencia.UsuarioAppDAOBD;
import br.com.pucgo.adote.util.MaskEditUtil;
import br.com.pucgo.adote.util.Valida;

public class EditarPerfilActivity extends Activity {

    private ImageButton btnAddFoto;
    private Button btnSalvar, btnCancelar;
    private ImageView imvImagem;
    private EditText edtNome, edtEmail, edtSenha, edtTelefone1, edtTelefone2;
    private TextView tvIdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        inicializaVariaveis();
        preencheCampos();
        selecionarImagem();
        salvaUsuario();
        cancelarEdicao();
    }

    private void inicializaVariaveis() {
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnAddFoto = findViewById(R.id.btnAddFoto);
        tvIdUsuario = findViewById(R.id.tvIdUsuario);
        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtTelefone1 = findViewById(R.id.edtTelefone1);
        edtTelefone2 = findViewById(R.id.edtTelefone2);

        imvImagem = findViewById(R.id.imvImagem);
        //mascara de campo telefonico
        edtTelefone1.addTextChangedListener(MaskEditUtil.mask(edtTelefone1, MaskEditUtil.FORMAT_FONE));
        edtTelefone2.addTextChangedListener(MaskEditUtil.mask(edtTelefone2, MaskEditUtil.FORMAT_FONE));

    }

    private void preencheCampos() {
        Intent i = getIntent();
        if(i.getStringExtra("id") != null){
            tvIdUsuario.setText(i.getStringExtra("id"));
            if(!tvIdUsuario.getText().equals("0") ){
                edtNome.setText(i.getStringExtra("nome"));
                edtEmail.setText(i.getStringExtra("email"));
                edtTelefone1.setText(i.getStringExtra("telefone1"));
                edtTelefone2.setText(i.getStringExtra("telefone2"));
            }
        }
    }

    private void salvaUsuario() {
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Valida valida = new Valida();

                if (valida.validaNome(edtNome.getText().toString()) &&
                        valida.validaEmail(edtEmail.getText().toString()) &&
                        valida.validaSenha(edtSenha.getText().toString()) &&
                        valida.validaTelefone(edtTelefone1.getText().toString())) {

                    String variaveisUsuario = edtNome.getText().toString() + "/" + edtEmail.getText().toString() +
                            "/" + edtSenha.getText().toString() + "/" + edtTelefone1.getText().toString() +
                            "/" + edtTelefone2.getText().toString() ;

                    if ( tvIdUsuario.getText().equals("0") ) {
                        AsyncWS asyncWS = new AsyncWS("usuarioController/incluir/" + variaveisUsuario);
                        try {
                            if (asyncWS.execute().get().equals("true")) {
                                Toast.makeText(EditarPerfilActivity.this, "Conta criada com Sucesso!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (Exception e) {
                            Log.e("Erro Incluir", e.getMessage());
                        }
                    }else {
                        UsuarioAppDAOBD db = new UsuarioAppDAOBD(EditarPerfilActivity.this);
                        Usuario usuario = new Usuario();
                        usuario.setId( db.buscar().get(0).getId() );
                        usuario.setEmail(edtEmail.getText().toString());
                        usuario.setSenha(edtSenha.getText().toString());
                        db.atualizar(usuario);

                        AsyncWS asyncWS = new AsyncWS("usuarioController/alterar/" + tvIdUsuario.getText().toString() + "/" + variaveisUsuario);
                        try {
                            if (asyncWS.execute().get().equals("true")) {
                                Toast.makeText(EditarPerfilActivity.this, "Conta Atualziada com Sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intentPerfil = new Intent(EditarPerfilActivity.this , PerfilActivity.class);
                                startActivity(intentPerfil);
                                finish();
                            }
                        } catch (Exception e) {
                            Log.e("Erro ALTERAR", e.getMessage());
                        }
                    }
                } else {
                    Toast.makeText(EditarPerfilActivity.this, "Dados INVALIDO!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void cancelarEdicao() {
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPerfilActivity.this);
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
                imvImagem.setImageURI(imagemSelecionada);
            }
        }
    }
}
