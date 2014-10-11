package calida.com.br

import java.io.Serializable

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory
import org.apache.ftpserver.listener.ListenerFactory


final class FtpServerSingle implements Serializable {
	
	private static def instance
	
	public static FtpServer getInstanceServer(){
		if(instance == null){
			instance = getServer()
		}
		
		instance
	}
	
	private FtpServerSingle(){}
	
	private static getServer(){
		def serverFactory = new FtpServerFactory()
		def factory = new ListenerFactory()
			factory.setPort(2221)
		
		serverFactory.addListener("default", factory.createListener())
		serverFactory.connectionConfig.anonymousLoginEnabled = true
		serverFactory.connectionConfig.maxLogins = 0
		
		serverFactory.createServer()
	}

}
