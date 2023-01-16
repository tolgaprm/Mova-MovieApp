package com.prmto.mova_movieapp.feature_movie_tv_detail.presentation.detail.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.prmto.mova_movieapp.databinding.YoutubePlayerViewBinding
import com.prmto.mova_movieapp.feature_movie_tv_detail.domain.models.detail.video.VideoResult


class VideosAdapter(
    private val lifecycle: Lifecycle
) : ListAdapter<VideoResult, VideosAdapter.VideoResultViewHolder>(
    videoItemCallback
) {

    class VideoResultViewHolder(
        private val binding: YoutubePlayerViewBinding
    ) : ViewHolder(binding.root) {

        fun bind(
            videoResult: VideoResult,
            lifecycle: Lifecycle
        ) {


            binding.txtVideoName.text = videoResult.name
            binding.txtVideoType.text = videoResult.type
            if (Build.VERSION.SDK_INT >= 26) {
                binding.txtVideoName.tooltipText = videoResult.name
            }

            lifecycle.addObserver(binding.youtubePlayerView)
            binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(
                        videoId = videoResult.key,
                        startSeconds = 0f
                    )
                }
            })

        }

        companion object {
            fun from(parent: ViewGroup): VideoResultViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = YoutubePlayerViewBinding.inflate(inflater, parent, false)
                return VideoResultViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoResultViewHolder {
        return VideoResultViewHolder.from(parent = parent)
    }

    override fun onBindViewHolder(holder: VideoResultViewHolder, position: Int) {
        val videoResult = getItem(position)
        holder.bind(videoResult = videoResult, lifecycle = lifecycle)
    }
}

val videoItemCallback = object : DiffUtil.ItemCallback<VideoResult>() {

    override fun areItemsTheSame(oldItem: VideoResult, newItem: VideoResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VideoResult, newItem: VideoResult): Boolean {
        return oldItem == newItem
    }
}