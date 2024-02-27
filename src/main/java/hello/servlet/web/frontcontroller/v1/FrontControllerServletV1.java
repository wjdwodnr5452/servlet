package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*") // 서블릿 클래스를 서블릿 컨테이너에 등록하기 위해 WebServlet을 사용함
public class FrontControllerServletV1 extends HttpServlet { // 서블릿 클래스는 HttpServlet 상속을 받음, http 프로토콜을 받아 처리 하기 위해서 HttpServlet을 상속

    private Map<String, ControllerV1> controllerV1Map = new HashMap<>();

    public FrontControllerServletV1() { // 서블릿 클래스가 컨테이너에 등록되면서 url 패턴을 map에 담아줌
        controllerV1Map.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerV1Map.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members", new MemberListControllerV1());
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // service 메소드는 클라이언트의 요청이 있을 때 마다 매번 서블릿 컨테이너가 자동으로 실행
        System.out.println("frontControllerServletV1");

        String requestURI = request.getRequestURI();

        ControllerV1 controller = controllerV1Map.get(requestURI); // 인터페이스를 통해 다형성으로 구현 request에 나온 url을 통해 컨트롤러 가져오기

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request, response); // 컨트롤러 메소드 호출로 로직 실행

    }
}
