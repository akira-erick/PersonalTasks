package akira.erick.com.personaltasks.adapter

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.TileDeletedTaskBinding
import akira.erick.com.personaltasks.model.Task
import akira.erick.com.personaltasks.ui.OnDeletedTaskClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class DeletedTaskRvAdapter(
    private val taskList: MutableList<Task>,
    private val onDeletedTaskClickListener: OnDeletedTaskClickListener
): RecyclerView.Adapter<DeletedTaskRvAdapter.DeletedTaskViewHolder>() {
    inner class DeletedTaskViewHolder(tdtb: TileDeletedTaskBinding): RecyclerView.ViewHolder(tdtb.root){
        val titleTv: TextView = tdtb.titleTv
        val descriptionTv: TextView = tdtb.descriptionTv
        val deadlineTv: TextView = tdtb.deadlineTv

        init {
            tdtb.root.setOnCreateContextMenuListener{ menu, v, menuInfo ->
                (onDeletedTaskClickListener as AppCompatActivity).menuInflater.inflate(R.menu.context_deleted_tasks, menu)
                menu.findItem(R.id.visualize_task_mi).setOnMenuItemClickListener {
                    onDeletedTaskClickListener.onActivateTaskMenuItemClick(adapterPosition)
                    true
                }
                menu.findItem(R.id.reactivate_task_mi).setOnMenuItemClickListener {
                    onDeletedTaskClickListener.onVisualizationMenuItemClick(adapterPosition)
                    true
                }
            }

            tdtb.root.setOnClickListener {
                onDeletedTaskClickListener.onTaskClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeletedTaskViewHolder = DeletedTaskViewHolder(
        TileDeletedTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(
        holder: DeletedTaskViewHolder,
        position: Int
    ) {
        taskList[position].let { task ->
            with(holder) {
                titleTv.text = task.title
                descriptionTv.text = task.description
                deadlineTv.text = task.deadline
            }
        }
    }

}