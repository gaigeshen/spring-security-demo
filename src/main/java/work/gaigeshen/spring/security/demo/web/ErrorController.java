package work.gaigeshen.spring.security.demo.web;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;
import work.gaigeshen.spring.security.demo.commons.web.Result;
import work.gaigeshen.spring.security.demo.commons.web.ErrorResults;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gaigeshen
 */
@RequestMapping("/error")
@Controller
public class ErrorController extends AbstractErrorController {

    private final ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);

        Throwable error = errorAttributes.getError(new DispatcherServletWebRequest(request));
        Map<String, Object> errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.of());
        Map<String, Object> resultData = new HashMap<>(errorAttributes);
        resultData.put("result", ErrorResults.createResult(error, getStatus(request).value()));
        return new ModelAndView("oops", resultData);
    }

    @RequestMapping
    @ResponseBody
    public Result<?> error(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        Throwable error = errorAttributes.getError(new DispatcherServletWebRequest(request));
        return ErrorResults.createResult(error, getStatus(request).value());
    }
}
