package com.jxareas.techhub.ui.poster

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.jxareas.techhub.R
import com.jxareas.techhub.databinding.FragmentPosterBinding
import com.jxareas.techhub.utils.extensions.getLong
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PosterFragment : Fragment() {

    private var _binding: FragmentPosterBinding? = null
    private val binding: FragmentPosterBinding
        get() = _binding!!

    private val viewModel: PosterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment_main
            scrimColor = Color.TRANSPARENT
            duration = resources.getLong(R.integer.material_motion_duration_long_2)
            interpolator = LinearInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
        }
        sharedElementReturnTransition = MaterialContainerTransform().apply {
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            interpolator = LinearInterpolator()
            scrimColor = Color.TRANSPARENT
            drawingViewId = R.id.nav_host_fragment_main
            duration = resources.getLong(R.integer.material_motion_duration_medium_1)
        }
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.courseImage.observe(viewLifecycleOwner) { image ->
            image?.let { posterImage ->
                loadImage(posterImage)
            }

        }
    }

    private fun loadImage(posterImage: String) = binding.run {
        Glide.with(root.context)
            .load(posterImage)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean = false.also { startPostponedEnterTransition() }
            })
            .into(imageViewCoursePhoto)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}