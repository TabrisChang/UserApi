package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import lombok.Data;

@Data
public class CreateUserResponse {

  private String statusCode;
  private String no;
  private String branchCode;

}
