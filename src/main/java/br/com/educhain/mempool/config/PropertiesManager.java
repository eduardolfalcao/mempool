package br.com.educhain.mempool.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
	
	private static final String KEYGEN_ALGORITHM = "keygen_algorithm";
	private static final String SIGNATURE_ALGORITHM = "signature_algorithm";	
	
	private static PropertiesManager instance = new PropertiesManager();
	private Properties props;
	
	private PropertiesManager() {
		this.props = new Properties();
		try {
			props.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PropertiesManager getInstance() {
		return instance;
	}
	
	public String getKeyGenAlgorithm() {
		return props.getProperty(PropertiesManager.KEYGEN_ALGORITHM);
	}
	
	public String getSignatureAlgorithm() {
		return props.getProperty(PropertiesManager.SIGNATURE_ALGORITHM);
	}

}