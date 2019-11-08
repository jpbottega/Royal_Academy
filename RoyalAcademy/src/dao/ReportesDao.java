package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Funciones;
import modelo.InfoCursos;
import modelo.PermisoFunciones;


public class ReportesDao extends DBManager {
	
	
	public List<InfoCursos> traerInfoCursos(int id_curso) {
		List<InfoCursos> infocursos = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			
				NativeQuery query = sesion.createSQLQuery("call info_cursos("+id_curso+")").addEntity(InfoCursos.class);
				infocursos = (List<InfoCursos>) query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return infocursos;
		}
		return infocursos;
	}
	
}
