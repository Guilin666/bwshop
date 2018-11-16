package bwie.example.com.bwshop.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import bwie.example.com.bwshop.greendao.DaoMaster;
import bwie.example.com.bwshop.greendao.DaoSession;
import bwie.example.com.bwshop.model.ChacheBean;

public class SqlLiteUtils {

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public void init(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "gui");
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    //添加
    public void insert(ChacheBean chacheBean){
        daoSession.getChacheBeanDao().insert(chacheBean);
    }

    //添加2
    public void insertData(ChacheBean chacheBean){
        daoSession.getChacheBeanDao().insert(chacheBean);
    }


    //删除
    public void deleteAll(){
        daoSession.getChacheBeanDao().deleteAll();
    }

    //查询
    public List<ChacheBean> selectAll(){
        List<ChacheBean> chacheBeans = daoSession.getChacheBeanDao().loadAll();
        return chacheBeans;
    }




}
