package top.jplayer.baseprolibrary.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Obl on 2018/3/14.
 * top.jplayer.baseprolibrary.utils
 */

public class StringUtils {
    private static StringUtils mStrUtils;

    public static StringUtils init() {
        if (mStrUtils == null) {
            mStrUtils = new StringUtils();
        }
        return mStrUtils;
    }

    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     *
     * @return String
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;

        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return "#" + r + g + b;
    }

    public boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public boolean isEmpty(CharSequence sequence) {
        return TextUtils.isEmpty(sequence);
    }

    public boolean isEmpty(TextView textView) {
        return TextUtils.isEmpty(textView.getText());
    }

    public String fixNullStr(CharSequence sequence) {
        return fixNullStr(sequence.toString());
    }

    public String fixNullStr(String addStr, String... preStr) {
        StringBuilder aft = new StringBuilder("");
        for (int i = 0; i < preStr.length; i++) {
            if (preStr[i] == null) {
                aft.append("");
            } else {
                aft.append(preStr[i]);
                aft.append(addStr);
            }
        }
        return aft.toString();
    }

    public String fixNullStr(String string) {
        if (isEmpty(string)) {
            return "";
        }
        return string;
    }

    /**
     * 判断是否为空,并返回输入的相应字段
     */
    public String fixNullStr(Object preStr, String midStr, String aftStr) {
        if (preStr == null || preStr.equals("")) {
            return aftStr;
        }
        return preStr.toString() + midStr;
    }

    /**
     * @param preStr 判断的str
     * @param aftStr 如果是空，返回的str
     * @return aftStr
     */
    public String fixNullStr(String preStr, String aftStr) {
        if (isEmpty(preStr)) {
            return aftStr;
        }
        return preStr;
    }


    public void tipEditTextLength(EditText edit, Editable s, int maxLen, String msg) {
        if (s.length() > maxLen) {
            ToastUtils.init().showQuickToast(msg);
            edit.setText(s.subSequence(0, maxLen));
            edit.setSelection(edit.getText().length());
        }
    }

    public boolean tipEditTextLength(EditText edit, int mixLen, int maxLen) {
        int length = edit.getText().toString().trim().length();
        if (length > mixLen && length < maxLen) {
            return false;
        }
        return true;
    }

    public void copyString(Context context, String copy) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", copy);
        // 将ClipData内容放到系统剪贴板里。
        if (cm != null) {
            cm.setPrimaryClip(mClipData);
        }
    }

    /**
     * uri转path
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                data = getStringByCursor(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                data = getStringByCursor(context, contentUri, null);
            }
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            data = getStringByCursor(context, uri, null);
        }
        return data;
    }

    private static String getStringByCursor(Context context, Uri uri, String selection) {
        String data = "";
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, selection, null, null);
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                if (index > -1) {
                    data = cursor.getString(index);
                }
            }
            cursor.close();
        }
        return data;
    }
}
