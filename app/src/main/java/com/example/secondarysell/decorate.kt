package com.example.secondarysell


import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ListPaddingDecoration(context: Context, val paddingLeft: Int, val paddingRight: Int) :
    androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
    private var mDivider: Drawable? = null
    init {
        mDivider = ContextCompat.getDrawable(context, R.drawable.decorate)
    }
    override fun onDrawOver(c: Canvas, parent: androidx.recyclerview.widget.RecyclerView,
                            state: androidx.recyclerview.widget.RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft + paddingLeft
        val right = parent.width - parent.paddingRight - paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as androidx.recyclerview.widget.RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + (mDivider?.intrinsicHeight ?: 0)
            mDivider?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}