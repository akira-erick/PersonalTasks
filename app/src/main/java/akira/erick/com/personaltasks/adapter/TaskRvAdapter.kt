package akira.erick.com.personaltasks.adapter

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.TileTaskBinding
import akira.erick.com.personaltasks.model.Task
import akira.erick.com.personaltasks.ui.OnTaskClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class TaskRvAdapter(
    private val taskList: MutableList<Task>,
    private val  onTaskClickListener: OnTaskClickListener
): RecyclerView.Adapter<TaskRvAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(ttb: TileTaskBinding): RecyclerView.ViewHolder(ttb.root){
        val titleTv: TextView = ttb.titleTv
        val descriptionTv: TextView = ttb.descriptionTv
        val deadlineTv: TextView = ttb.deadlineTv

        init {
            //creating context to each tile to a new holder
            ttb.root.setOnCreateContextMenuListener{ menu, v, menuInfo ->
                (onTaskClickListener as AppCompatActivity).menuInflater.inflate(R.menu.context_menu_main, menu)
                menu.findItem(R.id.edit_contact_mi).setOnMenuItemClickListener {
                    onTaskClickListener.onEditTaskMenuItemClick(adapterPosition)
                    true
                }
                menu.findItem(R.id.remove_contact_mi).setOnMenuItemClickListener {
                    onTaskClickListener.onRemoveTaskMenuItemClick(adapterPosition)
                    true
                }
            }

            //set click listener to a new holder
            ttb.root.setOnClickListener {
                onTaskClickListener.onTaskClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder = TaskViewHolder (
        TileTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}