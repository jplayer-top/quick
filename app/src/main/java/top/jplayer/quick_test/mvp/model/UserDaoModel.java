package top.jplayer.quick_test.mvp.model;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.quick_test.greendao.DaoManager;
import top.jplayer.quick_test.greendao.UserBeanDao;
import top.jplayer.quick_test.mvp.model.bean.UserBean;

/**
 * Created by Obl on 2018/8/10.
 * top.jplayer.quick_test.mvp.model
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class UserDaoModel {
    private DaoManager mManager;

    public UserDaoModel(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成bean记录的插入，如果表未创建，先创建bean表
     *
     * @param bean
     * @return
     */
    public boolean insertUser(UserBean bean) {
        boolean flag = false;
        flag = mManager.getDaoSession().getUserBeanDao().insert(bean) != -1;
        LogUtil.e(bean);
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     */
    public boolean insertMultUser(final List<UserBean> beanList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (UserBean bean : beanList) {
                        mManager.getDaoSession().insertOrReplace(bean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @return
     */
    public boolean updatebean(UserBean bean) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param bean
     * @return
     */
    public boolean deleteUserBean(UserBean bean) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(bean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(UserBean.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<UserBean> queryAllbean() {
        return mManager.getDaoSession().loadAll(UserBean.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public UserBean querybeanById(long key) {
        return mManager.getDaoSession().load(UserBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<UserBean> querybeanByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(UserBean.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     */
    public List<UserBean> querybeanByQueryBuilder(long id) {
        QueryBuilder<UserBean> queryBuilder = mManager.getDaoSession().queryBuilder(UserBean.class);
        return queryBuilder.where(UserBeanDao.Properties.Id.eq(id)).list();
    }
}
