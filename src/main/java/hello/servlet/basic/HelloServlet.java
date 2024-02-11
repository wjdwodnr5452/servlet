package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override // 서블릿이 호출 하면 service 메소드를 호출 한다.
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");

        // 서블릿 http 요청이 오면 Servlet container가 request,response 객체를 만들어서 던젼다.
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // http://localhost:8080/hello?username=kim
        String username = request.getParameter("username");
        System.out.println("username = " + username);


        // 응답은 HttpServletResponse 넣어주면 웹브라우저에 응답하는 response-http 응답 메시지에 데이터가 담긴다.
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);


    }
}
