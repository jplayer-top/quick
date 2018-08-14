package top.jplayer.baseprolibrary.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import opensource.jpinyin.PinyinFormat;
import opensource.jpinyin.PinyinHelper;


/**
 * Created by PEO on 2017/2/27.
 * 获取联系人工具
 */

public class ContactUtils {
    public static List<ContactsInfoBean> list = new ArrayList<>();

    public static List<ContactsInfoBean> load(Context context) {
        Cursor cursor;
        try {
            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            cursor = context.getContentResolver().query(contactUri,
                    new String[]{"display_name", "sort_key", "contact_id", "data1"},
                    null, null, "sort_key");
            String contactName;
            String contactNumber;
            String contactSortKey;
            int contactId;
            if (list.size() > 0) {
                return list;
            }
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    contactSortKey = getSortkey(cursor.getString(1));
                    ContactsInfoBean contactsInfo = new ContactsInfoBean(contactName, contactNumber, contactSortKey, contactId);
                    if (contactName != null)
                        list.add(contactsInfo);
                }
                cursor.close();//使用完后一定要将cursor关闭，不然会造成内存泄露等问题
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    private static String getSortkey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        } else if (key.equals("")) {
            return "#";   //获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
        } else {
            return c2e(key).substring(0, 1).toUpperCase(Locale.CHINESE);
        }
    }
    public static class ContactsInfoBean implements Comparable<ContactsInfoBean> {
        public String name;
        public String number;
        public String sortKey;
        public int id;
        public boolean isCheck;

        public ContactsInfoBean(String name, String number, String sortKey, int id) {
            this.name = name;
            this.number = number;
            this.sortKey = sortKey;
            this.id = id;
        }


        @Override
        public int compareTo(@NonNull ContactsInfoBean o) {
            return this.sortKey.compareTo(o.sortKey);
        }
    }
    /**
     * 将传入的数据转化为汉语拼音
     *
     * @param name 要转化的汉字
     * @return 转化为汉字拼音
     */
    public static String c2e(String name) {
        //将传入的字符串转化为数组
        char[] charArray = name.toCharArray();
        //用于记录转化的字符
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            //将传入的字符转化为汉语拼音
            String pinyinName = "ww";
            try {
                pinyinName = PinyinHelper.convertToPinyinArray(c, PinyinFormat.WITHOUT_TONE)[0];
            } catch (Exception e) {
                System.out.println(name+"");
                e.printStackTrace();
            }
            sb.append(pinyinName);
        }
        return sb.toString();
    }

    public static String teaC2E(String name) {
        String pinyinName = "";
        try {
            pinyinName = PinyinHelper.convertToPinyinString(name, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pinyinName;
    }
}
