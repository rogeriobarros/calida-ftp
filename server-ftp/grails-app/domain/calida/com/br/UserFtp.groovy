package calida.com.br

import java.io.Serializable
import java.util.List

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.ftpserver.ftplet.Authority
import org.apache.ftpserver.ftplet.AuthorizationRequest
import org.apache.ftpserver.ftplet.User

class UserFtp implements Serializable {
	
	String name
	String password
	String checkPassword
	String homeDirectory
	int maxIdleTime = 0
	Boolean enabled = true
	
	static hasMany = [authorities: Authority]
	static transients = ['checkPassword']
	
	static constraints = {
		name(blank:false, nullable:false, size:5..150)
		password(blank:false, nullable:false, size:5..150, password: true)
		homeDirectory(blank:false, nullable:false, size:5..1020)
		maxIdleTime(nullable:false)
		enabled(nullable:false)
	}
	
	static mapping = {
		id generator:'sequence', params:[sequence:'user_seq']
		table 'USER_FTP'
		version true
		id column:'user_id'
		authorities lazy:true
	}

	
	@Override
	List<Authority> getAuthorities() {
		authorities
	}
	
	void setAuthorities(listAuth){
		listAuth.each {
			this.addToAuthorities(it)
		}
	}

	@Override
	public boolean equals(Object obj) {
		new EqualsBuilder()
                 .appendSuper(super.equals(obj))
                 .append(name, obj.name)
                 .append(homeDirectory, obj.homeDirectory)
                 .append(maxIdleTime, obj.maxIdleTime)
				 .append(enabled, obj.enabled)
                 .isEquals()
	}
	
	@Override
	public int hashCode() {
		new HashCodeBuilder().append(name).append(homeDirectory).append(maxIdleTime).append(enabled).toHashCode()
	}
	
	@Override
	public String toString() {
		new StringBuilder().append("Nome: ").append(name).append(", ")
		.append("homeDirectory: ").append(homeDirectory).append(", ")
		.append("maxIdleTime: ").append(maxIdleTime).append(", ")
		.append("enabled: ").append(enabled).toString()
	}

}
