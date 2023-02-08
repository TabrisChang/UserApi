package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api.adapter.in.rest.api;

import java.util.Date;
import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class CreateUserRequest {

  private String branchCode;
  private String businessCategory;
  private String verificationCode;
  private String firstName;
  private String lastName;
  private Date birthday;
  private String email;
  private String phone;

}
