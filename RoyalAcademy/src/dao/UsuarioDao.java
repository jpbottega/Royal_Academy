package dao;

import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import modelo.Usuario;
import endec.StringEncrypter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;


public class UsuarioDao extends DBManager {

	public boolean insertarUsuario(Usuario u) {
		Transaction t = null;
		//StringEncrypter crypto = new StringEncrypter("nosequevaaca");
		//u.setPass(crypto.encrypt(u.getPass()));
		boolean insertado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (this.traerUsuarioPorMail(u.getEmail()) == null) {
				t = sesion.beginTransaction();
				sesion.save(u);
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

	public boolean eliminarUsuario(Usuario u) {
		Transaction t = null;
		boolean eliminado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			t = sesion.beginTransaction();
			sesion.delete(u);
			t.commit();
			eliminado = true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return eliminado;
		}
		return eliminado;
	}

	public boolean actualizarUsuario(Usuario u) {
		Transaction t = null;
		boolean actualizado = false;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			t = sesion.beginTransaction();
			sesion.update(u);
			t.commit();
			actualizado = true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			return actualizado;
		}
		return actualizado;
	}

	public Usuario traerUsuarioPorId(int id) {
		Usuario user = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			if (id != 0) {
				SQLQuery query = sesion.createSQLQuery("select * from usuarios where id = :id").addEntity(Usuario.class)
						.setParameter("id", id);
				user = (Usuario) query.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}
		return user;
	}

	public Usuario traerUsuarioPorMail(String email) {
		Usuario user = null;
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from usuarios where email = :email")
					.addEntity(Usuario.class).setParameter("email", email);
			user = (Usuario) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	public List<Usuario> traerTodos(int id_rol) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = sesion.createSQLQuery("select * from usuarios where id_rol = :rol")
					.addEntity(Usuario.class).setParameter("rol", id_rol);
			usuarios = (List<Usuario>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return usuarios;
		}
		return usuarios;
	}
	
	public List<Usuario> traerUsuariosPorNomApeMail(String nombre, String apellido, String mail, int id_rol){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
			NativeQuery query = null;
			if (nombre == "" && apellido == "" && mail == "") {
				usuarios = this.traerTodos(id_rol); // si no mando nada traigo a todos los usuarios de ese rol
			}
			else { // si mando algo preparo el query acorde
				if (mail != "") {
					query = sesion.createSQLQuery("select * from usuarios where email = :mail and id_rol = :rol")
							.addEntity(Usuario.class).setParameter("mail", mail).setParameter("rol", id_rol);
				}
				else if (nombre != "" && apellido != "") {
					query = sesion.createSQLQuery("select * from usuarios where nombre = :nombre and apellido = :apellido and id_rol = :rol")
							.addEntity(Usuario.class).setParameter("nombre", nombre).setParameter("apellido", apellido).setParameter("rol", id_rol);
				} else if (nombre != "" && apellido == "") {
						query = sesion.createSQLQuery("select * from usuarios where nombre = :nombre and id_rol = :rol")
								.addEntity(Usuario.class).setParameter("nombre", nombre).setParameter("rol", id_rol);
				} else if (nombre == "" && apellido != "") {
						query = sesion.createSQLQuery("select * from usuarios where apellido = :apellido and id_rol = :rol")
							.addEntity(Usuario.class).setParameter("apellido", apellido).setParameter("rol", id_rol);
				}
				usuarios = (List<Usuario>) query.list(); // ejecuto el query
			}
		} catch (Exception e) {
			e.printStackTrace();
			return usuarios;
		}
		return usuarios;
	}
}
