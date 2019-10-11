package dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Curso;
import modelo.Sede;

public class CursoDao extends DBManager {
	public boolean insertarCurso(Curso curso) {
		Transaction t = null;
		// StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		// u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerCursoPorDenominacion(curso.getDenominacion()) == null) {
				t = sesion.beginTransaction();
				sesion.save(curso);
				t.commit();
				insertado = true;
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return insertado;
		}
		return insertado;
	}

	public boolean eliminarCurso(Curso curso) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerCursoPorDenominacion(curso.getDenominacion()) != null) {
				t = sesion.beginTransaction();
				sesion.delete(curso);
				t.commit();
				eliminado = true;
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return eliminado;
		}
		return eliminado;
	}

	public Curso traerCursoPorId(int id) {
		Curso curso = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				NativeQuery query = sesion.createSQLQuery("select * from cursos where id = :id").addEntity(Curso.class)
						.setParameter("id", id);
				curso = (Curso) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return curso;
		}
		return curso;
	}

	public Curso traerCursoPorDenominacion(String denominacion) {
		Curso curso = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from cursos where denominacion = :denominacion")
					.addEntity(Sede.class).setParameter("denominacion", denominacion);
			curso = (Curso) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return curso;
		}
		return curso;
	}

	
	public List<Curso> traerCursoCarrera(int id_carrera) {
		List<Curso> curso = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			curso = (List<Curso>) sesion.createSQLQuery("select * from cursos where id_carrera =" + id_carrera)
					.addEntity(Curso.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return curso;
		}
		return curso;
	}
	
	public List<Curso> traerCursoPorUsuario(int id_usuario) {
		List<Curso> curso = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			curso = (List<Curso>) sesion.createSQLQuery("select * from cursos where id in (select id_curso from curso_usuario where id_usuario = :id)")
					.addEntity(Curso.class).setParameter("id", id_usuario).list();
		} catch (Exception e) {
			e.printStackTrace();
			return curso;
		}
		return curso;
	}

	public List<Curso> traerTodos() {
		List<Curso> curso = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from cursos").addEntity(Curso.class);
			curso = (List<Curso>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return curso;
		}
		return curso;
	}
}
