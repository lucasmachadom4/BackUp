package br.com.pucgo.adote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.util.Formata;
import br.com.pucgo.adote.util.MaskEditUtil;
import br.com.pucgo.adote.util.Valida;

public class CadastrarAnimalFinalActivity extends Activity {

    private EditText edtValorDoacao, edtNumeroCartao, edtNomeTitular, edtDataValidade, edtCodigoCvv;
    private ImageButton btnVoltar;
    private Button btnFinalizar, btnDoacao;
    private LinearLayout layoutDoacao;
    private Spinner spinnerBandeira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_animal_final);

        inicializaVariaveis();
        voltar();
        exibeLayoutDoacao();
        finalizarCadastro();
    }

    private void inicializaVariaveis() {
        btnVoltar = findViewById(R.id.btnVoltar);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        btnDoacao = findViewById(R.id.btnDoacao);
        layoutDoacao = findViewById(R.id.layoutDoacao);
        edtValorDoacao = findViewById(R.id.edtValorDoacao);
        edtNomeTitular = findViewById(R.id.edtNomeTitular);
        edtNumeroCartao = findViewById(R.id.edtNumeroCartao);
        edtDataValidade = findViewById(R.id.edtDataValidade);
        edtCodigoCvv = findViewById(R.id.edtCodigoCvv);
        spinnerBandeira = findViewById(R.id.spinnerBandeira);


        //MASCARA PARA OS CAMPOS
        //edtNumeroCartao.addTextChangedListener(MaskEditUtil.mask(edtNumeroCartao, MaskEditUtil.FORMAT_CARTAO));
        edtDataValidade.addTextChangedListener(MaskEditUtil.mask(edtDataValidade, MaskEditUtil.FORMAT_DATE_CARTAO));
        layoutDoacao.setVisibility(View.GONE);
        spinnerBandeira.setSelection(0);
    }

    private void voltar() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarAnimalFinalActivity.this);
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

    private void exibeLayoutDoacao() {
        btnDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutDoacao.getVisibility() == View.VISIBLE) {
                    btnDoacao.setText("SIM");
                    layoutDoacao.setVisibility(View.GONE);
                } else {
                    btnDoacao.setText("NÂO");
                    layoutDoacao.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void fazerInclusaoAPI(String valorDoacao) {
        Intent i = getIntent();
        Intent animalTela = new Intent(CadastrarAnimalFinalActivity.this, AnimalActivity.class);

        String descricao = "-", comando;
        if (!i.getStringExtra("descricao").isEmpty()) {
            descricao = i.getStringExtra("descricao");
        }
        if (i.getStringExtra("id").equals("0")) {
            comando = "animalController/incluir/" + i.getStringExtra("nome") + "/"
                    + descricao + "/" + i.getStringExtra("sexo") + "/"
                    + i.getStringExtra("data") + "/" + i.getStringExtra("cidade") + "/"
                    + i.getStringExtra("imagem") + "/" + valorDoacao + "/"
                    + i.getStringExtra("idtipo") + "/" + i.getStringExtra("idUsuario");
            //animalTela.putExtra("id", idUltmaInclusao( i.getStringExtra("idUsuario") ) + "");
        } else {
            comando = "animalController/alterar/" + i.getStringExtra("id") + "/" + i.getStringExtra("nome") + "/"
                    + descricao + "/" + i.getStringExtra("sexo") + "/"
                    + i.getStringExtra("data") + "/" + i.getStringExtra("cidade") + "/"
                    + i.getStringExtra("imagem") + "/" + valorDoacao + "/"
                    + i.getStringExtra("idtipo");

            //animalTela.putExtra("id", i.getStringExtra("id") + "");
            AnimalActivity.getInstance().finish();
        }

        animalTela.putExtra("nome", i.getStringExtra("nome") + "");
        animalTela.putExtra("descricao", descricao + "");
        animalTela.putExtra("sexo", i.getStringExtra("sexo") + "");
        animalTela.putExtra("data", i.getStringExtra("data").replace("-", "/") + "");
        animalTela.putExtra("cidade", i.getStringExtra("cidade") + "");
        animalTela.putExtra("imagem", i.getStringExtra("imagem") + "");
        animalTela.putExtra("idTipo", i.getStringExtra("idtipo") + "");
        animalTela.putExtra("tipo", i.getStringExtra("tipo") + "");

        animalTela.putExtra("usuarioNome", i.getStringExtra("usuarioNome") + "");
        animalTela.putExtra("usuarioEmail", i.getStringExtra("usuarioEmail") + "");
        animalTela.putExtra("usuarioTelefone1", i.getStringExtra("usuarioTelefone1") + "");
        animalTela.putExtra("usuarioTelefone2", i.getStringExtra("usuarioTelefone2") + "");

        AsyncWS asyncWS = new AsyncWS(comando);
        try {
            if (asyncWS.execute().get().equals("true")) {
                Toast.makeText(CadastrarAnimalFinalActivity.this, "Salvo com Sucesso!", Toast.LENGTH_SHORT).show();
                CadastrarAnimalActivity.getInstance().finish();
                //startActivity(animalTela);
                finish();
            } else {
                asyncWS.cancel(true);
            }
        } catch (Exception e) {
            Log.e("ERRO Inclusao", e.getMessage());
            asyncWS.cancel(true);
        }
    }

//    private int idUltmaInclusao(String idUsuario){
//        AsyncWS asyncWS = new AsyncWS("consultar/ultimo/" + idUsuario);
//        try {
//            Animal animal = asyncWS.getTranslation(asyncWS.execute().get(), Animal.class);
//            return animal.getId();
//        } catch (Exception e) {
//            Log.e("ERRO Inclusao", e.getMessage());
//            asyncWS.cancel(true);
//            return 0;
//        }
//    }

    private void finalizarCadastro() {
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutDoacao.getVisibility() == View.VISIBLE) {
                    Valida valida = new Valida();
                    Formata formata = new Formata();
                    if (    valida.validaValor(edtValorDoacao.getText().toString())
                            && valida.validaCartao(edtNumeroCartao.getText().toString())
                            && valida.validaNome(edtNomeTitular.getText().toString())
                            && valida.validaSpinner(spinnerBandeira.getSelectedItemPosition())
                            && valida.validaDataValidade(edtDataValidade.getText().toString())
                            && valida.validaCodigoSeguraca(edtCodigoCvv.getText().toString())) {

                        fazerInclusaoAPI( formata.formataParaMoeda(edtValorDoacao.getText().toString()));
                    } else {
                        String msgErro = "";
                        if (!valida.validaValor(edtValorDoacao.getText().toString())) {
                            msgErro += "Valor da Doação Invalido!.\n";
                        }
                        if (!valida.validaCartao(edtNumeroCartao.getText().toString())) {
                            msgErro += "Número Cartão Invalido!.\n";
                        }
                        if (!valida.validaNome(edtNomeTitular.getText().toString())) {
                            msgErro += "Nome Titular Invalido!.\n";
                        }
                        if (!valida.validaSpinner(spinnerBandeira.getSelectedItemPosition())) {
                            msgErro += "Selecione uma Bandeira!.\n";
                        }
                        if (!valida.validaDataValidade(edtDataValidade.getText().toString())) {
                            msgErro += "Data de Valiade Invalida!.\n";
                        }
                        if (!valida.validaCodigoSeguraca(edtCodigoCvv.getText().toString())) {
                            msgErro += "CVV Invalido!.";
                        }
                        Toast.makeText(CadastrarAnimalFinalActivity.this, msgErro, Toast.LENGTH_LONG).show();
                    }
                } else {
                    fazerInclusaoAPI("0");
                }
            }
        });
    }

}
