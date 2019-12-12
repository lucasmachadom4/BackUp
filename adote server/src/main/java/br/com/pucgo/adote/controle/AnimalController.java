package br.com.pucgo.adote.controle;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucgo.adote.entidade.Animal;
import br.com.pucgo.adote.entidade.Tipo;
import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.negocio.AnimalService;

@RestController
@RequestMapping("/animalController")
public class AnimalController {
	
	private AnimalService animalService = new AnimalService();	

	@RequestMapping(value = "/consultar/ultimo/{idUsuario}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Animal consultaEmailUsuario(@PathVariable int idUsuario) {
		return animalService.consultarUltimoAnimalUsuario(idUsuario);
	}

	@RequestMapping(value = "/consultar/{id}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Animal consultarAnimalId(@PathVariable int id) {

		return animalService.consultarAnimalId(id);
	}
	@RequestMapping(value = "/consultar/usuario/{id}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<Animal> consultarAnimaisUsuario(@PathVariable int id) {

		return animalService.consultarAnimaisUsuario(id);
	}
	
	@RequestMapping(value = "/consultar/pesquisa/{pesquisa}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ArrayList<Animal> consultarAnimaisPor(@PathVariable String pesquisa) {

		return animalService.consultarAnimaisPor(pesquisa);
	}

	@RequestMapping(value = "/incluir/{nome}/{descricao}/{sexo}/{dataNascimento}/{cidade}/{caminhoFoto}/{valorDoacao}/{idTipo}/{idUsuario}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean inserirAnimalJSON(@PathVariable String nome, @PathVariable String descricao,
			@PathVariable String sexo, @PathVariable String dataNascimento, @PathVariable String cidade,
			@PathVariable String caminhoFoto, @PathVariable double valorDoacao, @PathVariable int idTipo, @PathVariable int idUsuario) {		
		
		

		return animalService.incluirAnimal(nome, descricao, sexo, dataNascimento, cidade, caminhoFoto, valorDoacao, idTipo, idUsuario);
		
	}
	
	@RequestMapping(value = "/alterar/{id}/{nome}/{descricao}/{sexo}/{dataNascimento}/{cidade}/{caminhoFoto}/{valorDoacao}/{idTipo}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean alterarAnimalJSON(@PathVariable int id, @PathVariable String nome, @PathVariable String descricao,
			@PathVariable String sexo, @PathVariable String dataNascimento, @PathVariable String cidade,
			@PathVariable String caminhoFoto, @PathVariable double valorDoacao, @PathVariable int idTipo) {		
		
		return animalService.alterarAnimal(id, nome, descricao, sexo, dataNascimento, cidade, caminhoFoto, valorDoacao, idTipo);
		
	}
	
	@RequestMapping(value = "/excluir/{id}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean excluirAnimal(@PathVariable String id) {
		return animalService.excluirAnimal(Integer.parseInt(id));
	}
	
}
