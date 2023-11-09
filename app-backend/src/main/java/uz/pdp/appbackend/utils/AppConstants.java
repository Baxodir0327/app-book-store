package uz.pdp.appbackend.utils;

import uz.pdp.appbackend.controller.AttachmentController;
import uz.pdp.appbackend.controller.AuthController;

public interface AppConstants {
    String BEARER_TYPE = "Bearer";
    String BASIC_TYPE = "Basic";
    String AUTH_HEADER = "Authorization";
    String REFRESH_AUTH_HEADER = "RefreshToken";
    String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#_$%^&+=])(?=\\S+$).{8,}$";
    String[] OPEN_PAGES = {
            AuthController.BASE_PATH + "/**"
    };
    String[] GET_METHOD_OPEN_PAGES = {
            AttachmentController.BASE_PATH + "**"
    };

    static String QUERY(){
        return "";
    }
}
