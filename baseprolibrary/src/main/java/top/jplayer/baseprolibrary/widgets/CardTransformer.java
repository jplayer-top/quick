/**
 * Copyright 2017 Sun Jian
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.jplayer.baseprolibrary.widgets;

import android.support.v4.view.ViewPager;
import android.view.View;

import top.jplayer.baseprolibrary.utils.SizeUtils;

/**
 * description
 * <p>调整ViewPager滑动视差
 * Created by sunjian on 2017/6/22.
 */
public class CardTransformer implements ViewPager.PageTransformer {

    private int mMaxTranslateOffsetX;
    private ViewPager mViewPager;

    public CardTransformer(int maxOffset) {

        mMaxTranslateOffsetX = SizeUtils.dp2px(maxOffset);
    }


    @Override
    public void transformPage(View page, float position) {

        if (mViewPager == null) {
            mViewPager = (ViewPager) page.getParent();
        }
        float offset = -position;
        float v = mMaxTranslateOffsetX * offset;

        if (position == 0) {
            page.setTranslationX(SizeUtils.dp2px(0));
        } else {
            page.setTranslationX(v);
        }

    }
}
