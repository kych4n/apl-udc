package apl.udc.global.util;

import apl.udc.global.common.BaseResponse;
import apl.udc.global.message.FailureMessage;
import apl.udc.global.message.SuccessMessage;
import org.springframework.http.ResponseEntity;

public interface ApiResponseUtil {
    static ResponseEntity<BaseResponse<?>> success(SuccessMessage successMessage) {
        return ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage));
    }

    static <T> ResponseEntity<BaseResponse<?>> success(SuccessMessage successMessage, T data) {
        return ResponseEntity.status(successMessage.getHttpStatus())
                .body(BaseResponse.of(successMessage, data));
    }

    static ResponseEntity<BaseResponse<?>> failure(FailureMessage failureMessage) {
        return ResponseEntity.status(failureMessage.getHttpStatus())
                .body(BaseResponse.of(failureMessage));
    }

    static <T> ResponseEntity<BaseResponse<?>> failure(FailureMessage failureMessage, T data) {
        return ResponseEntity.status(failureMessage.getHttpStatus())
                .body(BaseResponse.of(failureMessage, data));
    }
}