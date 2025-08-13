package com.softeer.security;

import com.softeer.entity.Role;
import com.softeer.error.ExceptionCreator;
import com.softeer.security.auth.JwtResolver;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserIdArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER = "Bearer ";

  private final JwtResolver jwtResolver;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(LoginUserId.class)
        && Long.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(
          MethodParameter parameter,
          ModelAndViewContainer mavContainer,
          NativeWebRequest webRequest,
          WebDataBinderFactory binderFactory
  ) {
    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    String jwtToken = request.getHeader(AUTHORIZATION_HEADER);

    if (jwtToken == null) {
      throw ExceptionCreator.create(JwtException.UNAUTHORIZED_USER); // 401
    }

    jwtToken = extractToken(jwtToken);
    long userId = jwtResolver.getUserId(jwtToken);
    Role userRole = jwtResolver.getRole(jwtToken);

    LoginUserId annotation = parameter.getParameterAnnotation(LoginUserId.class);
    Role requiredRole = annotation.role();

    if (!hasSufficientRole(userRole, requiredRole)) {
      throw ExceptionCreator.create(JwtException.FORBIDDEN_USER); // 403
    }

    return userId;
  }

  private boolean hasSufficientRole(Role userRole, Role requiredRole) {
    return userRole == requiredRole;
  }
  private String extractToken(String token) {
    return token.substring(BEARER.length());
  }

}
