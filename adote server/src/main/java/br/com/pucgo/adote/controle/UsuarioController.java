package br.com.pucgo.adote.controle;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.negocio.UsuarioService;
import br.com.pucgo.adote.persistencia.UsuarioDAO;

@RestController
@RequestMapping("/usuarioController")
public class UsuarioController {

	private Usuario usuario = new Usuario();
	private UsuarioService usuarioService = new UsuarioService();	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String teste() {
		String result = "usuarioController ON";
		return result;
	}

	@RequestMapping(value = "/testeusuario.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Usuario testeJSON() {
		usuario.setId(0);
		usuario.setNome("teste");
		usuario.setEmail("teste@teste.teste");
		usuario.setSenha("senhaTeste");
		usuario.setTelefone1("1");
		usuario.setTelefone2("2");
		return usuario;
	}

	@RequestMapping(value = "/login/{email}/{senha}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean altenticaLogin(@PathVariable String email, @PathVariable String senha) {
		
		return usuarioService.altenticaLogin(email, senha);
	}

	@RequestMapping(value = "/incluir/{nome}/{email}/{senha}/{telefone1}/{telefone2}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean insereUsuarioJSON(@PathVariable String nome, @PathVariable String email,
			@PathVariable String senha, @PathVariable String telefone1, @PathVariable String telefone2) {		
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		usuario.setTelefone1(telefone1);
		usuario.setTelefone2(telefone2);	
		
		return usuarioService.incluirUsuario(usuario);

	}
	
	@RequestMapping(value = "/consultarinf/{email}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Usuario consultaEmailUsuario(@PathVariable String email) {		
		
		return usuarioService.consultarInfUsuario(email);		
	}	
	
	@RequestMapping(value = "/consultar/{id}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Usuario consultarUsuarioId(@PathVariable int id) {	
			
		return usuarioService.consultarUsuarioId(id);		
	}	

	@RequestMapping(value = "/alterar/{id}/{nome}/{email}/{senha}/{telefone1}/{telefone2}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean alterarUsuarioJSON(@PathVariable int id, @PathVariable String nome, @PathVariable String email,
			@PathVariable String senha, @PathVariable String telefone1, @PathVariable String telefone2) {		
		usuario.setId(id);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		usuario.setTelefone1(telefone1);
		usuario.setTelefone2(telefone2);		
		
		return usuarioService.alterarUsuario(usuario);
	}
	
	@RequestMapping(value = "/excluir/{id}.json", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody boolean excluirUsuarioJSON(@PathVariable int id) {
		
		return usuarioService.excluirUsuario(id);
	}

}
