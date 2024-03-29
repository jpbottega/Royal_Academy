package servlets;



import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.ServletContext;

import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IReportEngine;



import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.core.internal.registry.RegistryProviderFactory;


public class BirtEngine {

	private static IReportEngine birtEngine = null;

	private static Properties configProps = new Properties();

	private final static String configFile = "BirtConfig.properties";
	
	public static synchronized void initBirtConfig() {
		loadEngineProps();
	}

	@SuppressWarnings("unchecked")
	public static synchronized IReportEngine getBirtEngine(ServletContext sc) {
		if (birtEngine == null) 
		{
			EngineConfig config = new EngineConfig();
			if( configProps != null){
				String logLevel = configProps.getProperty("logLevel");
				Level level = Level.OFF;
				if ("SEVERE".equalsIgnoreCase(logLevel)) 
				{
					level = Level.SEVERE;
				} else if ("WARNING".equalsIgnoreCase(logLevel))
				{
					level = Level.WARNING;
				} else if ("INFO".equalsIgnoreCase(logLevel)) 
				{
					level = Level.INFO;
				} else if ("CONFIG".equalsIgnoreCase(logLevel))
				{
					level = Level.CONFIG;
				} else if ("FINE".equalsIgnoreCase(logLevel)) 
				{
					level = Level.FINE;
				} else if ("FINER".equalsIgnoreCase(logLevel)) 
				{
					level = Level.FINER;
				} else if ("FINEST".equalsIgnoreCase(logLevel)) 
				{
					level = Level.FINEST;
				} else if ("OFF".equalsIgnoreCase(logLevel)) 
				{
					level = Level.OFF;
				}

				config.setLogConfig(System.getProperty("catalina.base")+configProps.getProperty("logDirectory"), level);
			}

			config.setEngineHome("");
			config.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, BirtEngine.class.getClassLoader()); 
		
			
			IPlatformContext context = new PlatformServletContext( sc );
			config.setPlatformContext( context );

		
			//Create the report engine
			//birtEngine = new ReportEngine( config );
			//ReportEngine engine = new ReportEngine( null );
			
			
			try
			{
				Platform.startup( config );
			}
			catch ( BirtException e )
			{
				e.printStackTrace( );
			}

			IReportEngineFactory factory = (IReportEngineFactory) Platform
			.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			birtEngine = factory.createReportEngine( config );


		}
		return birtEngine;
	}

	public static synchronized void destroyBirtEngine() {
		if (birtEngine == null) {
			return;
		}		
		birtEngine.destroy();
		Platform.shutdown();
		RegistryProviderFactory.releaseDefault();
		birtEngine = null;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private static void loadEngineProps() {
		try {
			//Config File must be in classpath
			ClassLoader cl = Thread.currentThread ().getContextClassLoader();
			InputStream in = null;
			in = cl.getResourceAsStream (configFile);
			configProps.load(in);
			in.close();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
