package no.scriptotek.bookscanner;

import no.scriptotek.nfcbookscanner.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateUserActivity extends Activity {

    private static final String TAG = "CreateUserActivity";
    private AccountManager mAccountManager;

    String url = "http://nfcat.biblionaut.net/users/create";
    Uri u = Uri.parse(url);
    Intent i = new Intent(Intent.ACTION_VIEW);
    Context context = this;
    WebView webview;
    boolean isWorking = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Called onCreate");

        mAccountManager = AccountManager.get(this);

        Intent intent = getIntent();
        
        setContentView(R.layout.createuser);

        webview = (WebView) findViewById(R.id.webView1);
        webview.setBackgroundColor(Color.BLACK);
        webview.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;

        webview.addJavascriptInterface(this, "JSInterface");

//        webview.setWebChromeClient(new WebChromeClient() {
//          public void onProgressChanged(WebView view, int progress) {
//            // Activities and WebViews measure progress with different scales.
//            // The progress meter will automatically disappear when we reach 100%
//        	  if (progress == 100) {
//        		  //setWorking(false);
//        	  } else {
//        		  //setWorking(true);        		
//        	  }
//          }
//        });

        webview.setWebViewClient(new WebViewClient() {
          public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
          }
        });
        webview.loadUrl(url);
        
    }
    
    @JavascriptInterface
    public String helloDroid() {
    	//Toast.makeText(this, "Saying hello", Toast.LENGTH_SHORT).show();
    	return "ehlo";
    }

    @JavascriptInterface
    public void storeNewDeviceKey(String ltid, String key) {
    	
    	Log.i(TAG, "storeNewDeviceKey()");
        
    	final Account account = new Account(ltid, Constants.ACCOUNT_TYPE);
    	Log.i(TAG, "step1()");
        mAccountManager.addAccountExplicitly(account, key, null);
    	Log.i(TAG, "stored()");

    	Toast.makeText(this, "Key stored", Toast.LENGTH_SHORT).show();

    }

    @JavascriptInterface
    public void closeWizard() {
    	final Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
        startActivity(mainActivity);
    }

}
