package akira.erick.com.personaltasks.model

import akira.erick.com.personaltasks.model.Constant.INVALID_TASK_ID
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: Int? = INVALID_TASK_ID,
    var title: String = "",
    var description: String = "",
    var deadline: String = ""
): Parcelable
