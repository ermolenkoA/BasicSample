package com.example.android.databinding.basicsample.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.Popularity
import com.example.android.databinding.basicsample.util.BindingConverters.booleanToVisibility
import com.example.android.databinding.basicsample.util.ConverterUtil.isZero

/**
 * A Binding Adapter that is called whenever the value of the attribute `app:popularityIcon`
 * changes. Receives a popularity level that determines the icon and tint color to use.
 */
@BindingAdapter("app:popularityIcon")
fun popularityIcon(view: ImageView, popularity: Popularity) {

    val color = getAssociatedColor(popularity, view.context)

    ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(color))

    view.setImageDrawable(getDrawablePopularity(popularity, view.context))
}

/**
 * A Binding Adapter that is called whenever the value of the attribute `android:progressTint`
 * changes. Depending on the value it determines the color of the progress bar.
 */
@BindingAdapter("app:progressTint")
fun tintPopularity(view: ProgressBar, popularity: Popularity) {

    val color = getAssociatedColor(popularity, view.context)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.progressTintList = ColorStateList.valueOf(color)
    }
}

/**
 *  Sets the value of the progress bar so that 5 likes will fill it up.
 *
 *  Showcases Binding Adapters with multiple attributes. Note that this adapter is called
 *  whenever any of the attribute changes.
 */
@BindingAdapter(value = ["app:progressScaled", "android:max"], requireAll = true)
fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / 50).coerceAtMost(max)
}

/**
 * Unused Binding Adapter to replace the Binding Converter that hides a view if the number
 * of likes is zero.
 */
@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = booleanToVisibility(isZero(number))
}

private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
    return when (popularity) {
        Popularity.NORMAL -> context.theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.colorForeground)
        ).getColor(0, 0x000000)
        Popularity.POPULAR -> ContextCompat.getColor(context, R.color.popular)
        Popularity.STAR -> ContextCompat.getColor(context, R.color.star)
    }
}

private fun getDrawablePopularity(popularity: Popularity, context: Context): Drawable? {
    return when (popularity) {
        Popularity.NORMAL -> {
            ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
        }
        Popularity.POPULAR -> {
            ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
        Popularity.STAR -> {
            ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
    }
}
