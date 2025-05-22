package akira.erick.com.personaltasks.adapter

import akira.erick.com.personaltasks.databinding.TileTaskBinding
import akira.erick.com.personaltasks.model.Task
import akira.erick.com.personaltasks.ui.OnTaskClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskRvAdapter(
    private val taskList: MutableList<Task>,
    private val  onTaskClickListener: OnTaskClickListener
): RecyclerView.Adapter<TaskRvAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(ttb: TileTaskBinding): RecyclerView.ViewHolder(ttb.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}