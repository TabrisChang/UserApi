package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.reactive.function.BodyInserters;
import tw.com.firstbank.fcbcore.fir.service.ServiceApplication;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.CreateUserRequest;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.CreateUserResponse;
import tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.mapper.UserControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserUseCaseApi;

@SpringBootTest(classes = ServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CreateUserUseCaseApi createUserUseCaseApi;

  @Autowired
  private UserControllerMapper mapper;

  @Test
  public void testValidatedAnnotation() throws Exception {
    //Arrange
    String url = "/v1/users";
    CreateUserRequest request = Instancio.of(CreateUserRequest.class).create();
    CreateUserResponseCommand responseCommand = Instancio.of(CreateUserResponseCommand.class).create();
    when(createUserUseCaseApi.execute(any())).thenReturn(responseCommand);
    CreateUserResponse resp = mapper.toCreateUserResponse(responseCommand);

    //Act
    ResponseSpec callApiResult = this.webTestClient.post().uri(url)
        .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .body(BodyInserters.fromValue(request))
        .exchange();

    //Assert
    callApiResult.expectStatus().isOk();
    callApiResult.expectBody().json(objectMapper.writeValueAsString(resp));

  }

}
