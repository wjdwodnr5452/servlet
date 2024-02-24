# 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술
- 출처 : 김영한 - 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술
- 인프런 강의 : 김영한 - 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술 공부 내용 정리 글 입니다.

# Hello 서블릿
- @ServletComponentScan
  - 패키지 안에 있는 서블릿을 자동 등록 
- @WebServlet (서블릿 애노테이션)
  - name : 서블릿 이름
  - urlPatterns : URL 매핑

##### 서블릿 매핑 된 url 호출하면 service 메소드가 호출
![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/46da01ff-ad6a-4105-88e8-af41f55c5b35)


### 서블릿 컨테이너 동작 방식 설명
##### 이미지 출처 : 인프런- 김영한 스프링MVC1편
![인프런-스프링MVC1편](https://github.com/wjdwodnr5452/servlet/assets/90361061/dc79f208-a922-42e8-9fe8-03b399cd607f)

1. 스프링 부트 실행하면 내장 톰켓 서버가 띄어진다.
2. 톰캣 서버는 내부에 서블릿 컨테이너를 가지고 있음
3. 그래서 서블릿 컨테이너를 통해 내부에 서블릿들을 생성 해줌 


### 웹 애플리케이션 서버의 요청 응답 구조
##### 이미지 출처 : 인프런- 김영한 스프링MVC1편
![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/bcc0f82f-97e6-46ac-b902-611e44f845cd)

1. URL을 호출 하면 request, response 객체를 만들어서 싱글톤에 띄어져 있는 helloServlet을 호출 해줌
2. helloServlet을 호출 하면서 request와 response를 넘겨줌
3. 데이터를 정리하고 response 객체를 반환 하면 웹브라우져에 데이터가 보여짐

# HttpServletRequsest - 개요
- 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱하고 응답에 대한 데이터는 HttpServletRequest 객체에 담아서 제공

#### 임시 저장소 기능
- 해당 HTTP 요청이 시작부터 끝날 때 까지 유지되는 임시 저장소 기능
http 안에 작은 저장소가 있어서 http 요청 메시지가 살아 있는 동안 쓸 수 있게 setAttribute에 값을 지정 하고 getAttibute로 조회 가능

#### 세션 관리 기능
- 로그인 기능
- request.getSession(create:true)

# Http 요청 데이터 개요
- HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법 3가지가 있음

### GET- 쿼리 파라미터
- /url?username=hello&age=20
- 메시지 바디 없이, url의 쿼리 파라미터에 데이터를 포함해서 보냄
- 예) 검색, 필터, 페이징 등에서 많이 사용하는 방식

### POST - HTML Form
- Content-Type : application/x-www-form-urlencoded
- 메시지 바디에 쿼리 파라미터 형식으로 전달 username=hello&age=20 
- 예) 회원 가입, 상품 주문, HTML Form 사용

Content-Type은 body에 있는 어떤 스타일에 있는 데이터인지 대해서 설명
application/x-www-form-urlencoded는 html form을 통해서 전달된 정보

### HTTP message body에 데이터를 직접 담아서 요청
- HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON 사용
- POST, PUT, PATCH

# Http 요청 데이터 - GET 쿼리 파라미터
- 쿼리 파라미터는 url에 다음과 같이 ?를 시작으로 보낼 수 있음 추가 파라미터는 &로 구분하면 됨
#### http://localhost:8080/request-header?username=hello&age=20

클라이언트에서 서버로 보내면 서버에서는 HttpServletRequest가 제공하는 메서드를 통해 쿼리 파라미터를 조회 할 수 있음

#### 전체 파라미터 조회
```
// getParameterNames 모든 요청 파라미터를 꺼낼 수 있다.
request.getParameterNames().asIterator().forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));
```

#### 단일 파라미터 조회
```
String username = request.getParameter("username");
String age = request.getParameter("age");
```

#### 복수 파라미터 조회
```
String[] usernames = request.getParameterValues("username");
for (String name : usernames) {
  System.out.println("name = " + name);
}
```

#### 복수 파라미터에서 단일 파라미터 조회
username=hello&username=hello2 같은 파라미터 이름은 하나인데 값이 중복이면 getParameterValues 를 사용 해야한다. 중복일 때 getParameter를 사용시 첫번째 값을 반환 한다.
주로 중복으로 보내는 경우는 없음


