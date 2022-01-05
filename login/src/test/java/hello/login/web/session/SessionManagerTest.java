package hello.login.web.session;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {

  SessionManager sessionManager = new SessionManager();

  @Test
  public void sessionTest() throws Exception{
    //세션 생성
    Member member = new Member();
    MockHttpServletResponse response = new MockHttpServletResponse();
    sessionManager.createSession(member,response);

    //요청에 응답 쿠키 저장
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setCookies(response.getCookies());

    //세션 조회
    Object result = sessionManager.getSession(request);
    assertThat(result).isEqualTo(member);

    //세션 만료
    sessionManager.expire(request);
    Object expired = sessionManager.getSession(request);
    assertThat(expired).isNull();

  }

}