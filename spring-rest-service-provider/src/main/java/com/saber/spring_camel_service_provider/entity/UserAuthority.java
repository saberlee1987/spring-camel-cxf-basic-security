package com.saber.spring_camel_service_provider.entity;


import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
public class UserAuthority implements GrantedAuthority {
  
  @Column(length = 30)
  private String authority;
  
  public UserAuthority(String authority) {
    this.authority = authority;
  }
  
  @Override
 
  public String getAuthority() {
    return this.authority;
  }
  
  public void setAuthority(String authority) {
    this.authority = authority;
  }
  

}
