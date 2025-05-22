package akira.erick.com.personaltasks.adapter

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.databinding.TileTaskBinding
import akira.erick.com.personaltasks.model.Task
import android.content.ClipDescription
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView

class TaskAdapter(context: Context, private val taskList: MutableList<Task>) :
    ArrayAdapter<Task> (
        context,
        R.layout.tile_task,
        taskList
    ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //Get task to fill task data
        val task = taskList[position]

        //verify if is existent task or recycled one
        var taskTileView = convertView
        //make new one
        if (taskTileView == null){
            TileTaskBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).apply {
                val tileContactViewHolder = TileTaskViewHolder(titleTv, descriptionTv, deadlineTv)
                taskTileView = root
                (taskTileView as LinearLayout).tag = tileContactViewHolder
            }
        }

        return super.getView(position, convertView, parent)
    }

    private data class TileTaskViewHolder(val titleTv: TextView, val description: TextView, val deadline: TextView)
}