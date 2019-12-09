package br.com.pucgo.adote.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validar {
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
  public boolean validaEmail(String email){
      //^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$
      Pattern regra = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
      Matcher m = regra.matcher( email );
      if(m.matches() && email.length() >= 5){
          return true;
      }else{
          return false;
      }
  }
  /**
   * 
   * @param senha
   * @return Verdadeiro se passar na validação
   */
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
      if(telefone.length() >= 11){
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
	  dataNascimento = dataNascimento.replace("-", "/");
      Pattern regra = Pattern.compile("(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)");
      Matcher m = regra.matcher( dataNascimento );
      if(m.matches()){    	  
          Date dateAtual = new Date();          
          SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

          String dia = dataNascimento.substring(0,2);
          String mes = dataNascimento.substring(3,5);
          String ano = dataNascimento.substring(6);

          if( Integer.parseInt(ano) > Integer.parseInt(formatador.format(dateAtual).substring(6)) || Integer.parseInt(ano) < 1995 ){
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
  
  public boolean validaDoacao(double moeda) {
	  if(moeda > 0.0 ){
          return true;
      }else{
          return  false;
      }
  }
  
  public boolean validaSexo(String sexo) {
	  if(!sexo.isEmpty()) {
		  return true;
	  }else {
		  return false;
	  }
  }
  
  public String formataDataBanco(String data) {
	  String dia = data.substring(0,2);
      String mes = data.substring(3,5);
      String ano = data.substring(6);
      return ano + "-" + mes + "-" + dia ;
  }
}
