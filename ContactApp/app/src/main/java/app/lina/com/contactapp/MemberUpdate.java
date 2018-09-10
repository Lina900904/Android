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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import static app.lina.com.contactapp.Main.*;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_update);
        final Context ctx = MemberUpdate.this;
        EditText addr = findViewById(R.id.changeAddress);
        EditText email = findViewById(R.id.changeEmail);
        EditText phone = findViewById(R.id.changePhone);
        EditText name = findViewById(R.id.name);
        String[] spec = getIntent().getStringExtra("spec").split(",");
        /*seq+","+m.name+","+m.pw+","+ m.email+","+m.phone+","+m.addr+","+m.photo*/



        ImageView profile = findViewById(R.id.profileImg);
        int prof=getResources().getIdentifier(this.getPackageName()
                +":drawable/"+spec[6],null,null);
        profile.setImageDrawable(getResources().getDrawable(prof,ctx.getTheme()));

        name.setText(spec[1]);
        Log.d("이름은여",spec[1]+"");
        email.setText(spec[3]);
        phone.setText(spec[4]);
        addr.setText(spec[5]);

        findViewById(R.id.confirmBtn).setOnClickListener((View v)->{
            ItemExit query = new ItemExit(ctx);
            Log.d("이름은여",name.getText().toString()+"");
           query.m.name=(name.getText().toString().equals("")?
                    spec[1] : name.getText().toString());
            query.m.addr = (addr.getText().toString().equals("")?
                    spec[5] :addr.getText().toString());
            query.m.email = (email.getText().toString().equals("")?
                    spec[3] :email.getText().toString());
            query.m.phone = (phone.getText().toString().equals("")?
                    spec[6] :phone.getText().toString());
            query.m.seq = Integer.parseInt(spec[0]);
            Log.d("넘어온 seq",query.m.seq+"");

            Main.Member m=(Member)new UpdateService() {
                @Override
                public Object perform() {
                    return query.excute();
                }
            }.perform();
            Intent moveDetail = new Intent(ctx, MemberDetail.class);
            moveDetail.putExtra("seq",spec[0]+"");
            startActivity(moveDetail);
        });

        findViewById(R.id.cancelBtn).setOnClickListener((View v)->{
            Intent moveDetail = new Intent(ctx, MemberDetail.class);
            moveDetail.putExtra("seq",spec[0]);
            startActivity(moveDetail);
        });
    }

    private class MemberUpdateQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;
        public MemberUpdateQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);

        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }
    private class ItemExit extends MemberUpdateQuery{
        Main.Member m;


        public ItemExit(Context ctx) {
            super(ctx);
            m = new Member();
        }
        public Main.Member excute(){

            // pw, email, phone, addr, photo
            Cursor c = this.getDatabase().
                    rawQuery(
                    String.format(" UPDATE %s " +
                            " SET %s = '%s', " +
                            "  %s = '%s', "+
                            "  %s = '%s', " +
                            "  %s = '%s' " +
                            " WHERE %s LIKE '%s' "
                            ,MEMTABLE,MEMNAME,m.name,MEMADDR,m.addr,MEMEAIL,m.email,MEMPHONE,m.phone,MEMSEQ,m.seq),null);
            Log.d(" sql 왔다","");
            Main.Member m = new Main.Member();
            if (c!=null){
                while (c.moveToNext()){
                    // .seq = Integer.parseInt(c.getString(c.getColumnIndex(MEMSEQ)));
                    m.name = c.getString(c.getColumnIndex(MEMNAME));
                    m.email = c.getString(c.getColumnIndex(MEMEAIL));
                    m.phone = c.getString(c.getColumnIndex(MEMPHONE));
                    m.addr = c.getString(c.getColumnIndex(MEMADDR));
                    Log.d("등록된 회원이",m.name+"");
                }
            }



            return m;
        }
    }



}

