package no.scriptotek.bibsys.loans;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.EventLog;

public class Loan {

    // No need to be bitwise - they're mutually exclusive
    public static final int STATE_COMPLETED = -1;
    public static final int STATE_FAILED = 1;
    public static final int STATE_QUEUED = 2;
    public static final int STATE_IN_PROGRESS = 3;

    private ContentProviderClient client;
    private Uri contentUri;
	private Uri localUri;

	// Begin content data
    private String loan_type;
    private String title;
    private String order_date;
	private String loan_date;
	private String res_date;
	private String due_date;
	private String avail_date;
	private String patron_id;
	private String loan_id;
	private String dokid;
	private String objektid;
	private String henteinfo;
	private String location;
	private String renewable;
	private String number_renewed;
	private String status;
	private String original_date;
    // End content data

	private int state;
    private long transferred;

    private boolean isMultiple;

    public boolean getMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    //public EventLog.LogBuilder event;

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(contentUri, flags);
        // Begin content data
        parcel.writeString(loan_type);
        parcel.writeString(title);
        parcel.writeSerializable(order_date);
        // End content data
        parcel.writeInt(state);
        parcel.writeLong(transferred);
        parcel.writeInt(isMultiple ? 1 : 0);
    }

    public Loan(Parcel in) {
        
    	contentUri = (Uri)in.readParcelable(Uri.class.getClassLoader());
        // Begin content data
        loan_type = in.readString();
        title = in.readString();
        order_date = in.readString();
        // End content data
        state = in.readInt();
        transferred = in.readLong();
        isMultiple = in.readInt() == 1;

    }

    public long getTransferred() {
        return transferred;
    }

    public void setTransferred(long transferred) {
        this.transferred = transferred;
    }

    public Uri getContentUri() {
        return contentUri;
    }

    public String getOrderDate() {
        return order_date;
    }

    public Loan(Uri localUri, String remoteUri, String filename, String description, long dataLength, Date dateCreated, Date dateUploaded, String creator, String editSummary, String title) {
        this.title = title;
        //timestamp = new Date(System.currentTimeMillis());
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setOrderDate(String date) {
        this.order_date = date;
    }

    public String getPageContents() {
        StringBuffer buffer = new StringBuffer();
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
        /*buffer
            .append("== {{int:filedesc}} ==\n")
                .append("{{Information\n")
                    .append("|description=").append(description).append("\n")
                    .append("|source=").append("{{own}}\n")
                    .append("|author=[[User:").append(creator).append("]]\n");
        if(dateCreated != null) {
            buffer
                    .append("|date={{According to EXIF data|").append(isoFormat.format(dateCreated)).append("}}\n");
        }
        buffer
                .append("}}").append("\n")
            .append("== {{int:license-header}} ==\n")
                .append("{{self|cc-by-sa-3.0}}\n\n")
            .append("{{Uploaded from Mobile|platform=Android|version=").append(NfcBookScanner.APPLICATION_VERSION).append("}}\n")
            .append("{{subst:unc}}");  // Remove when we have categorization
        return buffer.toString();*/
        return "Hello";
    }

    public void setContentProviderClient(ContentProviderClient client) {
        this.client = client;
    }

    public void save() {
        try {
            if(contentUri == null) {
                contentUri = client.insert(LoansContentProvider.BASE_URI, this.toContentValues());
            } else {
                client.update(contentUri, toContentValues(), null, null);
            }
        } catch(RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        try {
            if(contentUri == null) {
                // noooo
                throw new RuntimeException("tried to delete item with no content URI");
            } else {
                client.delete(contentUri, null, null);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Table.COLUMN_LOANTYPE, getLoanType());
        /*if(getLocalUri() != null) {
            cv.put(Table.COLUMN_LOCAL_URI, getLocalUri().toString());
        }
        /*if(getImageUrl() != null) {
            cv.put(Table.COLUMN_IMAGE_URL, getImageUrl().toString());
        }
        if(getDateUploaded() != null) {
            cv.put(Table.COLUMN_UPLOADED, getDateUploaded().getTime());
        }
        cv.put(Table.COLUMN_LENGTH, getDataLength());
        cv.put(Table.COLUMN_TIMESTAMP, getTimestamp().getTime());
        cv.put(Table.COLUMN_STATE, getState());
        cv.put(Table.COLUMN_TRANSFERRED, transferred);
        cv.put(Table.COLUMN_SOURCE,  source);
        cv.put(Table.COLUMN_DESCRIPTION, description);
        cv.put(Table.COLUMN_CREATOR, creator);
        cv.put(Table.COLUMN_MULTIPLE, isMultiple ? 1 : 0); */
        return cv;
    }
    
    public void setLoanType(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoanType() {
        return this.loan_type;
    }

    /*public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }*/

    public Loan() {
        //timestamp = new Date(System.currentTimeMillis());
    }

    public static Loan fromCursor(Cursor cursor) {
        // Hardcoding column positions!
        Loan c = new Loan();
        c.contentUri = LoansContentProvider.uriForId(cursor.getInt(0));
        c.loan_type = cursor.getString(1);
        c.order_date = cursor.getString(2);
        c.loan_date = cursor.getString(3);
        c.res_date = cursor.getString(4);
        c.due_date = cursor.getString(5); // new Date()
        c.avail_date = cursor.getString(6);
        c.patron_id = cursor.getString(7);
        c.loan_id = cursor.getString(8);
        c.dokid = cursor.getString(9);
        c.objektid = cursor.getString(10);
        c.henteinfo = cursor.getString(11);
        c.title = cursor.getString(12);
        c.location = cursor.getString(13);
        c.renewable = cursor.getString(14);
        c.number_renewed = cursor.getString(15);
        c.original_date  = cursor.getString(16);
        c.status = cursor.getString(17);
        return c;
    }
/*
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }*/

    public void setLocalUri(Uri localUri) {
        this.localUri = localUri;
    }


    public static class Table {
        public static final String TABLE_NAME = "loans";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_LOANTYPE = "loan_type";
        public static final String COLUMN_ORDER_DATE = "order_date";
        public static final String COLUMN_LOAN_DATE = "loan_date";
        public static final String COLUMN_RES_DATE = "res_date";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_AVAIL_DATE = "avail_date";
        public static final String COLUMN_PATRON_ID = "patron_id";
        public static final String COLUMN_LOAN_ID = "loan_id"; // Currently transferred number of bytes
        public static final String COLUMN_DOKID = "dokid";
        public static final String COLUMN_OBJEKTID = "objektid";
        public static final String COLUMN_HENTEINFO = "henteinfo"; // Initial uploader
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_RENEWABLE = "renewable";
        public static final String COLUMN_NUMBER_RENEWED = "number_renewed";
        public static final String COLUMN_ORIGINAL_DATE = "original_date";
        public static final String COLUMN_STATUS = "status";

        // NOTE! KEEP IN SAME ORDER AS THEY ARE DEFINED UP THERE. HELPS HARD CODE COLUMN INDICES.
        public static final String[] ALL_FIELDS = {
                COLUMN_ID,
                COLUMN_LOANTYPE,
                COLUMN_ORDER_DATE,
                COLUMN_LOAN_DATE,
                COLUMN_RES_DATE,
                COLUMN_DUE_DATE,
                COLUMN_AVAIL_DATE,
                COLUMN_PATRON_ID,
                COLUMN_LOAN_ID,
                COLUMN_DOKID,
                COLUMN_OBJEKTID,
                COLUMN_HENTEINFO,
                COLUMN_TITLE,
                COLUMN_LOCATION,
                COLUMN_RENEWABLE,
                COLUMN_NUMBER_RENEWED,
                COLUMN_ORIGINAL_DATE,
                COLUMN_STATUS
        };


        private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY,"
                + "loan_type STRING,"
                + "order_date STRING,"
                + "loan_date STRING,"
                + "res_date STRING,"
                + "due_date STRING,"
                + "avail_date STRING,"
                + "patron_id STRING,"
                + "loan_id STRING,"
                + "dokid STRING,"
                + "objektid STRING,"
                + "hentinfo STRING,"
                + "title STRING,"
                + "location STRING,"
                + "renewable INTEGER,"
                + "number_renewed INTEGER,"
                + "original_date STRING,"
                + "status STRING"
        + ");";


        public static void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_STATEMENT);
        }

        public static void onUpdate(SQLiteDatabase db, int from, int to) {
            if(from == to) {
                return;
            }
            /*
            if(from == 1) {
                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN description STRING;");
                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN creator STRING;");
                from++;
                onUpdate(db, from, to);
                return;
            }
            if(from == 2) {
                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN multiple INTEGER;");
                db.execSQL("UPDATE " + TABLE_NAME + " SET multiple = 0");
                from++;
                onUpdate(db, from, to);
                return;
            }
            if(from == 3) {
                // Do nothing
                from++;
                return;
            }
            if(from == 4) {
                // Do nothing -- added Category
                from++;
                return;
            }
            */
        }
    }
}
