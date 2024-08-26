package project.server.dto.response.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import project.server.common.ResponseCode;
import project.server.common.ResponseMessage;
import project.server.dto.response.ResponseDto;
import project.server.security.jwt.constants.JwtConstants;

import java.io.IOException;

@Getter
public class LoginResponseDto extends ResponseDto {

    private String token;
    private int expirationTime;

    private LoginResponseDto(String token) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        //response body 부분의 token 가리기
        this.token = token.substring(0,5)+"...";
        this.expirationTime = 518400;
    }

    //HTTP Status 200
    public static void success(HttpServletResponse response, String token) throws IOException {
        response.setContentType("application/json");
        response.setHeader(JwtConstants.TOKEN_HEADER, JwtConstants.TOKEN_PREFIX + token);
        response.setStatus(HttpServletResponse.SC_OK);

        LoginResponseDto dto = new LoginResponseDto(token);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), dto);
    }

    //HTTP Status 401
    public static void authorizationFail(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ResponseDto dto = new ResponseDto(ResponseCode.AUTHORIZATION_FAIL, ResponseMessage.AUTHORIZATION_FAIL);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), dto);
    }

    //HTTP Status 500
    public static void databaseError(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        ResponseDto dto = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), dto);
    }
}
