package dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import modelo.CursoExamen;
import modelo.Sede;

public class CursoExamenDao extends DBManager {
	
	public CursoExamen traerCursoExamenPorId(int id) {
		CursoExamen carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from cursoexamen where id = :id").addEntity(CursoExamen.class)
						.setParameter("id", id);
				carrera = (CursoExamen) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public List<CursoExamen> traerExamenesPorCurso(int id_curso){
		List<CursoExamen> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from cursoexamen where id_curso = :id")
					.addEntity(CursoExamen.class).setParameter("id", id_curso);
			carrera = (List<CursoExamen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public List<CursoExamen> traerTodos(){
		List<CursoExamen> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from cursoexamen")
					.addEntity(CursoExamen.class);
			carrera = (List<CursoExamen>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
}
