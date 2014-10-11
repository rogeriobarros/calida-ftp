package calida.com.br

import java.io.Serializable;

import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.AuthorizationRequest;
import org.apache.ftpserver.ftplet.User;


class AuthorityFtp implements Serializable{
	
	String className
	int max = 0

    static constraints = {
		className(blank:false, nullable:false, size:5..150)
		max(nullable:false)
    }
	
	static mapping = {
		id generator:'sequence', params:[sequence:'authority_seq']
		table 'AUTHORITY_FTP'
		version true
		id column:'auth_id'
	}
	
}
