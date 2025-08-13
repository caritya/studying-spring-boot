package tacos;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
//@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
@RequiredArgsConstructor
@Table(name = "users")//TODO зачем? А без этой аннотации у меня почему-то таблица не создается в БД 
//потому что она не определена в schema.sql
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
//	@NotBlank(message = "username is required")
	private final String username;
//	@NotBlank(message = "password is required")
	private final String password;
//	@NotBlank(message = "fullname is required")
	private final String fullname;
//	@NotBlank(message = "street is required")
	private final String street;
//	@NotBlank(message = "city is required")
	private final String city;
//	@NotBlank(message = "state is required")
	private final String state;
//	@NotBlank(message = "zip code is required")
	private final String zip;
//	@NotBlank(message = "telephone number is required")
	private final String phoneNumber;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
