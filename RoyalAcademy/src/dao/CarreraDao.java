package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Carrera;
import modelo.Funciones;
import modelo.Sede;


public class CarreraDao extends DBManager {
	
	public List<Sede> traerSedesHabilitadas(int id_carrera) {
		List<Sede> Funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			
				NativeQuery query = sesion.createSQLQuery("call sedesHabilitadas("+id_carrera+")").addEntity(Sede.class);
				Funciones = (List<Sede>) query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Funciones;
		}
		return Funciones;
	}
	public List<Sede> traerSedesDisponibles(int id_carrera) {
		List<Sede> Funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			
				NativeQuery query = sesion.createSQLQuery("call sedesDisponibles("+id_carrera+")").addEntity(Sede.class);
				Funciones = (List<Sede>) query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Funciones;
		}
		return Funciones;
	}

	public Carrera traerCarreraPorId(int id) {
		Carrera carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from carreras where id = :id").addEntity(Carrera.class)
						.setParameter("id", id);
				carrera = (Carrera) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}

	public Carrera traerCarreraPorDenominacion(String denominacion) {
		Carrera carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from carreras where denominacion = :denominacion")
					.addEntity(Carrera.class).setParameter("denominacion", denominacion);
			carrera = (Carrera) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
	
	public List<Carrera> traerTodos(){
		List<Carrera> carrera = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			SQLQuery query = sesion.createSQLQuery("select * from carreras")
					.addEntity(Carrera.class);
			carrera = (List<Carrera>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return carrera;
		}
		return carrera;
	}
}
