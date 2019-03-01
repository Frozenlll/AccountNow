package com.example.fran.accountnow.Adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.fran.accountnow.Data.Charge;
import com.example.fran.accountnow.R;

import java.util.List;

import static android.R.color.holo_green_light;
import static android.R.color.holo_red_light;

public class ChargeAdapter extends BaseQuickAdapter<Charge, BaseViewHolder> {

    public ChargeAdapter(@LayoutRes int layoutResId, @Nullable List<Charge> datas) {
        super(layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, Charge item) {
        switch (item.kind) {
            case 0:
                helper.setText(R.id.kind, "支出");
                helper.setTextColor(R.id.kind, mContext.getResources().getColor(holo_red_light));
                helper.setTextColor(R.id.cost, mContext.getResources().getColor(holo_red_light));
                break;
            case 1:
                helper.setText(R.id.kind, "收入");
                helper.setTextColor(R.id.kind, mContext.getResources().getColor(holo_green_light));
                helper.setTextColor(R.id.cost, mContext.getResources().getColor(holo_green_light));
                break;
        }
        helper.setText(R.id.cost, ""+item.cost);
        helper.setText(R.id.ps, item.ps);
        helper.setText(R.id.time, String.format("%d-%d-%d", item.year, item.month, item.date));
    }
}
