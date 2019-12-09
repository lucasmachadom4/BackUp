package br.com.pucgo.adote.negocio;

import java.util.ArrayList;

import br.com.pucgo.adote.entidade.Tipo;
import br.com.pucgo.adote.persistencia.TipoDAO;
import br.com.pucgo.adote.util.Validar;

public class TipoService {

	private Validar validar = new Validar();
	
	public ArrayList<Tipo> consultarTipo() {
		TipoDAO tipoDAO = new TipoDAO();
		return tipoDAO.consultarTipos();
	}
	
	public Tipo consultarTipoId(int id) {
		TipoDAO tipoDAO = new TipoDAO();
		return tipoDAO.consultaTipoId(id);
	}
	
	public boolean incluirUsuario(Tipo tipo) {
		TipoDAO tipoDAO = new TipoDAO();
		
		if(validar.validaNome(tipo.getNome())) {
			ArrayList<Tipo> listaTipos = new ArrayList<Tipo>();	
			for(int i=0; i<listaTipos.size(); i++) {
				if( listaTipos.get(i).getId() != tipo.getId()  && listaTipos.get(i).getNome() == tipo.getNome()) {
					System.out.println("repetido");
					return false;//repitido no banco					
				}
			}			
			return tipoDAO.incluirTipo(tipo);
		}else {
			return false;
		}
	}
	
	public boolean alterarTipo(Tipo tipo) {		
		TipoDAO tipoDAO = new TipoDAO();
		ArrayList<Tipo> listaTipos = new ArrayList<Tipo>();	
		
		if(validar.validaNome(tipo.getNome())) {
			listaTipos = tipoDAO.consultarTipos();
			for(int i=0; i<listaTipos.size(); i++) {
				if( listaTipos.get(i).getId() != tipo.getId()  && listaTipos.get(i).getNome() == tipo.getNome()) {
					System.out.println("repetido");
					return false;//repitido no banco					
				}
			}	
			return tipoDAO.alterarTipo(tipo);
		}else {
			return false;
		}
	}
	
	
	public boolean excluirTipo(int id) {
		TipoDAO tipoDAO = new TipoDAO();		
		return tipoDAO.excluirTipo(id);
	}
	
	
}
