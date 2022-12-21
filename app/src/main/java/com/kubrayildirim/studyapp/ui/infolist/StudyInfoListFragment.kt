package com.kubrayildirim.studyapp.ui.infolist

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.kubrayildirim.studyapp.R
import com.kubrayildirim.studyapp.adapter.StudyInfoListAdapter
import com.kubrayildirim.studyapp.data.model.StudyInfo
import com.kubrayildirim.studyapp.databinding.FragmentStudyInfoListBinding
import com.kubrayildirim.studyapp.ui.activity.MainViewModel
import com.kubrayildirim.studyapp.util.MyBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudyInfoListFragment : Fragment() {
    private lateinit var binding: FragmentStudyInfoListBinding
    private val viewModel: StudyInfoListViewModel by viewModels()
    private val sessionViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receiver = MyBroadcastReceiver { state ->
            if (state) {
                Toast.makeText(requireContext(), "Plane Mod On", Toast.LENGTH_LONG).show()
                binding.rvStudyInfoList.setBackgroundResource(R.color.purple_200)
            } else {
                Toast.makeText(requireContext(), "Plane Mod Off", Toast.LENGTH_LONG).show()
                binding.rvStudyInfoList.setBackgroundResource(R.color.white)
            }
        }
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        requireContext().registerReceiver(receiver, filter)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStudyInfoListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUser()
        binding.btnStudy.setOnClickListener {
            val action =
                StudyInfoListFragmentDirections.actionStudyInfoListFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }


    private fun observeUser() {
        viewLifecycleOwner.lifecycleScope.launch {
            sessionViewModel.signIn()
            sessionViewModel.authState.collect {
                if (it.user == null) {
                    Toast.makeText(context, "Please login to continue", Toast.LENGTH_LONG).show()
                    binding.rvStudyInfoList.visibility = View.GONE
                    binding.btnStudy.visibility = View.GONE
                } else {
                    val user = it.user!!.uid
                    updateListState(user)
                    binding.btnStudy.visibility = View.VISIBLE
                    observeFeed()
                }
            }
        }
    }

    private fun observeFeed() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.studyInfoState.collect {
                if (it.loading) {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                } else if (it.studInfoList?.size!! > 0) {
                    initRecycler(it.studInfoList!!)
                } else {
                    binding.rvStudyInfoList.visibility = View.GONE
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler(list: MutableList<StudyInfo>) {
        binding.rvStudyInfoList.apply {
            adapter = StudyInfoListAdapter(list)
        }
    }


    private fun updateListState(user: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getStudyInfoList(user)
        }
    }
}