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
