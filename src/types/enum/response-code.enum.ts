enum ResponseCode {
    //HTTP Status 200
    SUCCESS = "SU",

    //HTTP Status 400
    MISSING_ID = "MI",
    DUPLICATE_ID = "DI",
    MISSING_PASSWORD = "MP",
    MISSING_PASSWORD_CHECK = "MPC",
    MISSING_NAME = "MN",
    DUPLICATE_NAME = "DN",
    NOT_EXISTED_POST = "NP",
    NOT_EXISTED_USER = "NU",

    //HTTP Status 401
    LOGIN_FAIL = "LF",
    AUTHORIZATION_FAIL = "AF",

    //HTTP Status 403
    NO_PERMISSION = "NP",

    //HTTP Status 500
    DATABASE_ERROR = "DE",
}

export default ResponseCode;