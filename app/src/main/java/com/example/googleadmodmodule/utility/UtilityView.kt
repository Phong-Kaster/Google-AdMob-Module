package com.example.googleadmodmodule.utility

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import androidx.viewpager.widget.ViewPager

object UtilityView {
    /**
     * */
    fun View.clickWithDebounce(debounceTime: Long = 500L, action: () -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0
            override fun onClick(v: View) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
                else action()
                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }

    /**
     * auto scroll view pager*/
    fun ViewPager.autoScroll(interval: Long) {
        val handler = Handler(Looper.getMainLooper())
        var scrollPosition = 0

        val runnable = object : Runnable {

            override fun run() {
                try {
                    val count = adapter?.count ?: 0
                    setCurrentItem(scrollPosition++ % count, true)

                    handler.postDelayed(this, interval)
                } catch (_: java.lang.Exception) {

                }
            }
        }

        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                scrollPosition = position + 1
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not necessary
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Not necessary
            }
        })

        handler.post(runnable)
    }

    fun View.makeVisible(){
        this.visibility = View.VISIBLE
    }

    fun View.makeGone(){
        this.visibility = View.GONE
    }

    fun View.makeInvisible(){
        this.visibility = View.INVISIBLE
    }

}