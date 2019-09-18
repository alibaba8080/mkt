package com.pst.mkt.ui.indexf;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.pst.mkt.R;
import skin.support.SkinCompatManager;

/**
 * @ProjectName: AndroidSkinDemo
 * @Package: com.itfitness.androidskindemo
 * @ClassName: ChangeSkinActivity
 * @Description: java类作用描述
 * @Author: LML
 * @CreateDate: 2018/9/19 16:03
 * @UpdateUser: 更新者：
 * @UpdateDate: 2018/9/19 16:03
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

@SuppressLint("Registered")
public class ChangeSkinActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar activityChangeskinToolbar;
    private ImageView activityChangeskinToolbarImgBack;
    private CardView activityChangeskinCdBlue;
    private CardView activityChangeskinCdBlack;
    private CardView activityChangeskinCdRed;
    private CardView activityChangeskinCdYellow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeskin);
        activityChangeskinToolbar = (Toolbar) findViewById(R.id.activity_changeskin_toolbar);
        activityChangeskinToolbarImgBack = (ImageView) findViewById(R.id.activity_changeskin_toolbar_img_back);
        activityChangeskinCdBlue = (CardView) findViewById(R.id.activity_changeskin_cd_blue);
        activityChangeskinCdBlack = (CardView) findViewById(R.id.activity_changeskin_cd_black);
        activityChangeskinCdRed = (CardView) findViewById(R.id.activity_changeskin_cd_red);
        activityChangeskinCdYellow = (CardView) findViewById(R.id.activity_changeskin_cd_yellow);
        setSupportActionBar(activityChangeskinToolbar);
        activityChangeskinToolbarImgBack.setOnClickListener(this);
        activityChangeskinCdBlue.setOnClickListener(this);
        activityChangeskinCdBlack.setOnClickListener(this);
        activityChangeskinCdRed.setOnClickListener(this);
        activityChangeskinCdYellow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_changeskin_toolbar_img_back:
                finish();
                break;
            case R.id.activity_changeskin_cd_blue:
                SkinCompatManager.getInstance().restoreDefaultTheme();
                break;
            case R.id.activity_changeskin_cd_black:
                SkinCompatManager.getInstance().loadSkin("night", null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                break;
            case R.id.activity_changeskin_cd_red:
                SkinCompatManager.getInstance().loadSkin("red", null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                break;
            case R.id.activity_changeskin_cd_yellow:
                SkinCompatManager.getInstance().loadSkin("yellow", null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                break;
        }
    }
}
