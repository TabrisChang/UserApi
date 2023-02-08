package tw.com.firstbank.fcbcore.fir.service.application.in.user.impl;

import java.util.Calendar;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserRequestCommand;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.domain.user.type.StatusCode;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.UserService;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.api.CreateUserUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.application.in.user.mapper.UserUseCaseMapper;

@Slf4j
@AllArgsConstructor
@Service
public class CreateUserUseCaseImpl implements CreateUserUseCaseApi {

  private UserUseCaseMapper mapper;
  private UserService userService;

  @Override
  public CreateUserResponseCommand execute(CreateUserRequestCommand requestCommand) {
    CreateUserResponseCommand resp = new CreateUserResponseCommand();
    resp.setStatusCode(StatusCode.UNKNOWN_ERROR);

    try {
      // 驗證使用者資訊
      String statusCode = getStatusCodeByVerifyUserInfo(requestCommand);
      if (StatusCode.SUCCESS.equals(statusCode)) {
        String no = getUserNo();
        userService.createUser(mapper.toUserDto(no, requestCommand));
        resp = mapper.toCreateUserResponseCommand(StatusCode.SUCCESS, no, requestCommand.getBranchCode());
      } else {
        resp.setStatusCode(statusCode);
      }

    } catch (Exception ex) {
      log.error("Create User Error.", ex);
    }

    return resp;
  }

  private String getUserNo() {
    return userService.getUserNo();
  }

  /**
   * 驗證使用者資訊是否符合格式
   *
   * @return StatusCode
   */
  private String getStatusCodeByVerifyUserInfo(CreateUserRequestCommand requestCommand) {
    String statusCode = StatusCode.UNKNOWN_ERROR;
    try {
      if (!verifyUserBranchCode(requestCommand.getBranchCode())) {
        statusCode = StatusCode.BRANCH_CODE_ERROR;
      } else if (!verifyUserBusinessCategory(requestCommand.getBusinessCategory())) {
        statusCode = StatusCode.BUSINESS_CATE_ERROR;
      } else if (!verifyUserVerificationCode(requestCommand.getVerificationCode())) {
        statusCode = StatusCode.VERIFICATION_CODE_ERROR;
      } else if (!verifyStringLength(requestCommand.getFirstName(), 1, 60)) {
        statusCode = StatusCode.FIRST_NAME_ERROR;
      } else if (!verifyStringLength(requestCommand.getLastName(), 1, 60)) {
        statusCode = StatusCode.LAST_NAME_ERROR;
      } else if (!verifyOver18YearsOld(requestCommand.getBirthday())) {
        statusCode = StatusCode.BIRTHDAY_ERROR;
      } else if (!verifyEmail(requestCommand.getEmail())) {
        statusCode = StatusCode.EMAIL_ERROR;
      } else if (!verifyPhone(requestCommand.getPhone())) {
        statusCode = StatusCode.PHONE_ERROR;
      } else {
        statusCode = StatusCode.SUCCESS;
      }


    } catch (Exception ex) {
      log.error("Verify User Info Error.", ex);
    }
    return statusCode;
  }

  private boolean verifyUserBranchCode(String branchCode) {
    boolean result = false;
    if (branchCode != null) {
      if (verifyNumber(branchCode, 3) && Integer.parseInt(branchCode) >= 90 && Integer.parseInt(branchCode) <= 300) {
        result = true;
      }
    }
    return result;
  }

  private boolean verifyUserBusinessCategory(String businessCategory) {
    return verifyNumber(businessCategory, 2);
  }

  private boolean verifyUserVerificationCode(String verificationCode) {
    return verifyNumber(verificationCode, 1);
  }

  private boolean verifyNumber(String value, int length) {
    boolean result = false;
    if (value != null && value.matches("\\d{" + length + "}")) {
      result = true;
    }
    return result;
  }

  private boolean verifyStringLength(String value, int min, int max) {
    boolean result = false;
    if (value != null && value.length() >= min && value.length() <= max) {
      result = true;
    }
    return result;
  }

  private boolean verifyOver18YearsOld(Date date) {
    boolean result = false;
    Calendar calendar = Calendar.getInstance();
    Calendar birthday = DateUtils.toCalendar(date);
    int yearDiff = calendar.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
    if (yearDiff > 18) {
      result = true;
    } else if (yearDiff == 18) {
      int monthDiff = calendar.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
      if (monthDiff > 0) {
        result = true;
      } else if (monthDiff == 0) {
        if (calendar.get(Calendar.DATE) - birthday.get(Calendar.DATE) >= 0) {
          result = true;
        }
      }
    }
    return result;
  }

  private boolean verifyEmail(String email) {
    boolean result = false;

    if (email != null && email.matches(
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
      result = true;
    }
    return result;
  }

  private boolean verifyPhone(String phone) {
    boolean result = false;

    if (phone != null && phone.matches("09\\d{8}")) {
      result = true;
    }
    return result;
  }


}
