package project.server.common;

public interface ResponseCode {

    //HTTP Status 200
    String SUCCESS = "SU";

    //HTTP Status 400
    String MISSING_ID = "MI";
    String DUPLICATE_ID = "DI";
    String MISSING_NAME = "MN";
    String DUPLICATE_NAME = "DN";
    String MISSING_PASSWORD = "MP";
    String NOT_EXISTED_POST = "NP";
    String NOT_EXISTED_USER = "NU";
    String VALIDATION_FAILED = "VF";

    //HTTP Status 401
    String AUTHORIZATION_FAIL = "AF";

    //HTTP Status 403
    String NO_PERMISSION = "NP";

    //HTTP Status 500
    String DATABASE_ERROR = "DE";
}
