package br.com.pucgo.adote.entidade;

public class Tipo {

    private int id;
    private String nome;

    public Tipo(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Tipo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
