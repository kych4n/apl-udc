package apl.udc.global.exception;

import apl.udc.global.message.FailureMessage;

public class OdcServerErrorException extends AplException {

    public OdcServerErrorException(FailureMessage failureMessage) {
        super(failureMessage);
    }

    public static OdcServerErrorException wrong() {
        return new OdcServerErrorException(FailureMessage.ODC_SERVER_ERROR);
    }

}
