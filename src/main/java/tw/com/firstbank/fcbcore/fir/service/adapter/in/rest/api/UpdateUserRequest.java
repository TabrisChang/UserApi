package tw.com.firstbank.fcbcore.fir.service.adapter.in.rest.api;

import java.util.Date;
import lombok.Data;

@Data
public class UpdateUserRequest {
  private String businessCategory;
  private String verificationCode;
  private String firstName;
  private String lastName;
  private Date birthday;
  private String email;
  private String phone;

}
