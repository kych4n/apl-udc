package apl.udc.api.controller;

import apl.udc.api.dto.request.InfoForFilteringRequest;
import apl.udc.api.dto.response.AttributeResponse;
import apl.udc.api.facade.MainFacade;
import apl.udc.global.common.BaseResponse;
import apl.udc.global.message.SuccessMessage;
import apl.udc.global.util.ApiResponseUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {

    private final MainFacade mainFacade;

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getAttributes(@RequestHeader("Authorization") String authorization)
            throws IOException {
        AttributeResponse attributes = mainFacade.getAttributes(authorization);
        return ApiResponseUtil.success(SuccessMessage.SUCCESS, attributes);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<?>> useDecryptedData(
            @RequestHeader("Authorization") String authorization,
            @RequestBody @Validated InfoForFilteringRequest infoForFilteringRequest)
            throws Exception {
        String encryptedDataUrl = mainFacade.getEncryptedDataUrl(authorization, infoForFilteringRequest);
        mainFacade.download(encryptedDataUrl);
        mainFacade.decrypt();
        mainFacade.done(authorization);
        return ApiResponseUtil.success(SuccessMessage.SUCCESS);
    }

}