# HTTP 요청 데이터 - POST HTML Form
- content-type: application/x-www-form-urlencoded
- 메시지 바디에 쿼리 파라미터 형식으로 데이터를 전달


application/x-www-form-urlencoded 형식은 앞에 있는 GET에서 살펴본 쿼리 파라미터 형식과 같음 따라서 쿼리 파라미터 조회 메서드를 그대로 사용 가능
클라이언트 입장에서는 두 방식에 차이가 있지만, 서버 입장에서는 둘의 형식이 동일 하므로 request.getParameter 로 편리하게 구분 없이 조회 가능

request.getParameter는 GET 쿼리파라미터 와 POST HTML Form 둘다 꺼내서 사용 할 수 있다.

#### content-type은 HTTP 메시지 바디의 데이터 형식을 지정함
- GET URL 쿼리 파라미터 형식으로 클라이언트에서 서버로 데이터를 전달할 때는 HTTP 메시지 바디를 사용하지 않기 때문에 content-type이 없음
- POST HTML Form 형식으로 데이터를 전달면 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기 때문에 바디에 포함된 데이터 어떤 형식인지 content-type을 꼭 지정해야 함 이렇게 폼으로 데이터를 전송하는 형식을 application/x-www-form-urlencoded 라 함

#### postman을 사용한 테스트
![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/2b472f90-d189-40f0-8166-eb8ae1a1ff84)


# HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트
### HTTP message body에 데이터를 직접 담아서 요청
- HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON 사용
- POST,PUT,PATCH
- 예전에는 XML을 썻지만 요즘은 JSON을 많이 씀
- API들은 서버랑 서버가 통신할 때 아니면 안드로이드 아이폰 같은 앱에서 요청을 할 때
- 요즘에는 React나 Vue.js 같은 웹 클라이언트에서 javaScript로 요청 할 때

```
ServletInputStream inputStream = request.getInputStream(); // ServletInputStream으로 받으면  request.getInputStream을 바이트 코드로 받음
String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// 바이트를 문자열로 변환
```


# HTTP 요청 데이터 - API 메시지 바디 - JSON
- json 형식은 json을 그대로 사용 하지 않고 객체로 바꿔서 사용
- JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson, Gson 같은 JSON 변환 라이브러리를 추가해서 사용해야함 스프링 부트로 Spring MVC를 선택하면 기본으로 Jackson 라이버리 'ObjectMapper'를 함께 제공한다.
```
// 제이슨 라이브러리 중에 잭슨 라이브러리가 있다.
private ObjectMapper objectMapper = new ObjectMapper();
```

참고 : HTML form 데이터도 메시지 바디를 통해 전송되므로 직접 읽을 수 있음 하지만 편리한 파라미터 조회 기능 request.getParameter(...) 을 이미 제공하기 때문에 파라미터 조회 기능을 사용하면 됨

 # HttpServletResponse - 기본 사용법
 - HttpServletResponse는 HTTP 응답 메시지를 생성 한다.
 - HTTP 응답코드 지정 예) 200, 400, 500 등
 - 헤더 생성
 - 바디 생성

#### 편의 기능 제공
- Content-Type, 쿠기, Redirect
  

 # HTTP 응답 데이터 - 단순 텍스트, HTML
- 단순 텍스트 응답 (writer.println("ok");)
- HTML 응답
- HTTP API - MessageBody JSON 응답
```
response.setContentType("text/html"); // 지정을 안해도 웹브라우져가 동작하는데 정석적으로 지정 해야함
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("    <div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");
```
HTTP 응답으로 HTML을 변환할 때는 content-type을 text/html로 지정 해야 함


# HTTP 응답 데이터 - API JSON
- HTTP API를 만들 때 주로 사용하는 것은 JSON 데이터이다.
- HTTP 응답으로 JSON을 반환할 때는 content-type을 application/json로 지정해야 하며 Jackson 라이브러리가 제공하는 objectMapper.writeValueAsString()을 사용하면 객체를 JSON 문자로 변경 할 수 있음

# 서블릿으로 회원관리 웹 애플리케이션 만들기

