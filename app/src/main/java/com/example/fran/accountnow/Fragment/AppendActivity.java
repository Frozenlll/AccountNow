package com.example.fran.accountnow.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fran.accountnow.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.Date;
import java.util.List;

public class AppendActivity extends AppCompatActivity {


    private int kind = 0;
    private int year = new Date().getYear() + 1900;
    private int month = new Date().getMonth() + 1;
    private int date = new Date().getDate();

    private RadioButton kind0, kind1;
    private EditText costText;
    private EditText psText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndPermission.with(getParent()).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(
                        new Action() {
                            @Override
                            public void onAction(List<String> permissions) {

                            }
                        }
                )
                .onDenied(
                        new Action() {
                            @Override
                            public void onAction(List<String> permissions) {

                            }
                        }
                );

        setContentView(R.layout.activity_append);

        kind0 = (RadioButton) findViewById(R.id.kindButton0);
        kind1 = (RadioButton) findViewById(R.id.kindButton1);
        costText = (EditText) findViewById(R.id.cost);
        psText = (EditText) findViewById(R.id.ps);
        TextView dateText = (TextView) findViewById(R.id.date);
        dateText.setText(String.format("%d-%d-%d", year, month, date));
        Button confirm = (Button) findViewById(R.id.confirm);
        Button cancel = (Button) findViewById(R.id.cancel);

        kind0.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kind = 0;
                        kind1.setChecked(false);
                    }
                }
        );
        kind1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        kind = 1;
                        kind0.setChecked(false);
                    }
                }
        );

        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        float cost;
                        try {
                            cost = Float.valueOf(costText.getText().toString());
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "账单数目格式错误", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String ps = psText.getText().toString();
                        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.fran.accountnow/databases/charges.db", null);
                        db.execSQL("INSERT INTO charge VALUES(NULL, ?, ?, ?, ?, ?, ?)", new Object[]{kind, cost, year, month, date, ps});
                        Toast.makeText(getApplicationContext(), "添加成功，请刷新", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
        );

        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
}
