package io.legado.app.data.entities

import android.os.Parcelable
import android.text.TextUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

@Parcelize
@Entity(
    tableName = "replace_rules",
    indices = [(Index(value = ["id"]))]
)
data class ReplaceRule(
    @PrimaryKey(autoGenerate = true)
    var id: Long = System.currentTimeMillis(),
    //名称
    @ColumnInfo(defaultValue = "")
    var name: String = "",
    //分组
    var group: String? = null,
    //替换内容
    @ColumnInfo(defaultValue = "")
    var pattern: String = "",
    //替换为
    @ColumnInfo(defaultValue = "")
    var replacement: String = "",
    //作用范围
    var scope: String? = null,
    //作用于标题
    @ColumnInfo(defaultValue = "0")
    var scopeTitle: Boolean = false,
    //作用于正文
    @ColumnInfo(defaultValue = "1")
    var scopeContent: Boolean = true,
    //是否启用
    @ColumnInfo(defaultValue = "1")
    var isEnabled: Boolean = true,
    //是否正则
    @ColumnInfo(defaultValue = "1")
    var isRegex: Boolean = true,
    //排序
    @ColumnInfo(name = "sortOrder", defaultValue = "0")
    var order: Int = 0
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is ReplaceRule) {
            return other.id == id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun isValid(): Boolean {
        if (TextUtils.isEmpty(pattern)) {
            return false
        }
        //判断正则表达式是否正确
        if (isRegex) {
            try {
                Pattern.compile(pattern)
            } catch (ex: PatternSyntaxException) {
                return false
            }
        }
        return true
    }
}