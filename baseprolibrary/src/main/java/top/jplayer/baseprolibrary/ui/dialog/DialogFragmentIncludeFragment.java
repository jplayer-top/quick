package top.jplayer.baseprolibrary.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.ui.fragment.TestFragment;
import top.jplayer.baseprolibrary.utils.ScreenUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialogFragment;

/**
 * Created by Obl on 2018/3/20.
 * top.jplayer.baseprolibrary.widgets.dialog
 */

public class DialogFragmentIncludeFragment extends BaseCustomDialogFragment {

    @Override
    public int initLayout() {
        return R.layout.dialog_fragment_include_fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_include_fragment, container);
    }

    @Override
    protected void initView(View view) {
        getChildFragmentManager().beginTransaction().replace(R.id.flAddFragment, new TestFragment()).commit();
    }

    @Override
    public int setHeight() {
        return ScreenUtils.getScreenHeight() - ScreenUtils.dp2px(150);
    }


}
