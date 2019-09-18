package tests;

import dao.RolDao;
import modelo.Rol;

public class TestRolDao {
	public static void main(String[] args) {
		RolDao rolDao = new RolDao();
		Rol r1 = new Rol("Admin");
		Rol r2 = new Rol("Alumno");
		Rol r3 = new Rol("Docente");
		rolDao.insertarRol(r1);
		rolDao.insertarRol(r2);
		rolDao.insertarRol(r3);
		
		for (Rol r : rolDao.traerTodos()) {
			System.out.println("Id: " + r.getId() + " - " + r.getDenominacion());
		}
		Rol traer = rolDao.traerRolPorDenominacion("Admin");
		System.out.println("Id: " + traer.getId() + " - " + traer.getDenominacion());
		// AL TOQUE PERROOOOOO
	}
}
