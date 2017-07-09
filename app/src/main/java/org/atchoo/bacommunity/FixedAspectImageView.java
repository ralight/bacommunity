/*
This file is part of Bacommunity.

Bacommunity is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Foobar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

*/
package org.atchoo.bacommunity;

import android.content.Context;
import android.widget.ImageView;
import android.util.AttributeSet;
import com.buzzingandroid.ui.ViewAspectRatioMeasurer;

public class FixedAspectImageView extends ImageView {
    public FixedAspectImageView(Context context) {
        super(context);
    }

    public FixedAspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedAspectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // The aspect ratio to be respected by the measurer
    private static final double VIEW_ASPECT_RATIO = 0.693572;

    private ViewAspectRatioMeasurer varm = new ViewAspectRatioMeasurer( VIEW_ASPECT_RATIO );

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        varm.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension( varm.getMeasuredWidth(), varm.getMeasuredHeight() );
    }
}
