package app.lina.com.contactapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import static app.lina.com.contactapp.Main.*;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        Intent intent = this.getIntent();
        final Context ctx = MemberDetail.this;
        final String seq = intent.getExtras().getString("seq");
        Log.d("넘어온 seq",seq);
        ItemExit query = new ItemExit(ctx);
        query.seq = seq;

        findViewById(R.id.updateBtn).setOnClickListener((View v)->{
           Main.Member m =(Member) new UpdateService() {
                @Override
                public Object perform() {
                    return query.excute();
                }
            }.perform();

            Intent upIntent = new Intent(ctx, MemberUpdate.class);
            upIntent.putExtra("spec",m.seq+","+m.name+","+m.pw+","+ m.email+","+m.phone+","+m.addr+","+m.photo);
            Log.d("배열값",m.name+"");

            startActivity(upIntent);
        });

        findViewById(R.id.callBtn).setOnClickListener((View v)->{
        });
        findViewById(R.id.dialBtn).setOnClickListener((View v)->{
        });
        findViewById(R.id.smsBtn).setOnClickListener((View v)->{
        });
        findViewById(R.id.albumBtn).setOnClickListener((View v)->{
        });
        findViewById(R.id.movieBtn).setOnClickListener((View v)->{
        });
        findViewById(R.id.mapBtn).setOnClickListener((View v)->{
        });
        findViewById(R.id.musicBtn).setOnClickListener((View v)->{
        });
        findViewById(R.id.listBtn).setOnClickListener((View v)->{
        });






        Main.Member m= (Member)new RetrieveService() {
            @Override
            public Object perform() {
                return query.excute();
            }
        }.perform();
        Log.d("검색된 이름","");

        int prof=getResources().getIdentifier(this.getPackageName()
                +":drawable/"+m.photo,null,null);
        ImageView profile = findViewById(R.id.profile);
        profile.setImageDrawable(getResources().getDrawable(prof,ctx.getTheme()));
        TextView name = findViewById(R.id.name);
        name.setText(m.name);
        TextView phone =findViewById(R.id.phone);
        phone.setText(m.phone);
        TextView email = findViewById(R.id.email);
        email.setText(m.email);
        TextView addr = findViewById(R.id.addr);
        addr.setText(m.addr);
}
        private class MemberDetileQuery extends Main.QueryFactory{
            SQLiteOpenHelper helper;
            public MemberDetileQuery(Context ctx) {
                super(ctx);
                helper = new Main.SQLiteHelper(ctx);
            }

            @Override
            public SQLiteDatabase getDatabase() {
                return helper.getReadableDatabase();
            }
        }

        private  class ItemExit extends MemberDetileQuery{
            Main.Member m = new Main.Member();
            String seq;
            public ItemExit(Context ctx) {
                super(ctx);
            }

            public Main.Member excute() {
                Main.Member m = new Main.Member();
                Cursor c = this.getDatabase().rawQuery(
                        String.format(
                                " SELECT * FROM %s " +
                        " WHERE %s LIKE '%s' ",MEMTABLE,MEMSEQ,seq),null);
                if(c!=null){
                        if(c.moveToNext()){
                            m.seq = Integer.parseInt(c.getString(c.getColumnIndex(MEMSEQ)));
                            m.name = c.getString(c.getColumnIndex(MEMNAME));
                            m.pw = c.getString(c.getColumnIndex(MEMPW));
                            m.email = c.getString(c.getColumnIndex(MEMEAIL));
                            m.phone = c.getString(c.getColumnIndex(MEMPHONE));
                            m.addr = c.getString(c.getColumnIndex(MEMADDR));
                            m.photo = c.getString(c.getColumnIndex(MEMPHOTO));
                            Log.d("등록된 회원이",m.name+"");
                        }


                    }else{
                    Log.d("등록된 회원이","없다");

                    }
                return m;
                }



        }



        }
