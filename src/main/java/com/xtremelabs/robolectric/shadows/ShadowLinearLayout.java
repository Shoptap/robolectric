package com.xtremelabs.robolectric.shadows;

import android.widget.LinearLayout;
import com.xtremelabs.robolectric.internal.Implements;

@Implements(LinearLayout.class)
public class ShadowLinearLayout extends ShadowViewGroup {
    public ShadowLinearLayout() {
        layoutParams = new LinearLayout.LayoutParams(0, 0);
    }
}
