package dao;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Pais;


public class PaisDao {
	
	

	public Pais traerPaisPorId(int id) {
		Pais p = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				NativeQuery query = sesion.createSQLQuery("select * from pais where id = :id").addEntity(Pais.class)
						.setParameter("id", id);
				p = (Pais) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return p;
		}
		return p;
	}

	public Pais traerPaisPorDenominacion(String denominacion) {
		Pais p = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from pais where denominacion = :denominacion")
					.addEntity(Pais.class).setParameter("denominacion", denominacion);
			p = (Pais) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return p;
	}
	
	public List<Pais> traerTodos(){
		List<Pais> paises = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from pais")
					.addEntity(Pais.class);
			paises = (List<Pais>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return paises;
	}
}
