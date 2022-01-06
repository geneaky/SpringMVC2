package hello.exception.servlet;

import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static javax.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static javax.servlet.RequestDispatcher.ERROR_SERVLET_NAME;
import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ErrorPageController {

  @RequestMapping("/error-page/404")
  public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
    log.info("errorPage 404");
    return "error-page/404";
  }

  @RequestMapping("/error-page/500")
  public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
    log.info("errorPage 500");
    printErrorInfo(request);
    return "error-page/500";
  }

  private void printErrorInfo(HttpServletRequest request) {
    log.info(String.valueOf(request.getAttribute(ERROR_MESSAGE)));
    log.info(String.valueOf(request.getAttribute(ERROR_REQUEST_URI)));
    log.info(String.valueOf(request.getAttribute(ERROR_SERVLET_NAME)));
    log.info(String.valueOf(request.getAttribute(ERROR_STATUS_CODE)));
  }
}
