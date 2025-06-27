package akira.erick.com.personaltasks.ui

interface OnDeletedTaskClickListener {
    fun onTaskClick(position: Int)
    fun onActivateTaskMenuItemClick(position: Int)
    fun onVisualizationMenuItemClick(position: Int)
}