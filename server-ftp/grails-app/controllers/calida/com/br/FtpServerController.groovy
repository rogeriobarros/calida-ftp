package calida.com.br

class FtpServerController {
	
	def ftpServerService
	
	def index() {
		def serverAddress = ftpServerService.server.getListener("default").serverAddress
		def port = ftpServerService.server.getListener("default").port
		def activeSessions = ftpServerService.server.getListener("default").activeSessions
		
		render "serverAddress: ${serverAddress}, port: ${port}, activeSessions: ${activeSessions}"
	}

    def start() { 
		ftpServerService.startFtpServer()
		
		render "Server Up"
	}
	
	def stop() {
		ftpServerService.startFtpServer()
		
		render "Server Down"
	}
}
