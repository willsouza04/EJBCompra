package br.univel.ejb.compra;

import java.util.Properties;
import javax.naming.Context;

public class JNDIConfig {
		
	public static Properties getConfigs() {
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.wildfly.naming.client.WildFlyInitialContextFactory");
		prop.put(Context.PROVIDER_URL,
				"http-remoting://localhost:8080");
		return prop;
	}

}
