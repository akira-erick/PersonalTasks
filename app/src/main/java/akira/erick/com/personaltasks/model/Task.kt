package akira.erick.com.personaltasks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var title: String = "",
    var description: String = "",
    var deadline: String = ""
): Parcelable
