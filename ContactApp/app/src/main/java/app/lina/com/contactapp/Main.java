package app.lina.com.contactapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context ctx = Main.this;

        findViewById(R.id.moveLogin).setOnClickListener((View v)-> {
            SQLiteHelper helper = new SQLiteHelper(ctx); //helper라는 객체가 곧 DB가 된다
            this.startActivity(new Intent(ctx, Login.class));
        });



    }
    static class Member{int seq; String name, pw, email, phone, addr, photo ;}
    static interface StatusService{public void perform();}
    static interface ListService{public List<?> perform();}
    static interface RetrieveService{public Object perform();}
    static interface UpdateService{public  Object perform();}

    static String DBNAME = "lina.db";
    static String MEMTABLE = "MEMBER";
    static String MEMSEQ="SEQ";
    static String MEMNAME = "NAME";
    static String MEMPW = "PW";
    static String MEMEAIL = "EMAIL";
    static String MEMPHONE = "PHONE";
    static String MEMADDR = "ADDR";
    static String MEMPHOTO = "PHOTO";
    static abstract class QueryFactory{
        Context ctx;
        public QueryFactory(Context ctx) {
            this.ctx = ctx;
        }
        public abstract SQLiteDatabase getDatabase();// 안드로이드의 내장된 객체
    }
    static class SQLiteHelper extends SQLiteOpenHelper{

        public SQLiteHelper(Context context) {
            super(context, DBNAME, null, 1);
            this.getWritableDatabase(); // 수정가능한 db
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format(
                    " CREATE TABLE IF NOT EXISTS %s " +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT, " +
                    " %s TEXT, " +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT," +
                    " %s TEXT  )" ,
                    MEMTABLE,MEMSEQ,MEMNAME,MEMPW,MEMEAIL,MEMPHONE,MEMADDR,MEMPHOTO);


            Log.d("실행할쿼리 : ",sql);


            db.execSQL(sql);
            String[] names = {"","김태리","손예진","수지","천우희","한지민"};
            String[] email={"","trif@gmail.com","yjin@gmail.com","suji@gmail.com","woohee@gmail.com","jimin@gmail.com"};
            for(int i=1;i<=5;i++){
                db.execSQL(String.format(
                        " INSERT INTO %s " +
                                " ( %s , " +
                                " %s , " +
                                " %s ," +
                                " %s ," +
                                " %s ," +
                                " %s   " +
                                " ) VALUES "+
                                " ( '%s' , " +
                                " '%s' , " +
                                " '%s' ," +
                                " '%s' ," +
                                " '%s' ," +
                                " '%s'  ) ",
                        MEMTABLE,MEMNAME,MEMPW,MEMEAIL,MEMPHONE,MEMADDR,MEMPHOTO,
                        names[i],"1",email[i],"010-1234-123"+i,"신촌","profile_"+i)); //db를 만들자마자 이쿼리를 실행해
            }
            Log.d("============= : ","쿼리실행완료");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MEMTABLE);
        }
    }


}
