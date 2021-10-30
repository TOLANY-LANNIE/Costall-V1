package com.example.costall.helper;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class Functions {

    //Main URL
    private static final String MAIN_URL = "http://192.168.88.49/Cost/public/";

    // Login URL
    public static String LOGIN_URL = MAIN_URL + "userInfo";

    // Register URL
    public static String REGISTER_URL = MAIN_URL + "newRegistration";

    // OTP Verification
    public static String OTP_VERIFY_URL = MAIN_URL + "verification.php";

    // Forgot Password
    public static String RESET_PASS_URL = MAIN_URL + "reset-password.php";
    public static final String URL_CREATE_SESSION = MAIN_URL+"createMeetingSession";

    public static final String URL_ADD_VENUE = MAIN_URL+"addVenue";
    public static final String URL_ADD_RESOURCE = MAIN_URL+"addResource";
    public static final String URL_ADD_ACTION= MAIN_URL+"addActionItem";
    public static final String URL_ADD_AGENDA_INIT= MAIN_URL+"addAgenda";
    public static final String URL_ADD_AGENDA= MAIN_URL+"";

    public static final String URL_GET_VENUE= MAIN_URL+"";

    public static final String URL_GET_RESOURCE_LIST= MAIN_URL+"getResources";
    public static final String URL_GET_AGENDA_LIST= MAIN_URL+"getAgendaItems";
    public static final String URL_GET_PARTICIPANTS_LIST= MAIN_URL+"getParticipants";
    public static final String URL_GET_ACTION_ITEM_LIST= MAIN_URL+"";
    public static final String URL_GET_MEETING_SESSION_LIST= MAIN_URL + "getMeetingSessions";

    public static final String URL_CREATE_PARTICIPANT= MAIN_URL + "addParticipants";
    public static final String GET_PARTICIPANTS_URL = MAIN_URL+"getParticipants/";

    public static final String URL_UPDATE_PARTICIPANT_COST = MAIN_URL + "updateParticipantCost";



    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public void logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
    }

    /**
     *  Email Address Validation
     */
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     *  Hide Keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static void hideProgressDialog(Context context) {
        FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }
}
