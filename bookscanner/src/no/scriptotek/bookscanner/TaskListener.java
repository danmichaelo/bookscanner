package no.scriptotek.bookscanner;

import org.json.JSONObject;

public interface TaskListener {
    void onLoggedIn(String result);
    void onReceivedUserInfo(JSONObject json);
}
