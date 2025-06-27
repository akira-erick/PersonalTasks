package akira.erick.com.personaltasks.model

import akira.erick.com.personaltasks.model.Constant.INVALID_TASK_ID
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = INVALID_TASK_ID,
    var title: String = "",
    var description: String = "",
    var deadline: String = "",
    var makeit: Int = 0
): Parcelable
