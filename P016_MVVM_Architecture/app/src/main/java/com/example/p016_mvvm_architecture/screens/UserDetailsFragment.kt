package com.example.p016_mvvm_architecture.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.p016_mvvm_architecture.R
import com.example.p016_mvvm_architecture.databinding.FragmentUserDetailsBinding
import com.example.p016_mvvm_architecture.tasks.SuccessResult

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private val viewModel: UserDetailsViewModel by viewModels { factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser(requireArguments().getLong(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater, container, false)

        viewModel.actionShowToast.observe(viewLifecycleOwner, Observer {
            it.getValue()?.let { messageRes -> navigator().toast(messageRes) }
        })
        viewModel.actionGoBack.observe(viewLifecycleOwner, Observer {
            it.getValue()?.let { navigator().goBack() }
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            binding.contentContainer.visibility = if (it.showContent) {
                val userDetails = (it.userDetailsResult as SuccessResult).data
                binding.userNameTextView.text = userDetails.user.name 
                if (userDetails.user.photo.isNOtBlank()) {
                    Glide.with(this)
                        .load(userDetails.user.photo)
                        .circleCrop()
                        .into(binding.photoImageView)
                } else {
                    Glide.with(this)
                        .load(R.drawable.ic_user_avatar)
                        .into(binding.photoImageView)
                }
                binding.userDetailsTextView.text = userDetails.details 

                View.VISIBLE
            } else {
                View.GONE
            }

            binding.progressBar.visibility = if (it.showProgress) View.VISIBLE else View.GONE
            binding.deleteButton.isEnabled = it.enableDeleteButton
        })

        binding.deleteButton.setOnClickListener {
            viewModel.deleteUser()
        }

        return binding.root
    }

    companion object {

        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long): UserDetailsFragment {
            val fragment = UserDetailsFragment()
            fragment.arguments = bundleOf(ARG_USER_ID to userId)
            return fragment
        }
    }
}