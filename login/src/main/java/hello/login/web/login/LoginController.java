package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final SessionManager sessionManager;

  @GetMapping("/login")
  public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
    return "login/loginForm";
  }

//  @PostMapping("/login")
  public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
      HttpServletResponse response) {
    if(bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 삭제)
    Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
    response.addCookie(idCookie);

    return "redirect:/";
  }

//  @PostMapping("/login")
  public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
      HttpServletResponse response) {
    if(bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

   sessionManager.createSession(loginMember,response);

    return "redirect:/";
  }

//  @PostMapping("/login")
  public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
      HttpServletRequest request) {

    if(bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    //세션이 있으면 있는 세션을 반환하고, 없으면 신규 세션을 생성한다.
    HttpSession session = request.getSession(); //default option true
    //default option 을 false로 설정하면 세션이 없을시 신규 세션을 새로 생성하지 않고 그냥 null을 반환한다.
    //세션에 로그인 회원 정보 보관
    session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
    session.setAttribute("test","abcd");

    return "redirect:/";
  }

  @PostMapping("/login")
  public String loginV4(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
      @RequestParam(defaultValue = "/") String redirectURL,
      HttpServletRequest request) {

    if(bindingResult.hasErrors()) {
      return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
      bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
      return "login/loginForm";
    }

    //세션이 있으면 있는 세션을 반환하고, 없으면 신규 세션을 생성한다.
    HttpSession session = request.getSession(); //default option true
    //default option 을 false로 설정하면 세션이 없을시 신규 세션을 새로 생성하지 않고 그냥 null을 반환한다.
    //세션에 로그인 회원 정보 보관
    session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
    session.setAttribute("test","abcd");

    return "redirect:" + redirectURL;
  }

//  @PostMapping("/logout")
  public String logout(HttpServletResponse response) {
    expireCookie(response,"memberId");
    return "redirect:/";
  }

//  @PostMapping("/logout")
  public String logoutV2(HttpServletRequest request) {
    sessionManager.expire(request);
    return "redirect:/";
  }

  @PostMapping("/logout")
  public String logoutV3(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/";
  }


  private void expireCookie(HttpServletResponse response,String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