서블릿 덕분에 동적으로 원하는 HTML을 마음껏 만들 수 있음 정적인 HTML 문서라면 화면이 계속 달라지는 회원의 저장 결과라던가, 회원 목록 같은 동적인 HTML을 만드는 일은 불가능 함
그런데, 코드에서 보듯이 이것은 매우 복잡하고 비효율 적이다. 자바 코드로 HTML을 만들어 내는 것 보다 차라리 HTML 문서에 동적으로 변경해야 하는 부분만 자바 코드를 넣을 수 있다면 더 편리할 것이다.
이것이 바로 템플릿 엔진이 나온 이유이다. 템플릿 엔진을 사용하면 HTML 문서에서 필요한 곳만 코드를 적용해서 동적으로 변경할 수 있다.

```
// 서블릿 html 동적 코드 매번 자바코드로 작성 해야 하기 때문에 비효율 적임
PrintWriter w = response.getWriter();
        w.write("<html>");
        w.write("<head>");
        w.write(" <meta charset=\"UTF-8\">");
        w.write(" <title>Title</title>");
        w.write("</head>");
        w.write("<body>");
        w.write("<a href=\"/index.html\">메인</a>");
        w.write("<table>");
        w.write(" <thead>");
        w.write(" <th>id</th>");
        w.write(" <th>username</th>");
        w.write(" <th>age</th>");
        w.write(" </thead>");
        w.write(" <tbody>");

        for (Member member : members) {
            w.write(" <tr>");
            w.write(" <td>" + member.getId() + "</td>");
            w.write(" <td>" + member.getUsername() + "</td>");
            w.write(" <td>" + member.getAge() + "</td>");
            w.write(" </tr>");
        }
        w.write(" </tbody>");
        w.write("</table>");
        w.write("</body>");
        w.write("</html>");
```

템플릿 엔진에는 JSP, Thymeleaf, Freemarker, Velocity 등이 있다.

#### jsp는 성능과 기능면에서 다른 템플릿 엔진과의 경쟁에서 밀리면서, 점점 사장되어 가는 추세임

![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/13c40b78-8390-4b42-8027-e755fe5f96de)
Status 200이 회색이면 캐시가 된것이다.


# JSP로 회원 관리 웹 애플리케이션 만들기

#### <%@ page contentType="text/html;charset=UTF-8" language="java" %>
- 첫 줄은 jsp 문서라는 뜻

#### jsp는 자바 코드를 그대로 다 사용 할 수 있음
- <%@ page import="hello.servlet.domain.member.MemberRepository" %>
- <% ~~ %>
  - 이 부분에 자바 코드를 입력 할 수 있음
- <%= ~~ %>
  - 이 부분에 자바 코드를 출력 할 수 있음
 
#### 서블릿과 jsp 한계
- 서블릿으로 개발 할 때 뷰 화면을 위한 HTML을 만드는 작업이 자바 코드에 섞여서 지저분하고 복잡 했다 jsp를 사용한 덕분에 뷰를 생성하는 HTML 작업을 깔끔하게 가져가고, 중간중간 동적으로 변경이 필요한 부분에만 자바 코들르 적용했다.
- 하지만 jsp 코드를 보면 java 코드 데이터를 조회하는 리포지토리 등등 다양한 코드가 모두 jsp에 노출 됨, jsp가 너무 많은 역할 한다.

#### mvc 패턴의 등장
- 비즈니스 로직은 서블릿 처럼 다른곳에서 처리하고, jsp는 목적에 맞는 html로 화면(view)을 그리는 일에 집중 해야함 과거 개발자들도 모두 비숫한 고민이 있었고 그래서 mvc 패턴이 등장 했다
  
# MVC 패턴 - 개요
#### 너무 많은 역할
- 하나의 서블릿이나 jsp 만으로 비즈니스 로직과 뷰 렌더링 까지 모두 처리하게 되면, 너무 많은 역할을 하게 되고, 결과적으로 유지보수가 어려워짐.
- 비즈니스 로직을 호출한는 부분에 변경이 발생해도 해당 코드를 손대야 하고, UI를 변경할 일이 있어도 비즈니스 로직이 함께 있는 해당 파일을 수정해야 함

#### 변경의 라이플 사이클
- UI를 일부 수정하는 일과 비즈니스 로직을 수정하는 일은 각각 다르게 발생할 가능성이 높고 대부분 서로에게 영향을 주지 않는다. 이렇게 변경의 라이프 사이클이 다른 부분을 하나의 코드로 관리하는 것은 유지보수하기 좋지 않다.

