package app.lina.com.contactapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static app.lina.com.contactapp.Main.*;

public class MemberList extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);
        final Context ctx = MemberList.this;
        ItemList query = new ItemList(ctx);
        ItemExit queryDelete = new ItemExit(ctx);
        ListView memberList = findViewById(R.id.memberList);
        memberList.setAdapter(new MemberAdapter(ctx,(ArrayList<Member>)new ListService(){
            @Override
            public List<?> perform() {
                return query.excute();
            }
        }.perform()));
        memberList.setOnItemClickListener(
                (AdapterView<?> p, View v, int i, long l)->{
                    Intent intent = new Intent(ctx,MemberDetail.class);
                    Main.Member m = (Member) memberList.getItemAtPosition(i);
                    intent.putExtra("seq",m.seq+"");
                    startActivity(intent);
        }
        );
        memberList.setOnItemLongClickListener(
                (AdapterView <?> p, View v, int i, long l)->{
                    Toast.makeText(ctx,"길게~~~Q",Toast.LENGTH_LONG).show();
                    Main.Member m = (Member) memberList.getItemAtPosition(i);
                    new AlertDialog.Builder(ctx).setTitle("DELETE").setMessage("정말로 삭제 하겠습니까?")
                            .setPositiveButton(android.R.string.yes,
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ctx,"삭제실행",Toast.LENGTH_LONG).show();
                                    ItemExit query =new ItemExit(ctx);
                                    query.m.seq = m.seq;
                                   Log.d("삭제 seq::",m.seq+"");
                                    new StatusService() {
                                        @Override
                                        public  void perform() {
                                             query.excute();
                                        }
                                    }.perform();
                                }

                            }).show();
                    Toast.makeText(ctx,"길게~~~",Toast.LENGTH_LONG).show();

                    return true;
                }

        );
    }
    private  class MemberListQuery extends QueryFactory {
        SQLiteOpenHelper helper;
        public MemberListQuery(Context ctx) {
            super(ctx);
            helper = new SQLiteHelper(ctx);
        }
        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemList extends MemberListQuery {
        Main.Member m ;
        String id, pw;
        public ItemList(Context ctx) {
            super(ctx);
        }
        public ArrayList<Member> excute(){
            ArrayList<Member> list =new ArrayList<>();
            Cursor c = this.getDatabase().rawQuery(
                    " SELECT * FROM MEMBER ",null
            );

            if(c!= null){
                while (c.moveToNext()){
                    m = new Member();
                    //name, pw, email, phone, addr, photo
                    m.seq = Integer.parseInt(c.getString(c.getColumnIndex(MEMSEQ)));
                    m.name = c.getString(c.getColumnIndex(MEMNAME));
                    m.pw = c.getString(c.getColumnIndex(MEMPW));
                    m.email = c.getString(c.getColumnIndex(MEMEAIL));
                    m.phone = c.getString(c.getColumnIndex(MEMPHONE));
                    m.addr = c.getString(c.getColumnIndex(MEMADDR));
                    m.photo = c.getString(c.getColumnIndex(MEMPHOTO));
                    list.add(m);
                }
                Log.d("등록된 회원이",list.size()+"");
            }else{
                Log.d("등록된 회원이","없습니다");
            }
            return list;

        }
    }
    private class MemberAdapter extends BaseAdapter{
        ArrayList<Member> list;
        LayoutInflater inflater;

        public MemberAdapter(Context ctx,ArrayList<Member> list) { //외부에서 전달받는것
            this.list = list;
            this.inflater = LayoutInflater.from(ctx);
        }
        private int[] photos ={
                R.drawable.profile_1,
                R.drawable.profile_2,
                R.drawable.profile_3,
                R.drawable.profile_4,
                R.drawable.profile_5
        };

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup p) {
            ViewHolder holder;
            if(v==null){
                v = inflater.inflate(R.layout.member_item, null);
                holder = new ViewHolder();
                holder.profile=v.findViewById(R.id.profile);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);
            }else{
                holder=(ViewHolder)v.getTag();
            }
            holder.profile.setImageResource(photos[i]);
            holder.name.setText(list.get(i).name);
            holder.phone.setText(list.get(i).phone);

            return v;
        }
    }
    static class ViewHolder{
        ImageView profile;
        TextView name, phone;
    }

    private class MemberDeleteQuery extends Main.QueryFactory{

        SQLiteOpenHelper helper;
        public MemberDeleteQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
            Log.d("MemberDeleteQuery 들어옴~~","");
        }

        @Override
        public SQLiteDatabase getDatabase() {
            Log.d("SQLiteDatabase 들어옴~~","");

            return helper.getWritableDatabase();
        }
    }
    private class ItemExit extends MemberDeleteQuery{

        Main.Member m ;
        public ItemExit(Context ctx) {

            super(ctx);
            m = new Member();
            Log.d("EXIT들어옴~~","");
        }

        public void excute() {
            Log.d("excut들어옴~~","");
            this.getDatabase().execSQL(
                    String.format(
                            " DELETE FROM %s "+
                            " WHERE %s LIKE  '%s' ",
                            MEMTABLE, MEMSEQ, m.seq));
            Log.d("EXIT밖에들어옴~~","");

        }


    }




    }







