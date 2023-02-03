package tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper;

import java.util.Date;
import lombok.Data;

@Data
public class UserDto {

  private String no;
  private String branchCode;
  private String businessCategory;
  private String verificationCode;
  private String firstName;
  private String lastName;
  private Date birthday;
  private String email;
  private String phone;

}
