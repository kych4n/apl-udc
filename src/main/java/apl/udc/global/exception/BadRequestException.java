package apl.udc.global.exception;

import apl.udc.global.message.FailureMessage;

public class BadRequestException extends AplException {

	public BadRequestException(FailureMessage failureMessage) {
		super(failureMessage);
	}

	public static BadRequestException wrong() {
		return new BadRequestException(FailureMessage.BAD_REQUEST);
	}

}