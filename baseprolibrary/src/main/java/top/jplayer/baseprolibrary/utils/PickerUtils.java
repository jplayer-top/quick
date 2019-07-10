package top.jplayer.baseprolibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void initTimePicker(Context context, String[] strings, OnSelectTimeListener listener) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1983, 0, 1);
        if (strings.length == 3) {
            selectedDate.set(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]) - 1,
                    Integer.parseInt(strings[2]));
        } else {
            selectedDate.set(2017, 0,
                    1);
        }
        Calendar endDate = Calendar.getInstance();
        //时间选择器
        mTimePickerView = new TimePickerView.Builder(context, (date, v) -> {//选中事件回调
            String patternDate = patternDate(date, "yyyy-MM-dd");
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
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubmitColor(context.getResources().getColor(R.color.colorBlackGold))
                .setCancelColor(context.getResources().getColor(R.color.grey))
                .setSubCalSize(18)//确定和取消文字大小
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    public void initTimePicker(Context context, String pattern, OnSelectTimeListener listener) {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH));
        Calendar endDate = Calendar.getInstance();
        endDate.set(2050, 11, 28);
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
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubmitColor(context.getResources().getColor(R.color.colorBlackGold))
                .setCancelColor(context.getResources().getColor(R.color.grey))
                .setSubCalSize(18)//确定和取消文字大小
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }


    public void initStringPicker(final List<String> optionsItems, int position, Context context,
                                 OnSelectStringListener listener) {

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
                .setSubmitColor(context.getResources().getColor(R.color.colorBlackGold))
                .setCancelColor(context.getResources().getColor(R.color.grey))
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


    public void initDoublePicker(List<String> optionsItems0,
                                 List<List<String>> optionsItems1,
                                 int position,
                                 Context context, OnSelectDoubleTimeListener listener) {
        mPickerView = new OptionsPickerView.Builder(context,
                (options1, options2, options3, v) -> {
                    String o1 = "";
                    String o2 = "";
                    if (optionsItems0 != null) {
                        o1 = optionsItems0.get(options1);
                        if (optionsItems1 != null && optionsItems1.size() > 0) {
                            o2 = optionsItems1.get(options1).get(options2);
                        }
                    }
                    if (listener != null) {
                        listener.onSelectDoubleTime(o1, o2);
                    }
                })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubmitColor(context.getResources().getColor(R.color.colorBlackGold))
                .setCancelColor(context.getResources().getColor(R.color.grey))
                .setSubCalSize(18)//确定和取消文字大小
                .setContentTextSize(21)//滚轮文字大小
                .setDividerColor(Color.DKGRAY)
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(position)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
        mPickerView.setPicker(optionsItems0, optionsItems1);//添加数据源
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
                if (postLocalBeanS.name.contains("市辖区")) {
                    postLocalBeanS.name = localBean.areas.get(i).area_name;
                }
                subsString.add(postLocalBeanS);
                ArrayList<SelectLocalBean> subsXString = new ArrayList<>();
                for (int k = 0; k < localBean.areas.get(i).subs.get(j).subs.size(); k++) {
                    String area_nameX = localBean.areas.get(i).subs.get(j).subs.get(k).area_name;
                    String area_codeX = localBean.areas.get(i).subs.get(j).subs.get(k).area_code;
                    SelectLocalBean postLocalBeanX = new SelectLocalBean();
                    postLocalBeanX.name = area_nameX;
                    postLocalBeanX.code = area_codeX;

                    if (postLocalBeanX.name.contains("市辖区")) {
                        continue;
                    }
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
                .setSelectOptions(14, 0, 0)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();
    }

    public interface OnSelectTimeListener {
        void onSelectTime(Date date, String patternDate);
    }

    public interface OnSelectDoubleTimeListener {
        void onSelectDoubleTime(String start, String end);
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

    public void doubleTimeShow() {
        if (!mPickerView.isShowing()) {
            mPickerView.show();
        }
    }

    public void localShow(Context context) {
        if (mLocalBean != null) {
            responseLocalBean(mLocalBean);
        } else {
            new LocalModel(LocalServer.class).requestLocalBeanByJson().subscribe(new NetCallBackObserver<LocalBean>(new
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


    public List<String> getTime() {
        String[] arr = new String[]{"00:00", "00:10", "00:20", "00:30", "00:40", "00:50", "01:00", "01:10", "01:20",
                "01:30", "01:40", "01:50", "02:00", "02:10", "02:20", "02:30", "02:40", "02:50", "03:00", "03:10", "03:20",
                "03:30", "03:40", "03:50", "04:00", "04:10", "04:20", "04:30", "04:40",
                "04:50", "05:00", "05:10", "05:20", "05:30", "05:40", "05:50", "06:00", "06:10",
                "06:20", "06:30", "06:40", "06:50", "07:00", "07:10", "07:20", "07:30", "07:40",
                "07:50", "08:00", "08:10", "08:20", "08:30", "08:40", "08:50", "09:00", "09:10",
                "09:20", "09:30", "09:40", "09:50", "10:00", "10:10", "10:20", "10:30", "10:40",
                "10:50", "11:00", "11:10", "11:20", "11:30", "11:40", "11:50", "12:00", "12:10",
                "12:20", "12:30", "12:40", "12:50", "13:00", "13:10", "13:20", "13:30", "13:40",
                "13:50", "14:00", "14:10", "14:20", "14:30", "14:40", "14:50", "15:00", "15:10",
                "15:20", "15:30", "15:40", "15:50", "16:00", "16:10", "16:20", "16:30", "16:40",
                "16:50", "17:00", "17:10", "17:20", "17:30", "17:40", "17:50", "18:00", "18:10",
                "18:20", "18:30", "18:40", "18:50", "19:00", "19:10", "19:20", "19:30", "19:40",
                "19:50", "20:00", "20:10", "20:20", "20:30", "20:40", "20:50", "21:00", "21:10",
                "21:20", "21:30", "21:40", "21:50", "22:00", "22:10", "22:20", "22:30", "22:40",
                "22:50", "23:00", "23:10", "23:20", "23:30", "23:40", "23:50"};
        return Arrays.asList(arr);
    }

    public List<String> getTime(String input) {
        int iInput = Integer.parseInt(input.replace(":", "")) + 10;
        ArrayList<String> list = new ArrayList<>();
        for (int i = iInput; i <= 2400; i += 10) {
            String str;
            if (i % 100 >= 60) {
                continue;
            }
            if (i < 10) {
                str = "00:0" + i;
            } else if (i < 100) {
                str = "00:" + i;
            } else if (i < 1000) {
                int i1 = i % 100;
                if (i1 == 0) {
                    str = "0" + i / 100 + ":0" + i1;
                } else {
                    str = "0" + i / 100 + ":" + i1;
                }
            } else {
                int i1 = i % 100;
                if (i1 == 0) {
                    str = i / 100 + ":0" + i1;
                } else {
                    str = i / 100 + ":" + i1;
                }
            }
            list.add(str);
        }
        return list;
    }

    public List<List<String>> getTime2() {
        ArrayList<List<String>> lists = new ArrayList<>();
        for (String s : getTime()) {
            lists.add(getTime(s));
        }
        return lists;
    }
}
