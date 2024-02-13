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
  

 
  
















