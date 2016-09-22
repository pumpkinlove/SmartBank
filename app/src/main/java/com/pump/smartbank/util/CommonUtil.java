package com.pump.smartbank.util;

import android.app.FragmentManager;

import com.pump.smartbank.domain.Config;
import com.pump.smartbank.view.JoshuaDialog;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by xu.nan on 2016/8/29.
 */
public class CommonUtil {

    public static JoshuaDialog MyAlert(String content, FragmentManager fragmentManager, String tag){
        JoshuaDialog dialog = new JoshuaDialog();
        dialog.setContent(content);
        dialog.setErrorFlag(true);
        dialog.show(fragmentManager, tag);
        return dialog;
    }

    public static Config getConfig(){
        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
        DbManager dbManager = x.getDb(daoConfig);
        try {
            Config config = dbManager.findFirst(Config.class);
            return config;
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }
}