#### 기능특화
- 특히 jsp 같은 뷰 템플릿은 화면을 렌더링 하는데 최적화 되어 있끼 때문에 이 부분의 업무만 담당하는 것이 가장 효과적임

#### Model View Controller
- MVC 패턴은 지금까지 학습한 것 처럼 하나의 서블릿이나, JSP로 처리하던 것을 컨트롤러와 뷰라는 영역으로 서로 역할은 나눈 것을 말한다. 웹 애플리케이션은 보통 이 MVC 패턴을 사용한다.

##### 컨트롤러 : HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행한다. 그리고 뷰에 전달할 결과 데이터를 조회 해서 모델에 담는다.
##### 모델 : 뷰에 출력할 데이터를 담아둔다. 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 되고, 화면을 렌더링 하는 일에 집중할 수 있다.
##### 뷰 : 모델에 담겨있는 데이터를 사용해서 화면을 그리는 일에 집중한다. 여기서 HTML을 생성 하는 부분을 말한다.

##### 이미지 출처 : 인프런- 김영한 스프링MVC1편
![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/f02b0369-c365-470a-812f-2fbb01ea6dc8)


#### 참고
- 컨트롤러에 비즈니스 로직을 둘 수도 있지만, 이렇게 되면 컨트롤러가 너무 많은 역할을 담당한다. 그래서 일반적으로 비즈니스 로직은 서비스라는 계층을 별도로 만들어서 처리한다. 그리고 컨트롤러는 비즈니스 로직이 있는 서비스를 호출하는 담당한다. 참고로 비즈니스 로직을 변경하면 비즈니스 로직을 호출하는 컨트롤러의 코드도 변경될 수 있다.

# MVC 패턴 - 적용
- 서블릿을 컨트롤러로 사용하고, jsp를 뷰로 사용해서 MVC 패턴 적용
- Model은 HttoServletRequest 객체를 사용한다. request 내부에 데이터 저장소를 가지고 있는데 request.setAttribute(), request.getAttribute()를 사용하면 데이터를 보관하고, 조회 할 수 있음

#### dispatcher.forward() : 다른 서블릿이나 jsp로 이동할 수 있는 기능이다. 서버 내부에서 다시 호출이 발생한다.
#### /WEB-INF : 이 경로안에 jsp가 있으면 외부에서 직접 jsp를 호출 할 수 없다. 우리가 기대하는 것은 항상 컨트롤러를 통해서 jsp를 호출 하는 것

#### redirect vs forward
- redirect : 리다이렉트는 실제 클라이언트(웹 브라우저)에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시 요청한다. 따라서 클라이언트가 인지 할 수 있고, URL 경로도 실제로 변경된다.
- forward : 포워드는 서버 내부에서 일어나는 호출이기 때문에 클라이언트가 전혀 인지하지 못한다.

#### form action 경로
```
<form action="save" method="post">
    username: <input type="text" name="username" />
    age:      <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
```
- form 안에 action에 경로를 /save를 하게 되면 절대 경로로 http://localhost:8080/save로 들어가 된다.
- /save가 아닌 / 빼고 save를 하게 되면 상대 경로로 현재 url 속한 계층에 붙는다. http://localhost:8080/servlet-mvc/members/save

# MVC 패턴 - 한계

#### MVC 컨트롤러의 단점
1. 포워드 중복
   - view로 이동하는 코드가 항상 중복 호출 되어야 한다. 물론 이 부분을 메서드로 공통화해도 되지만, 해당 메서드로 항상 직접 호출 해야한다.
```
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
dispatcher.forward(request, response); // 서블릿에서 jsp 호출
```

2. ViewPath에 중복
   - prefix : /WEB-INF/views/
   - suffix : .jsp
   - jsp가 아닌 thymeleaf 같은 다른 뷰로 변경한다면 전체 코드를 다 변경 해야한다.

```
String viewPath = "/WEB-INF/views/new-form.jsp";
```

4. 사용하지 않는 코드

5. 공통 처리가 어렵다
   - 기능이 복잡해질 수 록 컨트롤러에서 공통으로 처리해야 하는 부분이 점점 더 많이 증가할 것 이다. 단순히 공통 기능을 메서드로 뽑으면 될 것 같지만, 결과적으로 해당 메서들를 항상 호출해야 하고, 실수로 호출하지 않으면 문제가 될 것이다. 그리고 호출하는 것 자체도 중복이다.
  
