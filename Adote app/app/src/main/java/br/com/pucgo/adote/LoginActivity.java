package br.com.pucgo.adote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.persistencia.UsuarioAppDAOBD;
import br.com.pucgo.adote.util.Valida;

public class LoginActivity extends Activity {

    private Button btnEntrar, btnCriarConta;
    private EditText edtLogin, edtSenha;
    private UsuarioAppDAOBD bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializaVariaveis();
        fazerLogin();
        fazerCadastro();
    }

    private void inicializaVariaveis() {
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCriarConta = findViewById(R.id.btnCriarConta);
        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);


        bd = new UsuarioAppDAOBD(LoginActivity.this);
    }

    private void irMenuPrincipal(){
        Intent intentTelaPrincipal = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(intentTelaPrincipal);
        finish();
    }

    private void fazerLogin() {
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                Valida valida = new Valida();
                if( valida.validaEmail(edtLogin.getText().toString()) ){
                    try {
                        AsyncWS asyncWS = new AsyncWS( "usuarioController/login/" + edtLogin.getText().toString()+ "/" + edtSenha.getText().toString());
                        if( asyncWS.execute().get().equals("true") ){
                            Usuario usuario = new Usuario();
                            usuario.setEmail(edtLogin.getText().toString());
                            usuario.setSenha( edtSenha.getText().toString() );
                            bd.inserir(usuario);

                            irMenuPrincipal();
                        }else{
                            Toast.makeText(LoginActivity.this, "Email ou senha incorreto!", Toast.LENGTH_SHORT).show();
                            asyncWS.cancel(true);
                        }
                    }catch (Exception e){
                        Log.e("Erro asyncWS", e.getMessage());
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Email INVALIDO!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fazerCadastro() {
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCadastro = new Intent(LoginActivity.this, EditarPerfilActivity.class);
                startActivity(intentCadastro);
            }
        });
    }

}
