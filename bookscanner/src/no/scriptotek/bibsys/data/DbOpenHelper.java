package no.scriptotek.bibsys.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import no.scriptotek.bibsys.loans.*;
//import org.wikimedia.commons.modifications.ModifierSequence;

public class DbOpenHelper  extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "loans.db";
    private static final int DATABASE_VERSION = 5;

    public DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
        Loan.Table.onCreate(db);
        //ModifierSequence.Table.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Loan.Table.onUpdate(db, oldVersion, newVersion);
        //ModifierSequence.Table.onUpdate(sqLiteDatabase, from, to);
        //Category.Table.onUpdate(sqLiteDatabase, from, to);
	}
}
