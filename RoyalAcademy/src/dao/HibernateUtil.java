package dao;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import modelo.*;


public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/royal_academy?allowPublicKeyRetrieval=true&useSSL=false");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update"); // si quieren q se cree la bd a partir de las clases aca poner "create-drop"
                configuration.setProperties(settings);
                
                // CLASES ENTIDAD
                configuration.addAnnotatedClass(Usuario.class);
                configuration.addAnnotatedClass(Rol.class);
                configuration.addAnnotatedClass(Sede.class);
                configuration.addAnnotatedClass(Carrera.class);
                configuration.addAnnotatedClass(Curso.class);
                configuration.addAnnotatedClass(Examen.class);
                configuration.addAnnotatedClass(Pregunta.class);
                configuration.addAnnotatedClass(Respuesta.class);
                configuration.addAnnotatedClass(Pais.class);
                configuration.addAnnotatedClass(ExamenResolucion.class);
                configuration.addAnnotatedClass(Funciones.class);
                //configuration.addAnnotatedClass(PreguntaResuelta.class);
                configuration.addAnnotatedClass(CursoExamen.class);
                configuration.addAnnotatedClass(Funciones_Perfil.class);
                configuration.addAnnotatedClass(Sede_Carrera.class);
                configuration.addAnnotatedClass(PermisoFunciones.class);
                //configuration.addAnnotatedClass(AlumnoNotas.class);
                configuration.addAnnotatedClass(Sede_Usuario.class);
                configuration.addAnnotatedClass(Curso_Usuario.class);  
                configuration.addAnnotatedClass(Opciones_Pregunta.class);  
                configuration.addAnnotatedClass(PreguntaxExamen.class);  
                
                // GUARDO CONFIGURACION
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
