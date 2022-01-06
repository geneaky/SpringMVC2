package hello.login.web.filter;

import hello.login.web.SessionConst;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class LoginCheckFilter implements Filter {

  private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestURI = httpRequest.getRequestURI();

    HttpServletResponse httpResponse = (HttpServletResponse) response;

    try {
      log.info("인증 체크 필터 시작{}", requestURI);

      if(isLoginCheckPath(requestURI)) {
        log.info("인증 체그 로직 실행 {}", requestURI);
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
          log.info("미인증 사용자 요청 {}", requestURI);
          //로그인으로 redirect
          httpResponse.sendRedirect("/login?redirectURL=" + requestURI); //로그인 후 사용자가 기존 요청 페이지로 리다렉트 되게 편의성을 위함;
          return;
        }
      }

      chain.doFilter(request,response);
    } catch (Exception e) {
      throw e; // 에외 로깅이 가능한데, 톰캣까지 예외를 던져줘야함
    } finally {
      log.info("체크 필터 종료");
    }
  }

  /**
   * 화이트 리스틔의 경우 인증 체크 x
   */
  private boolean isLoginCheckPath(String requestURI) {
    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }

}
