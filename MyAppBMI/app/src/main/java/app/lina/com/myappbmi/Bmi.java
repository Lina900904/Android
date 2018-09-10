package app.lina.com.myappbmi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Bmi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi);
        final class Bm {
            double bmi, wei, hei;
            String res;
            public void result(){
                 bmi = (wei / ((hei*hei)/10000));
                if(bmi>=18.5 && bmi<23.0){
                    res = "정상";
                }else if(bmi>=23.0 && bmi<25.0){
                    res = "비만 전단계";
                }else if(bmi>=25.0 && bmi<30.0){
                    res = "비만";
                }else if(bmi>=30.0 && bmi<35.0){
                    res = "1단계 비만";
                }else if(bmi<18.5){
                    res = "저체중";
                }else{
                    res = "3단계 비만";
                }
            }

        }

        final Context this__ = Bmi.this;
        final EditText weight  = findViewById(R.id.weight);
        final EditText height  = findViewById(R.id.height);
        final TextView result = findViewById(R.id.result);
        findViewById(R.id.btn).setOnClickListener(
                (View v) -> {
                    double wei= Double.parseDouble(weight.getText().toString());
                    double hei= Double.parseDouble(height.getText().toString());
                    Bm b = new Bm();
                    b.wei = wei;
                    b.hei = hei;
                    b.result();
                    result.setText("BMI 단계:" + b.res);
                    Toast.makeText(this__, b.res, Toast.LENGTH_LONG).show();


                }
        );

    }
}