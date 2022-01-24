package com.saber.spring_camel_cxf_soap_provider.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	@Column(name = "password",length = 80)
	private String password;
	@Column(name = "username",length = 70)
	private String username;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"))
	private List<UserAuthority> userEntityAuthorities;
	
	public List<UserAuthority> getAuthorities() {
		return this.userEntityAuthorities;
	}
	

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	
	
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}
	
	
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public String toString() {
		return new GsonBuilder()
				.setLenient()
				.setPrettyPrinting()
				.enableComplexMapKeySerialization()
				.setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
				.setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
				.create().toJson(this, User.class);
	}
}