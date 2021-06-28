package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.databinding.FragmentSeeImageBinding
import ru.netology.nmedia.view.load

@AndroidEntryPoint
class SeeImageFragment : Fragment() {

    private lateinit var  binding: FragmentSeeImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSeeImageBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getString("attachUrl")?.let{
            binding.attch.load("${BuildConfig.BASE_URL}/media/${it}")
        }
    }

}