6. 정리하면 공통 처리가 어렵다는 문제가 있다.
   - 이 문제를 해결하려면 컨트롤러 호출 전에 공통 기능을 처리해야 한다. 소위 수문장 역할을 하는 기능이 필요하다. 프론트 컨트롤러 패턴을 도입하면 이런 문제를 깔끔하기 해결 할 수 있다.


# 프론트 컨트롤러 팬터 소개
- 프론트 컨트롤러 패턴을 도입하기 전에는 공통로직을 각각 만들어야함
- 프론트 컨트롤러 패턴을 도입하면 공통로직을 하나로 모아 놓움

#### frontController 특징
- 프론트 컨트롤러는 servlet 이다.
- 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
- 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
- 공통 처리 가능
- 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨

#### 스프링 웹 MVC와 프론트 컨트롤러
- 스프링 웹 MVC의 핵심도 바로 FrontController
- 스프링 웹 MVC의 DispatcherServlet이 FrontController 패턴으로 구현되어 있음

# 프론트 컨트롤러 도입 - v1

##### 이미지 출처 : 인프런- 김영한 스프링MVC1편
![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/02066137-e5e7-46bd-94fc-c640fa9658d1)

```
public interface ControllerV1 {
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
```
- 서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입하고 각 컨트롤러들은 이 인터페이스를 구현 하면된다.
```
public class MemberFormControllerV1 implements ControllerV1 {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response); // 서블릿에서 jsp 호출
    }
}
```

froncontroller 구현

```
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerV1Map = new HashMap<>();

    public FrontControllerServletV1() {
        controllerV1Map.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerV1Map.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerV1Map.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("frontControllerServletV1");
        String requestURI = request.getRequestURI();
        ControllerV1 controller = controllerV1Map.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        controller.process(request, response);
    }
}
```
#### urlPatterns
- urlPatterns = "/front-controller/v1/*"  : /front-controller/v1를 포함한 하위 모든 요청은 이 서블릿에서 받아들인다.

#### controllerMap
- key : 매핑 URL
- value : 호출될 컨트롤러

# view 분리 - v2

##### 이미지 출처 : 인프런- 김영한 스프링MVC1편
![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/ce91558b-cfad-49d8-be7a-7162b9888b57)

```
@Override
public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  return new MyView("/WEB-INF/views/new-form.jsp");
}
```
- 각 컨트롤러는 복잡한 dispatcher.forward()를 직접 생성해서 호출 하지 않아도 된다.
- MyView 객체를 생성하고 거기에 뷰 이름만 넣고 반환하면 된다.

  
```
@Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      String requestURI = request.getRequestURI();

      ControllerV2 controller = controllerV2Map.get(requestURI);

      if (controller == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          return;
      }
      MyView view = controller.process(request, response);
      view.render(request,response);
  }
```

- ControllerV2 반환 타입이 MyView 이 므로 프론트 컨트롤러는 컨트롤러의 호출 결과로 MyView를 반환 받는다.
- view.render()를 호출하면 forward 로직을 수행해서 jsp가 실행

# Model 추가 - v3 (복습)
#### 서블릿 종속성 제거
- 요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 컨트롤러가 서블릿 기술을 몰라도 동작 할 수 있다. request 객체를 Model로 사용하는 대신에 별도 Model 객체를 만들어서 반환하면 된다.

#### 뷰 이름 중복 제거
- 컨트롤러에서 지정하는 뷰 이름에 중복이 있는 것을 확인 할 수 있다.
- 컨트롤러는 뷰의 논리 이름을 반환하고, 실제 물리 위치의 이름은 컨트롤러에서 처리하도록 단순화 한다.
- 향후 뷰의 폴더 위치가 함께 이동해도 프론트 컨트롤러만 고치면 된다.
- "/WEB-INF/views/new-form.jsp" -> "new-form.jsp"


# 단순하고 실용적인 컨트롤러 - v4

##### 이미지 출처 : 인프런- 김영한 스프링MVC1편
![image](https://github.com/wjdwodnr5452/servlet/assets/90361061/58023ab7-cc8f-4075-8a26-7ba3fd547792)
- 기본적인 구조는 v3와 같다. 대신에 컨트롤러가 ModelView를 반환하지 않고, ViewName 만 반환한다.



  







  



