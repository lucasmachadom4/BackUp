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
        finalizarCastro();
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

    private void fazerInclusao(String valorDoacao) {
        Intent i = getIntent();
        String descricao = "-";
        if(!i.getStringExtra("descricao").isEmpty()){
            descricao = i.getStringExtra("descricao");
        }
        AsyncWS asyncWS = new AsyncWS("animalController/incluir/"+ i.getStringExtra("nome") + "/"
                + descricao + "/" + i.getStringExtra("sexo") + "/"
                + i.getStringExtra("data") + "/" + i.getStringExtra("cidade") + "/"
                + i.getStringExtra("imagem") + "/" + valorDoacao + "/"
                + i.getStringExtra("idtipo") + "/" + i.getStringExtra("idusuario") );
        try{
            if( asyncWS.execute().get().equals("true") ){
                CadastrarAnimalActivity.getInstance().finish();
                Toast.makeText(CadastrarAnimalFinalActivity.this, "Cadastro criada com Sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }catch (Exception e){
            Log.e("ERRO Inclusao", e.getMessage());
        }
    }


    private void finalizarCastro() {
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutDoacao.getVisibility() == View.VISIBLE ){
                    Valida valida = new Valida();
                    Log.e("If", "visivel");
                    if (valida.validaValor(edtValorDoacao.getText().toString())
                            && valida.validaCartao(edtNumeroCartao.getText().toString())
                            && valida.validaNome(edtNomeTitular.getText().toString())
                            && valida.validaSpinner(spinnerBandeira.getSelectedItemPosition())
                            && valida.validaDataValidade(edtDataValidade.getText().toString())
                            && valida.validaCodigoSeguraca(edtCodigoCvv.getText().toString())) {
                        Log.e("Entrou", "validação");
                        fazerInclusao( valida.formataParaMoeda( edtValorDoacao.getText().toString()));
                    } else {
                        Toast.makeText(CadastrarAnimalFinalActivity.this, "Dados do cartão invalidos!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.e("Else", "gone");
                    fazerInclusao("0");
                }
            }
        });
    }

}
