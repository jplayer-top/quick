package top.jplayer.baseprolibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.mvp.model.LocalModel;
import top.jplayer.baseprolibrary.mvp.model.LocalServer;
import top.jplayer.baseprolibrary.mvp.model.bean.LocalBean;
import top.jplayer.baseprolibrary.net.retrofit.NetCallBackObserver;
import top.jplayer.baseprolibrary.net.tip.LoaddingErrorImplTip;

/**
 * Created by Obl on 2018/3/19.
 * top.jplayer.baseprolibrary.utils
 */

public class PickerUtils {
    public TimePickerView mTimePickerView;
    public OptionsPickerView mPickerView;
    private ArrayList<SelectLocalBean> optionsLocalProItems = new ArrayList<>();
    private ArrayList<ArrayList<SelectLocalBean>> optionsLocalCityItems = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<SelectLocalBean>>> optionsAreaItems = new ArrayList<>();
    private OptionsPickerView mLocalPickerView;
    private LocalBean mLocalBean;

    public void initTimePicker(Context context, OnSelectTimeListener listener) {
        initTimePicker(context, "yyyy-MM-dd", listener);
    }

    public void initTimePicker(Context context) {
        //"yyyy-MM-dd HH:mm:ss"
        initTimePicker(context, "yyyy-MM-dd", null);
    }

    public void initTimePicker(Context context, String pattern, OnSelectTimeListener listener) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        mTimePickerView = new TimePickerView.Builder(context, (date, v) -> {//选中事件回调
            String patternDate = patternDate(date, pattern);
            if (listener != null) {
                listener.onSelectTime(date, patternDate);
            }
            if (v != null) {
                TextView textView = (TextView) v;
                textView.setText(patternDate);
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }


    private void initStringPicker(final ArrayList<String> optionsItems, int position, Context context, OnSelectStringListener listener) {

        mPickerView = new OptionsPickerView.Builder(context,
                (options1, options2, options3, v) -> {
                    String value = optionsItems.get(options1);
                    if (listener != null) {
                        listener.onSelectString(options1, value);
                    }
                    if (v != null) {
                        TextView textView = (TextView) v;
                        textView.setText(value);
                    }

                })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setContentTextSize(21)//滚轮文字大小
                .setDividerColor(Color.DKGRAY)
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(position)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        mPickerView.setPicker(optionsItems);//添加数据源
    }

    private void initStringPicker(final ArrayList<String> optionsItems, int position, Context context) {
        initStringPicker(optionsItems, position, context, null);
    }


    public void responseLocalBean(LocalBean localBean) {
        mLocalBean = localBean;
        if (localBean != null && localBean.areas.size() > 0) {
            if (!(optionsLocalProItems.size() > 0 && optionsLocalCityItems.size() > 0 && optionsAreaItems.size() > 0)) {
                bindForBean(localBean);
            }

            mLocalPickerView.setPicker(optionsLocalProItems, optionsLocalCityItems, optionsAreaItems);
            if (!mLocalPickerView.isShowing()) {
                mLocalPickerView.show();
            }
        }
    }

    private void bindForBean(LocalBean localBean) {
        for (int i = 0; i < localBean.areas.size(); i++) {//省
            ArrayList<ArrayList<SelectLocalBean>> minLocalItems = new ArrayList<>();
            ArrayList<SelectLocalBean> subsString = new ArrayList<>();
            for (int j = 0; j < localBean.areas.get(i).subs.size(); j++) {
                String area_nameS = localBean.areas.get(i).subs.get(j).area_name;
                String area_codeS = localBean.areas.get(i).subs.get(j).area_code;
                SelectLocalBean postLocalBeanS = new SelectLocalBean();
                postLocalBeanS.name = area_nameS;
                postLocalBeanS.code = area_codeS;
                subsString.add(postLocalBeanS);
                ArrayList<SelectLocalBean> subsXString = new ArrayList<>();
                for (int k = 0; k < localBean.areas.get(i).subs.get(j).subs.size(); k++) {
                    String area_nameX = localBean.areas.get(i).subs.get(j).subs.get(k).area_name;
                    String area_codeX = localBean.areas.get(i).subs.get(j).subs.get(k).area_code;
                    SelectLocalBean postLocalBeanX = new SelectLocalBean();
                    postLocalBeanX.name = area_nameX;
                    postLocalBeanX.code = area_codeX;
                    subsXString.add(postLocalBeanX);
                }
                minLocalItems.add(subsXString);
            }
            optionsLocalCityItems.add(subsString);
            optionsAreaItems.add(minLocalItems);
            String area_name = localBean.areas.get(i).area_name;
            String area_code = localBean.areas.get(i).area_code;
            SelectLocalBean postLocalBean = new SelectLocalBean();
            postLocalBean.code = area_code;
            postLocalBean.name = area_name;
            optionsLocalProItems.add(postLocalBean);
        }
    }

    public void initLocalPicker(Context context, OnSelectLocalListener listener) {
        mLocalPickerView = new OptionsPickerView.Builder(context, (options1, option2, options3, v) -> {
            ArrayList<SelectLocalBean> beans = new ArrayList<>();
            SelectLocalBean provinceBean = optionsLocalProItems.get(options1);
            beans.add(provinceBean);
            SelectLocalBean cityBean = optionsLocalCityItems.get(options1).get(option2);
            beans.add(cityBean);
            SelectLocalBean areaBean = optionsAreaItems.get(options1).get(option2).get(options3);
            beans.add(areaBean);
            if (listener != null) {
                listener.onSelectLocal(beans);
            }
        }).setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(18)//确定和取消文字大小
                .setSubmitColor(context.getResources().getColor(R.color.colorBlackGold))
                .setCancelColor(context.getResources().getColor(R.color.grey))
                .setContentTextSize(18)//滚轮文字大小
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
    }

    public interface OnSelectTimeListener {
        void onSelectTime(Date date, String patternDate);
    }

    public interface OnSelectStringListener {
        void onSelectString(int position, String string);
    }

    public interface OnSelectLocalListener {
        void onSelectLocal(List<SelectLocalBean> list);
    }

    public void timeShow() {
        if (!mTimePickerView.isShowing()) {
            mTimePickerView.show();
        }
    }

    public void stringShow() {
        if (!mPickerView.isShowing()) {
            mPickerView.show();
        }
    }

    public void localShow(Context context) {
        if (mLocalBean != null) {
            responseLocalBean(mLocalBean);
        } else {
            new LocalModel(LocalServer.class).requestLocalBean().subscribe(new NetCallBackObserver<LocalBean>(new
                    LoaddingErrorImplTip(context)) {
                @Override
                public void responseSuccess(LocalBean localBean) {
                    responseLocalBean(localBean);
                }

                @Override
                public void responseFail(LocalBean localBean) {

                }
            });
        }

    }

    private String patternDate(Date date, String pattern) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(date);
    }

    public class SelectLocalBean {
        public String code;
        public String name;

        @Override
        public String toString() {
            return name + "";
        }
    }
}
