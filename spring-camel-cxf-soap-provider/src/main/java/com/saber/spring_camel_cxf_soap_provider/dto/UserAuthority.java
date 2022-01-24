package com.saber.spring_camel_cxf_soap_provider.dto;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Embeddable
public class UserAuthority  {
  
  private String authority;
  
  public UserAuthority(String authority) {
    this.authority = authority;
  }
 
  public String getAuthority() {
    return this.authority;
  }
  
  public void setAuthority(String authority) {
    this.authority = authority;
  }
  

}
