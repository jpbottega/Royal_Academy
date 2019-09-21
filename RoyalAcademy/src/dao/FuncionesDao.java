package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Funciones;
import modelo.PermisoFunciones;


public class FuncionesDao extends DBManager {
	
	
	public List<Funciones> traerFuncionesHabilitadas(int id_rol) {
		List<Funciones> Funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			
				NativeQuery query = sesion.createSQLQuery("call funcionesHabilitadas("+id_rol+")").addEntity(Funciones.class);
				Funciones = (List<Funciones>) query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Funciones;
		}
		return Funciones;
	}
	public List<Funciones> traerFuncionesDisponibles(int id_rol) {
		List<Funciones> Funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			
				NativeQuery query = sesion.createSQLQuery("call funcionesDisponibles("+id_rol+")").addEntity(Funciones.class);
				Funciones = (List<Funciones>) query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Funciones;
		}
		return Funciones;
	}

	public List<PermisoFunciones> traerPermisoFunciones(int id_rol){
		List<PermisoFunciones> funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("call getPermisoFunciones("+id_rol+")")
					.addEntity(PermisoFunciones.class);
			funciones = (List<PermisoFunciones>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return funciones;
	}
	
	public List<Funciones> traerTodos(){
		List<Funciones> funciones = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from funciones")
					.addEntity(Funciones.class);
			funciones = (List<Funciones>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return funciones;
	}
}
