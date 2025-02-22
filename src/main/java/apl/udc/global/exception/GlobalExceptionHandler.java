package apl.udc.global.exception;

import apl.udc.global.common.BaseResponse;
import apl.udc.global.message.FailureMessage;
import apl.udc.global.util.ApiResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<?>> handleBadRequestException(BadRequestException e) {
        return ApiResponseUtil.failure(FailureMessage.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        return ApiResponseUtil.failure(FailureMessage.TYPE_MISMATCH);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<BaseResponse<?>> handleUnauthorizedException(UnauthorizedException e) {
        return ApiResponseUtil.failure(FailureMessage.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleNotFoundException(NotFoundException e) {
        return ApiResponseUtil.failure(FailureMessage.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return ApiResponseUtil.failure(FailureMessage.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(OdcServerErrorException.class)
    public ResponseEntity<BaseResponse<?>> handleOdcServerErrorException(
            OdcServerErrorException e) {
        return ApiResponseUtil.failure(FailureMessage.ODC_SERVER_ERROR);
    }

}
