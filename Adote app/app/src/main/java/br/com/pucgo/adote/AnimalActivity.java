package br.com.pucgo.adote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.pucgo.adote.conexao.AsyncWS;
import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.persistencia.UsuarioAppDAOBD;
import br.com.pucgo.adote.util.Formata;

public class AnimalActivity extends Activity {

    private static AnimalActivity animalActivity;

    private TextView tvIdAnimal, tvNomeAnimal, tvDescricao, tvSexo, tvCidade, tvDataNascimento;
    private TextView tvTipoId, tvTipoNome, tvUsuarioNome, tvUsuarioEmail, tvUsuarioTelefone1, tvUsuarioTelefone2;
    private TextView tvEditar, tvExcluir;
    private ImageView imvImagem;
    private ImageButton btnVoltar, btnEditar, btnExcluir;
    private ImageButton btnMostarResponsavel;
    private LinearLayout layoutResponsavel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        inicializaVariaveis();
        preencheCampos();
        voltarTela();
        editarAnimal();
        excluirAnimal();
        layoutAdotar();

    }

    private void inicializaVariaveis() {
        btnVoltar = findViewById(R.id.btnVoltar);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);
        btnMostarResponsavel = findViewById(R.id.btnMostarResponsavel);
        layoutResponsavel = findViewById(R.id.layoutResponsavel);

        imvImagem = findViewById(R.id.imvImagem);
        tvIdAnimal = findViewById(R.id.tvIdAnimal);
        tvNomeAnimal = findViewById(R.id.tvNomeAnimal);
        tvDescricao = findViewById(R.id.tvDescricao);
        tvSexo = findViewById(R.id.tvSexo);
        tvCidade = findViewById(R.id.tvCidade);
        tvDataNascimento = findViewById(R.id.tvDataNascimento);
        tvTipoId = findViewById(R.id.tvTipoId);
        tvTipoNome = findViewById(R.id.tvTipoNome);
        tvUsuarioNome = findViewById(R.id.tvUsuarioNome);
        tvUsuarioEmail = findViewById(R.id.tvUsuarioEmail);
        tvUsuarioTelefone1 = findViewById(R.id.tvUsuarioTelefone1);
        tvUsuarioTelefone2 = findViewById(R.id.tvUsuarioTelefone2);

        tvEditar = findViewById(R.id.tvEditar);
        tvExcluir = findViewById(R.id.tvExcluir);
        animalActivity = AnimalActivity.this;
    }

    public void preencheCampos() {
        Intent i = getIntent();
//        Animal animal = new Animal();
//        AsyncWS asyncWS = new AsyncWS("animalController/consultar/" + i.getStringExtra("id"));
//        try {
//            animal = asyncWS.getTranslation(asyncWS.execute().get(), Animal.class);
//        } catch (Exception e) {
//            asyncWS.cancel(true);
//        }
//        Formata formata = new Formata();
//        tvIdAnimal.setText( animal.getId() +"");
//        tvNomeAnimal.setText(animal.getNome() +"");
//        tvDescricao.setText(animal.getDescricao() + "");
//        tvSexo.setText(animal.getSexo() +"");
//        tvCidade.setText(animal.getCidade() +"");
//        tvDataNascimento.setText( formata.formataDataBanco( animal.getDataNascimento() ) +"");
//        tvTipoId.setText(animal.getTipo().getId() +"");
//        tvTipoNome.setText(animal.getTipo().getNome() +"");
//        tvUsuarioNome.setText(animal.getUsuario().getNome() +"");
//        tvUsuarioEmail.setText(animal.getUsuario().getEmail() +"");
//        tvUsuarioTelefone1.setText(animal.getUsuario().getTelefone1() +"");
//        tvUsuarioTelefone2.setText(animal.getUsuario().getTelefone2() +"");

        tvIdAnimal.setText("" + i.getStringExtra("id"));
        tvNomeAnimal.setText("" + i.getStringExtra("nome"));
        tvSexo.setText("" + i.getStringExtra("sexo"));
        tvCidade.setText("" + i.getStringExtra("cidade"));
        tvDataNascimento.setText("" + i.getStringExtra("data"));
        tvTipoId.setText("" + i.getStringExtra("idtipo"));
        tvTipoNome.setText("" + i.getStringExtra("tipo"));
        tvUsuarioNome.setText("" + i.getStringExtra("usuarioNome"));
        tvUsuarioEmail.setText("" + i.getStringExtra("usuarioEmail"));
        tvUsuarioTelefone1.setText("" + i.getStringExtra("usuarioTelefone1"));

        if (i.getStringExtra("descricao").equals("null") || i.getStringExtra("descricao").isEmpty()) {
            tvDescricao.setText("");
        }
        else {
            tvDescricao.setText(i.getStringExtra("descricao"));
        }
        if (i.getStringExtra("usuarioTelefone2").equals("null") || i.getStringExtra("usuarioTelefone2").isEmpty()) {
            tvUsuarioTelefone2.setText("");
        }
        else {
            tvUsuarioTelefone2.setText(i.getStringExtra("usuarioTelefone2"));
        }
        visibilidadeBotoes();
    }

    private void voltarTela() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void visibilidadeBotoes() {
        UsuarioAppDAOBD db = new UsuarioAppDAOBD(AnimalActivity.this);
        String emailDB = db.buscar().get(0).getEmail();
        if (emailDB.equals(tvUsuarioEmail.getText().toString())) {
            btnEditar.setVisibility(View.VISIBLE);
            btnExcluir.setVisibility(View.VISIBLE);
            tvExcluir.setVisibility(View.VISIBLE);
            tvEditar.setVisibility(View.VISIBLE);
        } else {
            btnEditar.setVisibility(View.INVISIBLE);
            btnExcluir.setVisibility(View.INVISIBLE);
            tvExcluir.setVisibility(View.INVISIBLE);
            tvEditar.setVisibility(View.INVISIBLE);
        }
    }

    private void editarAnimal() {
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroAnimal = new Intent(AnimalActivity.this, CadastrarAnimalActivity.class);
                Log.e( "Editar animal ID__", tvIdAnimal.getText().toString());
                cadastroAnimal.putExtra("id", tvIdAnimal.getText().toString());
                cadastroAnimal.putExtra("nome", tvNomeAnimal.getText().toString());
                cadastroAnimal.putExtra("descricao", tvDescricao.getText().toString());
                cadastroAnimal.putExtra("sexo", tvSexo.getText().toString());
                cadastroAnimal.putExtra("data", tvDataNascimento.getText().toString());
                cadastroAnimal.putExtra("cidade", tvCidade.getText().toString());
                //cadastroAnimal.putExtra("imagem", imvImagem.getText().toString());
                cadastroAnimal.putExtra("idTipo", tvTipoId.getText().toString());
                cadastroAnimal.putExtra("tipo", tvTipoNome.getText().toString());
                cadastroAnimal.putExtra("usuarioNome", tvUsuarioNome.getText().toString() + "");
                cadastroAnimal.putExtra("usuarioEmail", tvUsuarioEmail.getText().toString() + "");
                cadastroAnimal.putExtra("usuarioTelefone1", tvUsuarioTelefone1.getText().toString() + "");
                cadastroAnimal.putExtra("usuarioTelefone2", tvUsuarioTelefone2.getText().toString() + "");
                startActivity(cadastroAnimal);
            }
        });

    }

    private void excluirAnimal() {
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AnimalActivity.this);
                builder.setTitle("APAGAR ANIMAL?");
                builder.setMessage("Deseja apagar? Se fizer isso a operação não podera ser desfeita.");
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e( "Apagar animal ID__", tvIdAnimal.getText().toString());
                        AsyncWS asyncWS = new AsyncWS("animalController/excluir/" + tvIdAnimal.getText().toString());
                        try {
                            if (asyncWS.execute().get().equals("true")) {
                                finish();
                            } else {
                                asyncWS.cancel(true);
                            }
                        } catch (Exception e) {
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

    public void layoutAdotar() {
        btnMostarResponsavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutResponsavel.getVisibility() == View.VISIBLE) {
                    layoutResponsavel.setVisibility(View.GONE);
                } else {
                    layoutResponsavel.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static AnimalActivity getInstance() {
        return animalActivity;
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencheCampos();
    }
}
