package com.ita.utils;

import com.ita.domain.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class UsernamePasswordAuthenticationTokenUtils {

  public static UsernamePasswordAuthenticationToken generateUsernamePasswordAuthenticationToken(User user, String role){
    return new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.commaSeparatedStringToAuthorityList(role));
  }

}
