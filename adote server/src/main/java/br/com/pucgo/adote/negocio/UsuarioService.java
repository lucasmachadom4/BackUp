package br.com.pucgo.adote.negocio;

import br.com.pucgo.adote.entidade.Usuario;
import br.com.pucgo.adote.persistencia.UsuarioDAO;
import br.com.pucgo.adote.util.Validar;

public class UsuarioService {

	private Usuario usuario = new Usuario();
	private Validar validar = new Validar();

	public boolean altenticaLogin(String email, String senha) {

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuario = usuarioDAO.altenticaUsuario(email);		

		if (validar.validaEmail(email) && usuario.getEmail() != null) {
			if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
				return true;// email e senha iguas ao do banco
			} else {
				return false;// senha errada
			}
		} else {
			return false;// Validação barrou, ou não encontrou o email
		}
	}
	
	public Usuario consultarInfUsuario(String email) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (validar.validaEmail(email)) {
			usuario = usuarioDAO.consultarInfUsuario(email);
		}
		return usuario;
	}
	
	public Usuario consultarUsuarioId(int id) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();		
		return usuarioDAO.consultarUsuarioId(id);
	}

	public boolean incluirUsuario(Usuario usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		this.usuario = usuarioDAO.consultarInfUsuario(usuario.getEmail());///MUDAR PARA CONSULTAinf

		if (this.usuario.getEmail() == null) {
			if (validar.validaNome(usuario.getNome()) && validar.validaEmail(usuario.getEmail())
					&& validar.validaSenha(usuario.getSenha()) && validar.validaTelefone(usuario.getTelefone1())) {

				return usuarioDAO.incluirUsuario(usuario);
			} else {
				System.out.println("Erro na validação");
				return false;
			}
		} else {
			System.out.println("Email já existe");
			return false;
		}
	}

	

	public boolean alterarUsuario(Usuario usuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		this.usuario = usuarioDAO.consultarInfUsuario(usuario.getEmail());
		
		if(this.usuario.getEmail() != usuario.getEmail() && this.usuario.getId() == usuario.getId() ) {
			if (validar.validaNome(usuario.getNome()) && validar.validaEmail(usuario.getEmail())
					&& validar.validaSenha(usuario.getSenha()) && validar.validaTelefone(usuario.getTelefone1())) {
				
				return usuarioDAO.alterarUsuario(usuario);
			} else {
				System.out.println("Erro na validação");
				return false;
			}
		}else {
			System.out.println("Email já existe");
			return false;
		}
	}

	public boolean excluirUsuario(int id) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();		
		return usuarioDAO.excluirUsuario(id);		
	}
}
