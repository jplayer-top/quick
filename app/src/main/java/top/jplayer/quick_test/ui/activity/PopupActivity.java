package top.jplayer.quick_test.ui.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.widgets.popup.CommonPopupWindow;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/8/10.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class PopupActivity extends CommonToolBarActivity implements View.OnClickListener {
    // anchor for popup window
    private Button gravityBt;
    // horizontal radio buttons
    private RadioButton alignLeftRb;
    private RadioButton alignRightRb;
    private RadioButton centerHoriRb;
    private RadioButton toRightRb;
    private RadioButton toLeftRb;
    // vertical radio buttons
    private RadioButton alignAboveRb;
    private RadioButton alignBottomRb;
    private RadioButton centerVertRb;
    private RadioButton toBottomRb;
    private RadioButton toAboveRb;
    // popup window
    private CommonPopupWindow window;
    private CommonPopupWindow.LayoutGravity layoutGravity;
    private TextView horiText;
    private TextView vertText;

    @Override
    public int initAddLayout() {
        return R.layout.activity_popup;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        window = new CommonPopupWindow(this, R.layout.layout_popup_view, 80, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                horiText = view.findViewById(R.id.hori_text);
                vertText = view.findViewById(R.id.vert_text);
            }

            @Override
            protected void initEvent() {
            }
        };
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.ALIGN_LEFT | CommonPopupWindow.LayoutGravity.TO_BOTTOM);

        initView();
        initEvent();
    }

    private void initView() {
        gravityBt = mRootView.findViewById(R.id.gravity_bt);

        alignLeftRb = mRootView.findViewById(R.id.align_left_rb);
        alignRightRb = mRootView.findViewById(R.id.align_right_rb);
        centerHoriRb = mRootView.findViewById(R.id.center_hori_rb);
        toRightRb = mRootView.findViewById(R.id.to_right_rb);
        toLeftRb = mRootView.findViewById(R.id.to_left_rb);

        alignAboveRb = mRootView.findViewById(R.id.align_above_rb);
        alignBottomRb = mRootView.findViewById(R.id.align_bottom_rb);
        centerVertRb = mRootView.findViewById(R.id.center_vert_rb);
        toBottomRb = mRootView.findViewById(R.id.to_bottom_rb);
        toAboveRb = mRootView.findViewById(R.id.to_above_rb);
    }

    private void initEvent() {
        gravityBt.setOnClickListener(this);

        alignLeftRb.setOnClickListener(this);
        alignRightRb.setOnClickListener(this);
        centerHoriRb.setOnClickListener(this);
        toRightRb.setOnClickListener(this);
        toLeftRb.setOnClickListener(this);

        alignAboveRb.setOnClickListener(this);
        alignBottomRb.setOnClickListener(this);
        centerVertRb.setOnClickListener(this);
        toBottomRb.setOnClickListener(this);
        toAboveRb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gravity_bt:
                window.showBashOfAnchor(gravityBt, layoutGravity, 0, 0);
                break;
            case R.id.align_left_rb:
                layoutGravity.setHoriGravity(CommonPopupWindow.LayoutGravity.ALIGN_LEFT);
                horiText.setText("AL");
                break;

            case R.id.align_right_rb:
                layoutGravity.setHoriGravity(CommonPopupWindow.LayoutGravity.ALIGN_RIGHT);
                horiText.setText("AR");
                break;
            case R.id.center_hori_rb:
                layoutGravity.setHoriGravity(CommonPopupWindow.LayoutGravity.CENTER_HORI);
                horiText.setText("CH");
                break;
            case R.id.to_right_rb:
                layoutGravity.setHoriGravity(CommonPopupWindow.LayoutGravity.TO_RIGHT);
                horiText.setText("TR");
                break;
            case R.id.to_left_rb:
                layoutGravity.setHoriGravity(CommonPopupWindow.LayoutGravity.TO_LEFT);
                horiText.setText("TL");
                break;
            case R.id.align_above_rb:
                layoutGravity.setVertGravity(CommonPopupWindow.LayoutGravity.ALIGN_ABOVE);
                vertText.setText("AA");
                break;
            case R.id.align_bottom_rb:
                layoutGravity.setVertGravity(CommonPopupWindow.LayoutGravity.ALIGN_BOTTOM);
                vertText.setText("AB");
                break;
            case R.id.center_vert_rb:
                layoutGravity.setVertGravity(CommonPopupWindow.LayoutGravity.CENTER_VERT);
                vertText.setText("CV");
                break;
            case R.id.to_bottom_rb:
                layoutGravity.setVertGravity(CommonPopupWindow.LayoutGravity.TO_BOTTOM);
                vertText.setText("TB");
                break;
            case R.id.to_above_rb:
                layoutGravity.setVertGravity(CommonPopupWindow.LayoutGravity.TO_ABOVE);
                vertText.setText("TA");
                break;
        }
    }
}
