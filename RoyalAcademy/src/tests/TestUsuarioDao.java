package tests;

import java.util.Date;
import dao.UsuarioDao;
import modelo.Usuario;

public class TestUsuarioDao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UsuarioDao udao = new UsuarioDao();
		Usuario user = new Usuario("pato@gmail.com", "1234", "Juan", "Bottega", "1536880928", false, 1, new Date());
		Usuario user2 = new Usuario("jorge@gmail.com", "asdf", "jorge", "alguien", "1234567891", false, 1, new Date());
		udao.insertarUsuario(user);
		udao.insertarUsuario(user2);
		Usuario modificar = udao.traerUsuarioPorMail("admin");
		modificar.setNombre("Jorgito");
		modificar.setTelefono("42428224");
		modificar.setId_rol(3);
		udao.actualizarUsuario(modificar);
		udao.eliminarUsuario(udao.traerUsuarioPorMail("pato@gmail.com"));
		System.out.println(udao.traerUsuarioPorMail("martin@gmail.com"));
		
		
		
	}

}
