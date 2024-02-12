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













