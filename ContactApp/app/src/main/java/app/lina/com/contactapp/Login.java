package app.lina.com.contactapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.lina.com.contactapp.Main.*;

public class Login extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Context ctx = Login.this;

        findViewById(R.id.loginBtn).setOnClickListener((View v)->{
            ItemExist query = new ItemExist(ctx);
            EditText x = findViewById(R.id.id);
            EditText y = findViewById(R.id.password);
            query.id = x.getText().toString();
            query.pw = y.getText().toString();
            new StatusService() {
                @Override
                public void perform() {
                    if(query.execute()){

                        Toast.makeText(ctx,"로그인성공",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ctx, MemberList.class));
                    }else{
                        Toast.makeText(ctx,"로그인실패",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ctx, Login.class));
                    }
                }
            }.perform();
        });
    }
    private  class LoginQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;
        public LoginQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemExist extends LoginQuery{
        String id, pw;

        public ItemExist(Context ctx) {
            super(ctx);
        }
        public boolean execute(){

            return  super.getDatabase().rawQuery(String.format(
                    " SELECT * FROM %s "+
                            " WHERE %s LIKE '%s' AND %s LIKE '%s' ",
                    MEMTABLE,
                    MEMSEQ,
                    id,
                    MEMPW,
                    pw
                    ), null).moveToNext();

        }
    }


}
