import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import com.app.authenticationapp.R

fun Context.alertDialog(
    title: String = resources.getString(R.string.app_name),
    message: String = resources.getString(R.string.general_something_went_wrong),
    isCancelAble: Boolean = false,
    positiveBtnText: String = resources.getString(R.string.OK),
    negativeBtnText: String? = null,
    negativeClick: (() -> Unit)? = null,
    positiveClick: (() -> Unit)? = null

) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(isCancelAble)

    builder.setPositiveButton(positiveBtnText) { _, _ ->
        positiveClick?.invoke()
    }

    negativeBtnText?.let {
        builder.setNegativeButton(negativeBtnText) { _, _ ->
            negativeClick?.invoke()
        }
    }
    val alertDialog = builder.create()
    alertDialog.show()
    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        .setTextColor(Color.BLACK)
    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        .setTextColor(Color.BLACK)
}

fun Context.showInternetDialog(
    isCancelAble: Boolean = false,
    positiveClick: (() -> Unit)? = null
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(this.getString(R.string.app_name))
    builder.setMessage(this.getString(R.string.network_connection_error))
    builder.setPositiveButton(this.getString(R.string.OK)) { _, _ ->
        positiveClick?.invoke()
    }

    builder.setCancelable(isCancelAble)
    val alertDialog = builder.create()
    alertDialog.show()
}


