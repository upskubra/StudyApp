package com.kubrayildirim.studyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kubrayildirim.studyapp.data.model.StudyInfo
import com.kubrayildirim.studyapp.databinding.ItemRowBinding

class StudyInfoListAdapter(private val list: MutableList<StudyInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    class ListViewHolder(
        private val binding: ItemRowBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudyInfo) {
            binding.apply {
                txtDate.text = item.date
                txtTime.text = item.time
                txtName.text = item.id
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
