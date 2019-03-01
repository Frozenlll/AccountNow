package com.example.fran.accountnow.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fran.accountnow.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class CountFragment extends MyFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, container, false);

        AndPermission.with(getActivity()).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
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

        TextView totalincome = (TextView)view.findViewById(R.id.totalincome);
        TextView totalexpend = (TextView)view.findViewById(R.id.totalexpend);
        ColumnChartView columnChartView = (ColumnChartView)view.findViewById(R.id.chart);

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.fran.accountnow/databases/charges.db", null);
        float[] temp = new float[32];
        float temp1 = 0, temp2 = 0;
        Cursor cursor = db.query("charge", new String[]{"kind", "cost", "year", "month", "date",}, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            Date date = new Date();
            for (int i = cursor.getCount(); i > 0; i--) {
                switch (cursor.getInt(0)) {
                    case 1:
                        temp1 += cursor.getFloat(1);
                        break;
                    case 0:
                        temp2 += cursor.getFloat(1);
                        if(cursor.getInt(2) == date.getYear()+1900 && cursor.getInt(3) == date.getMonth()+1)
                            temp[cursor.getInt(4)] += cursor.getFloat(1);
                        break;
                }
                cursor.moveToNext();
            }
        }
        totalincome.setText(""+temp1);
        totalexpend.setText(""+temp2);

        List<Column> columns = new ArrayList<Column>();
        for(int i = 0; i <= 31; i++) {
            List<SubcolumnValue> values = new ArrayList<SubcolumnValue>();
            values.add(new SubcolumnValue((float)Math.random() * 10, ChartUtils.pickColor()).setTarget(temp[i]));
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }
        ColumnChartData columnChartData = new ColumnChartData(columns);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("日期");
        axisY.setName("支出");
        columnChartData.setAxisXBottom(axisX);
        columnChartData.setAxisYLeft(axisY);
        columnChartView.startDataAnimation();
        columnChartView.setColumnChartData(columnChartData);

        return view;
    }
}
