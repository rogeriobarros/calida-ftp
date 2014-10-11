package calida.com.br

class ListFilesController {
	
	def ftpServerService
	
	def index(){
		ftpServerService.listFiles()
		
		render "Files: ${ftpServerService.listFiles()}"
	}

}
