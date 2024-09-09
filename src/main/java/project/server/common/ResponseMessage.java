package project.server.common;

public interface ResponseMessage {

    //HTTP Status 200
    String SUCCESS = "Success.";

    //HTTP Status 400
    String MISSING_ID = "Login ID is missing.";
    String DUPLICATE_ID = "Duplicate Login ID.";
    String MISSING_NAME = "Name is missing.";
    String DUPLICATE_NAME = "Duplicate Name.";
    String MISSING_PASSWORD = "Password is missing.";
    String NOT_EXISTED_POST = "This post does not exist.";
    String NOT_EXISTED_USER = "This user does not exist.";
    String VALIDATION_FAILED = "Validation failed.";

    //HTTP Status 401
    String AUTHORIZATION_FAIL = "Authorization failed.";

    //HTTP Status 403
    String NO_PERMISSION = "Do not have permission.";

    //HTTP Status 500
    String DATABASE_ERROR = "Database error.";
}
