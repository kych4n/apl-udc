package apl.udc.api.controller;

import apl.udc.api.facade.AuthFacade;
import apl.udc.dto.response.SignInResponse;
import apl.udc.global.common.BaseResponse;
import apl.udc.global.message.SuccessMessage;
import apl.udc.global.util.ApiResponseUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/signin")
    public ResponseEntity<BaseResponse<?>> signIn() throws IOException {
        SignInResponse signInResponse = authFacade.signIn();
        return ApiResponseUtil.success(SuccessMessage.SUCCESS, signInResponse);
    }

}
