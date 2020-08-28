package com.latihangoding.githubuserapp.helpers

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.latihangoding.githubuserapp.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(RequestOptions()
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("visibility")
fun View.setVisibility(status: Boolean) {
    visibility = if (status) View.VISIBLE else View.GONE
}

@BindingAdapter("setFavorite")
fun ImageButton.setFavorite(isFavorite: Boolean) {
    setImageResource(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
}