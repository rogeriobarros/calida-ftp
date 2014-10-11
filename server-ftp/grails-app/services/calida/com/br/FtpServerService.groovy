package calida.com.br

import org.apache.ftpserver.command.impl.listing.DirectoryLister;
import org.apache.ftpserver.command.impl.listing.FileFormater;
import org.apache.ftpserver.command.impl.listing.LISTFileFormater;
import org.apache.ftpserver.command.impl.listing.ListArgument;
import org.apache.ftpserver.usermanager.UserFactory;
import org.apache.ftpserver.usermanager.impl.ConcurrentLoginPermission
import org.apache.ftpserver.usermanager.impl.TransferRatePermission
import org.apache.ftpserver.usermanager.impl.WritePermission

class FtpServerService {

	def server
    
	void startFtpServer() {
		try {
			server = FtpServerSingle.instanceServer
			attachUsers(server.userManager, server.fileSystem)
			server.start()
			
			log.info("FtpServer started")
		} catch (e) {
			throw new RuntimeException("Failed to start FtpServer", e)
		}
    }
	
	void stopFtpServer(){
		try{
			if(server && (!server.isStopped || !server.isSuspended)){
				server.resume()
				server.stop()
				
				log.info("FtpServer stoped")
			}
		}catch(e){
			throw new RuntimeException("Failed to stop FtpServer", e)
		}
	}
	
	def listFiles(){
		new DirectoryLister().listFiles(new ListArgument(), server.fileSystem, new LISTFileFormater())
	}
	
	private void attachUsers(userManager, fileSystem){
		def users = UserFtp.list()
			users.each{user ->
				def ftpUser = fixAuthorities(user)
				userManager.save(ftpUser)
				fileSystem.createFileSystemView(ftpUser)
			}
	}
	
	private fixAuthorities(user){
		def auths = []
		
		def wPerm = new WritePermission()
		def cLPerm = new ConcurrentLoginPermission(0, 0)
		def tRPerm = new TransferRatePermission(0, 0)
		
		auths << wPerm
		auths << cLPerm
		auths << tRPerm
		
		def factory = new UserFactory()
			factory.setAuthorities(auths)
			factory.name = user.name
			factory.password = user.password
			factory.homeDir = user.homeDirectory
			factory.maxIdleTime = user.maxIdleTime
			factory.enabled = user.enabled
			
		factory.createUser()
	}
	
}
