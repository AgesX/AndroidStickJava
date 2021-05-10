package com.zhy.stickynavlayout.view;

import android.content.Context;


import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.RecyclerView;


import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.zhy.stickynavlayout.R;


public class StickyNavLayout extends LinearLayout implements NestedScrollingParent
{
    private static final String TAG = "StickyNavLayout";

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes)
    {
        Log.e(TAG, "onStartNestedScroll");
        if (nestedScrollAxes == 1){
            return false;
        }
        else{
            return true;
        }

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed)
    {
        Log.e(TAG, "onNestedPreScroll");
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !target.canScrollVertically(-1);

        if (hiddenTop || showTop)
        {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private int TOP_CHILD_FLING_THRESHOLD = 3;
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed)
    {
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)
    {
       //不做拦截 可以传递给子View
       return false;
    }

    @Override
    public int getNestedScrollAxes()
    {
        Log.e(TAG, "getNestedScrollAxes");
        return 0;
    }

    private View mTop;
    private View mNav;
    private ViewPager2 mViewPager;

    private int mTopViewHeight;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;

    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;

    public StickyNavLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();

    }

    private void initVelocityTrackerIfNotExists()
    {
        if (mVelocityTracker == null)
        {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker()
    {
        if (mVelocityTracker != null)
        {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager2))
        {
            throw new RuntimeException(
                    "id_stickynavlayout_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager2) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + mNav.getMeasuredHeight() + mViewPager.getMeasuredHeight());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
    }


    public void fling(int velocityY)
    {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > mTopViewHeight)
        {
            y = mTopViewHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }


}
