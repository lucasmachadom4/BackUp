package br.com.pucgo.adote.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valida {

    /**
     *
     * @param nome
     * @return Verdadeiro se passar na validação
     */
    public boolean validaNome(String nome){
        // regex =  ^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$  ou ^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\s]+$
        Pattern regra = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]+$");
        Matcher m = regra.matcher( nome );
        if(m.matches() && nome.length() >= 3){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param Email
     * @return Verdadeiro se passar na validação
     */
    public boolean validaEmail(String Email){
        //^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$
        Pattern regra = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
        Matcher m = regra.matcher( Email );
        if(m.matches() && Email.length() >= 5){
            return true;
        }else{
            return false;
        }
    }

    public boolean validaSenha(String senha){
        if(senha.length() > 3){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param telefone
     * @return
     */
    public boolean validaTelefone(String telefone){
        if(telefone.length() >= 15){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param position
     * @return Verdadeiro se passar na validação
     */
    public boolean validaSpinner(int position){
        if(position !=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean validaSexo(boolean macho, boolean femea){
        if(macho == true|| femea == true ){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param dataNascimento
     * @return Verdadeiro se passar na validação
     */
    public boolean validaDataNascimento(String dataNascimento){
        // DD/MM/AAAA
        Pattern regra = Pattern.compile("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)");
        Matcher m = regra.matcher( dataNascimento );
        if(m.matches()){
            Date dateAtual = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

            String dia = dataNascimento.substring(0,2);
            String mes = dataNascimento.substring(3,5);
            String ano = dataNascimento.substring(6);

            if( Integer.parseInt(ano) > Integer.parseInt(formatador.format(dateAtual).substring(6)) || Integer.parseInt(ano) < 1980 ){
                return false;
            }else{
                if( Integer.parseInt(ano) == Integer.parseInt(formatador.format(dateAtual).substring(6)) &&
                        Integer.parseInt(mes) > Integer.parseInt(formatador.format(dateAtual).substring(3,5)) ){
                    return false;
                }else{
                    if(Integer.parseInt(ano) == Integer.parseInt(formatador.format(dateAtual).substring(6)) &&
                            Integer.parseInt(mes) == Integer.parseInt(formatador.format(dateAtual).substring(3,5))  &&
                            Integer.parseInt(dia) > Integer.parseInt(formatador.format(dateAtual).substring(0,2))){
                        return false;
                    }else{
                        return true;
                    }
                }
            }
        }else{
            return false;
        }
    }

    public boolean validaDataValidade(String dataValidade){
        // MM/AA
        Pattern regra = Pattern.compile("^(0[1-9]|1[012])/[12][0-9]|1{3}$");
        Matcher m = regra.matcher( dataValidade );
        if(m.matches()){
            DateFormat dateFormat = new SimpleDateFormat("MM/YY");
            Date dateAtual = new Date();
            String mes = dataValidade.substring(0,2);
            String ano = dataValidade.substring(3);
            if( Integer.parseInt(ano) < Integer.parseInt(dateFormat.format(dateAtual).substring(3))){
                return false;
            }else{
                if( Integer.parseInt(ano) == Integer.parseInt(dateFormat.format(dateAtual).substring(3)) &&
                        Integer.parseInt(mes) <= Integer.parseInt(dateFormat.format(dateAtual).substring(0,2)) ){
                    return false;
                }else{
                    return true;
                }
            }
        }else{
            return false;
        }
    }

    public boolean validaCartao(String numeroCartao){
        Pattern regra = Pattern.compile("^[0-9]{16,16}");
        Matcher m = regra.matcher( numeroCartao);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }
    public boolean validaCodigoSeguraca(String CodigoSeguracao){
        //CVV
        Pattern regra = Pattern.compile("[0-9]{3,3}$");
        Matcher m = regra.matcher( CodigoSeguracao );
        if(m.matches()){
            if(Integer.parseInt(CodigoSeguracao)<1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }
    //22 ou 22.2 ou 22,2
    public boolean validaValor(String valor){
        try{
            double moeda = Double.parseDouble(valor);
            if(moeda > 0.0 ){
                return true;
            }else{
                return  false;
            }
        }catch (Exception e){
            return false;
        }
    }


}
