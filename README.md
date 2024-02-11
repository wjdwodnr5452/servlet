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

