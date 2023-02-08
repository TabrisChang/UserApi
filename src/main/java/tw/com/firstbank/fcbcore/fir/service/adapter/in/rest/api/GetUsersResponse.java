package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class GetUsersResponse {

  private String statusCode;

  List<GetUserResponse> users = new ArrayList<>();

}
