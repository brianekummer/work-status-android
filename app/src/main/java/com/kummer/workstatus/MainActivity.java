package com.kummer.workstatus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_activity);

        Window window = getWindow();

        // Always keep the screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Hide the status bar and navigation bar
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        // Do not force the app's orientation because then it won't auto-rotate,
        // and I don't want to in-code force it to be only SCREEN_ORIENTATION_LANDSCAPE
        // or SCREEN_ORIENTATION_REVERSE_PORTRAIT. Instead, I won't specify the
        // orientation and I'll let Android auto-rotate.
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Set up the webview
        WebView myWebView = findViewById(R.id.main_activity_webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url = prefs.getString("work_status_url", "");
        if (url.isEmpty()) {
            // If do not have the URL, navigate to settings activity so user can set it
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        } else {
            WebView myWebView = findViewById(R.id.main_activity_webview);
            myWebView.loadUrl(url);
        }
    }
}