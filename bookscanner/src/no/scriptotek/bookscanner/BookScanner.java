package no.scriptotek.bookscanner;

import no.scriptotek.bibsys.data.DbOpenHelper;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.util.Log;

public class BookScanner extends Application {

    private DbOpenHelper dbOpenHelper;
    private Account currentAccount = null; // Unlike a savings account...
    
    public DbOpenHelper getDbOpenHelper() {
        if(dbOpenHelper == null) {
            dbOpenHelper = new DbOpenHelper(this);
        }
        return dbOpenHelper;
    }
    
    public Account getCurrentAccount() {
        if(currentAccount == null) {
            AccountManager accountManager = AccountManager.get(this);
            Account[] allAccounts = accountManager.getAccountsByType("no.scriptotek.bibsys");
            if(allAccounts.length != 0) {
                currentAccount = allAccounts[0];
            }
        }
        return currentAccount;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("BookScanner", "Application create");
    }
}
