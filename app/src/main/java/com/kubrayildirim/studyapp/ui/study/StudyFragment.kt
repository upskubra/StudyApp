package com.kubrayildirim.studyapp.ui.study

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kubrayildirim.studyapp.R
import com.kubrayildirim.studyapp.databinding.FragmentStudyBinding
import com.kubrayildirim.studyapp.ui.activity.MainViewModel
import com.kubrayildirim.studyapp.util.MyService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class StudyFragment : Fragment() {
    private var isPLaying = false
    private lateinit var binding: FragmentStudyBinding
    private val sessionViewModel by activityViewModels<MainViewModel>()
    private val viewModel: StudyViewModel by viewModels()
    private val args: StudyFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStudyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickListeners()
    }

    private fun clickListeners() {
        binding.ivPlay.setOnClickListener {
            // start service
            isPLaying = !isPLaying
            if (isPLaying) {
                Toast.makeText(requireContext(), "Stopped Music", Toast.LENGTH_SHORT).show()
                binding.ivPlay.setImageResource(R.drawable.pause_button)
                val intent = Intent(requireContext(), MyService::class.java)
                requireContext().startService(intent)

            } else {
                Toast.makeText(requireContext(), "Playing Music", Toast.LENGTH_SHORT).show()
                binding.ivPlay.setImageResource(R.drawable.play_button)
                val intent = Intent(requireContext(), MyService::class.java)
                requireContext().stopService(intent)

            }
        }
        binding.btnStart.setOnClickListener {
            binding.chronometer.format = "00:%s"
            //binding.chronometer.resetPivot()
            binding.chronometer.start()

        }

        binding.btnStop.setOnClickListener {
            binding.chronometer.stop()
        }

        binding.btnSave.setOnClickListener {
            binding.chronometer.stop()
            val time = binding.chronometer.text.toString()
            val id = args.name
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Ay numaralandırması 0'dan başlar
            val day = calendar.get(Calendar.DATE)
            val date = "$day-$month-$year"

            viewLifecycleOwner.lifecycleScope.launch {
                sessionViewModel.authState.value.user?.uid.let { user ->
                    try {
                        viewModel.addStudy(
                            id = id,
                            user!!,
                            date = date,
                            time = time,
                        )
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                    if (viewModel.studyInfoState.value.success == true) {
                        Toast.makeText(context, "Study Saved", Toast.LENGTH_LONG).show()

                    } else if (viewModel.studyInfoState.value.loading) {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
            // initObservers()
            val action =
                StudyFragmentDirections.actionStudyFragmentToStudyInfoListFragment()
            findNavController().navigate(action)
        }

    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.studyInfoState.collect { state ->
                if (state.success == true) {
                    Toast.makeText(context, "Study Saved", Toast.LENGTH_LONG).show()
                } else if (state.loading) {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "An Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}