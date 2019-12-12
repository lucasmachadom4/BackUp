package br.com.pucgo.adote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.persistencia.UsuarioAppDAOBD;

public class PerfilActivity extends Activity {

    private ImageButton btnVoltar, btnMeusAnimais;
    private Button btnEditar, btnSairConta, btnApagarConta;
    private TextView tvNomeExibido, tvEmailExibido, tvTelefone1Exibido, tvTelefone2Exibido, tvIdUsuario;
    private ImageView imvImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializaVariaveis();
        voltarTela();
        irMeusAnimais();
        preencheCamposUsuario();
        editarPerfil();
        desconectarConta();
        apagarConta();
    }

    private void inicializaVariaveis() {
        btnVoltar = findViewById(R.id.btnVoltar);
        btnEditar = findViewById(R.id.btnEditar);
        btnSairConta = findViewById(R.id.btnSairConta);
        btnApagarConta = findViewById(R.id.btnApagarConta);
        btnMeusAnimais = findViewById(R.id.btnMeusAnimais);

        tvIdUsuario = findViewById(R.id.tvIdUsuario);
        tvNomeExibido = findViewById(R.id.tvNomeExibido);
        tvEmailExibido = findViewById(R.id.tvEmailExibido);
        tvTelefone1Exibido = findViewById(R.id.tvTelefone1Exibido);
        tvTelefone2Exibido = findViewById(R.id.tvTelefone2Exibido);
        imvImagem = findViewById(R.id.imvImagem);
    }

    private void preencheCamposUsuario() {
        UsuarioAppDAOBD db = new UsuarioAppDAOBD(PerfilActivity.this);
        AsyncWS asyncWS = new AsyncWS("usuarioController/consultarinf/" + db.buscar().get(0).getEmail());
        try {
            Usuario usuario = asyncWS.getTranslation(asyncWS.execute().get(), Usuario.class);
            tvIdUsuario.setText(Integer.toString(usuario.getId()));
            tvNomeExibido.setText(usuario.getNome());
            tvEmailExibido.setText(usuario.getEmail());
            tvTelefone1Exibido.setText(usuario.getTelefone1());
            tvTelefone2Exibido.setText(usuario.getTelefone2());
        } catch (Exception e) {
            Log.e(" erro__", e.getMessage());
            asyncWS.cancel(true);
        }
    }

    private void voltarTela() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void irMeusAnimais(){
        btnMeusAnimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meusAnimais = new Intent(PerfilActivity.this, MeusAnimaisActivity.class);
                meusAnimais.putExtra("idUsuario", tvIdUsuario.getText().toString() );
                startActivity(meusAnimais);
            }
        });
    }

    private void editarPerfil() {
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editarPerfil = new Intent(PerfilActivity.this, EditarPerfilActivity.class);
                editarPerfil.putExtra("id", tvIdUsuario.getText().toString());
                editarPerfil.putExtra("nome", tvNomeExibido.getText().toString());
                editarPerfil.putExtra("email", tvEmailExibido.getText().toString());
                editarPerfil.putExtra("telefone1", tvTelefone1Exibido.getText().toString());
                editarPerfil.putExtra("telefone2", tvTelefone2Exibido.getText().toString());
                startActivity(editarPerfil);
            }
        });
    }

    private void desconectarConta() {
        btnSairConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
                builder.setTitle("DESCONECTAR?");
                builder.setMessage("Deseja desconectar a conta?");
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UsuarioAppDAOBD db = new UsuarioAppDAOBD(PerfilActivity.this);
                        db.deletar(db.buscar().get(0).getId());

                        PrincipalActivity.getInstance().finish();
                        Intent sairConta = new Intent(PerfilActivity.this, LoginActivity.class);
                        startActivity(sairConta);
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

    public void apagarConta(){
        btnApagarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
                builder.setTitle("APAGAR CONTA?");
                builder.setMessage("Deseja apagar a conta? Se fizer isso todos os dados do usuário e dos animais seram perdidos.");
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        AsyncWS asyncWS = new AsyncWS("usuarioController/excluir/" + tvIdUsuario.getText().toString() );
                        try {
                            if(asyncWS.execute().get().equals("true")){
                                UsuarioAppDAOBD db = new UsuarioAppDAOBD(PerfilActivity.this);
                                db.deletar(db.buscar().get(0).getId());
                                PrincipalActivity.getInstance().finish();
                                Intent sairConta = new Intent(PerfilActivity.this, LoginActivity.class);
                                startActivity(sairConta);
                                finish();
                            }else{
                                asyncWS.cancel(true);
                            }
                        }catch (Exception e){
                            asyncWS.cancel(true);
                        }
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

    @Override
    protected void onResume() {
        super.onResume();
        preencheCamposUsuario();
    }
}
