package br.com.pucgo.adote.util;

public class Formata {

    public String formataParaMoeda(String valor){
        try{
            double moeda = Double.parseDouble(valor);
            return  String.format("%.2f", moeda).replace(",",".");
        }catch (Exception e){
            return null;
        }
    }

    public String formataDataBanco(String data) {
        String dia = data.substring(8);
        String mes = data.substring(5,7);
        String ano = data.substring(0,4);
        return dia + "/" + mes + "/" + ano ;
    }
}
