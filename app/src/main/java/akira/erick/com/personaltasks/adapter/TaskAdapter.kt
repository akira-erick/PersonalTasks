package akira.erick.com.personaltasks.adapter

import akira.erick.com.personaltasks.R
import akira.erick.com.personaltasks.model.Task
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class TaskAdapter(context: Context, private val taskList: MutableList<Task>) :
    ArrayAdapter<Task> (
        context,
        R.layout.tile_task,
        taskList
    ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

}