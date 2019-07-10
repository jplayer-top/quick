package top.jplayer.baseprolibrary.ui.decoration;

import android.content.Context;

import com.yanyusong.y_divideritemdecoration.Y_Divider;
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder;
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration;

/**
 * Created by Obl on 2019/5/3.
 * top.jplayer.baseprolibrary.ui.decoration
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class DividerItemDecoration extends Y_DividerItemDecoration {
    private DividerItemDecoration(Context context) {
        super(context);
    }

    @Override
    public Y_Divider getDivider(int itemPosition) {
        Y_Divider divider = null;
        switch (itemPosition % 2) {
            case 0:
                //每一行第一个显示rignt和bottom
                divider = new Y_DividerBuilder()
                        .setRightSideLine(true, 0xff666666, 10, 0, 0)
                        .setBottomSideLine(true, 0xff666666, 20, 0, 0)
                        .create();
                break;
            case 1:
                //第二个显示Left和bottom
                divider = new Y_DividerBuilder()
                        .setLeftSideLine(true, 0xff666666, 10, 0, 0)
                        .setBottomSideLine(true, 0xff666666, 20, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}
