package com.example.fran.accountnow;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.fran.accountnow.Fragment.ChargeFragment;
import com.example.fran.accountnow.Fragment.CountFragment;
import com.example.fran.accountnow.Fragment.SetFragment;
import com.scwang.smartrefresh.header.CircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(
                new DefaultRefreshHeaderCreater() {
                    @NonNull
                    @Override
                    public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                        layout.setPrimaryColorsId(android.R.color.darker_gray, android.R.color.white);
                        return new CircleHeader(context);
                    }
                }
        );
        SmartRefreshLayout.setDefaultRefreshFooterCreater(
                new DefaultRefreshFooterCreater() {
                    @NonNull
                    @Override
                    public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                        return new BallPulseFooter(context);
                    }
                }
        );
    }

    private ChargeFragment chargeFragment = new ChargeFragment();
    private CountFragment countFragment = new CountFragment();
    private SetFragment setFragment = new SetFragment();
    private Fragment currentFragment = chargeFragment;

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

        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayout, chargeFragment);
        fragmentTransaction.add(R.id.fragmentLayout, countFragment);
        fragmentTransaction.add(R.id.fragmentLayout, setFragment);
        fragmentTransaction.hide(countFragment);
        fragmentTransaction.hide(setFragment);
        fragmentTransaction.show(currentFragment).commit();
        getFragmentManager().executePendingTransactions();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.hide(currentFragment);
                        switch (item.getItemId()) {
                            case R.id.navigation_charge:
                                setTitle(R.string.app_name);
                                currentFragment = chargeFragment;
                                break;
                            case R.id.navigation_count:
                                setTitle(R.string.app_name);
                                //fragmentTransaction.remove(countFragment);
                                //countFragment = new CountFragment();
                                //fragmentTransaction.add(R.id.fragmentLayout, countFragment);
                                currentFragment = countFragment;
                                break;
                            case R.id.navigation_set:
                                setTitle(R.string.set);
                                currentFragment = setFragment;
                                break;
                        }
                        fragmentTransaction.show(currentFragment).commit();
                        getFragmentManager().executePendingTransactions();
                        return true;
                    }
                }
        );
    }
}
