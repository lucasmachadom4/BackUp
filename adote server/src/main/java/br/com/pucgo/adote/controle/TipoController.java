package br.com.pucgo.adote.controle;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucgo.adote.entidade.Tipo;
import br.com.pucgo.adote.negocio.TipoService;
@RestController
@RequestMapping("/tipoController")
public class TipoController {
	
	private Tipo tipo = new Tipo();
	private TipoService tipoService = new TipoService();
	
	@RequestMapping(value = "/consultar/.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<Tipo> consultarTiposJSON() {				
		return tipoService.consultarTipo();
	}
	
	@RequestMapping(value = "/consultar/{id}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Tipo consultarTipoIdJSON(@PathVariable int id) {				
		return tipoService.consultarTipoId(id);
	}
	
	@RequestMapping(value = "/incluir/{nome}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean incluirTiposJSON(@PathVariable String nome) {		
		tipo.setNome(nome);			
		return tipoService.incluirUsuario(tipo);
	}
	
	@RequestMapping(value = "/alterar/{id}/{nome}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean alterarTiposJSON(@PathVariable int id, @PathVariable String nome) {			
		tipo.setId(id);
		tipo.setNome(nome);
		return tipoService.alterarTipo(tipo);
	}
	
	@RequestMapping(value = "/excluir/{id}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean excluirTipoJSON(@PathVariable String id) {			
		return tipoService.excluirTipo(Integer.parseInt(id));
	}

}
