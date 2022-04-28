import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.app.authenticationapp.R

object LoadingDialog {
    var dialog: Dialog? = null

    fun showLoadDialog(context: Context) {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
        dialog = Dialog(context)

        dialog!!.apply {
            window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.dialog_loading)
            show()
        }
    }

    fun hideLoadDialog() {
        dialog?.dismiss()
    }

    fun isDialogShowing(): Boolean {
        return dialog!!.isShowing
    }

}