package vn.snapbuck.sos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.snapbuck.sos.R;


/**
 * Created by sb3 on 5/26/15.
 */
public class SBCustomDialog extends Dialog {
    //private Dialog _dialog;

    protected SBCustomDialog(Context context) {
        super(context, false, null);
    }

    protected SBCustomDialog(Context context, int theme) {
        super(context, false, null);
    }

    public static class Builder {
        private Context _context;
        private SBCustomDialog _sbCustomDialog;
        private boolean isSingleDialog = false;
        private String _title;
        private String _message;
        private String _positiveButtonText;
        private String _negativeButtonText;
        private String _singleButtonText;
        private View.OnClickListener _positiveButtonListener;
        private View.OnClickListener _negativeButtonListener;
        private View.OnClickListener _singleButtonListener;

        public Builder(Context context) {
            _context = context;
            _sbCustomDialog = new SBCustomDialog(context, R.style.FullHeightDialog);
            _sbCustomDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            _sbCustomDialog.setCancelable(false);
            _sbCustomDialog.setCanceledOnTouchOutside(true);
            int divierId = _sbCustomDialog.getContext().getResources()
                    .getIdentifier("android:id/titleDivider", null, null);
            View divider = _sbCustomDialog.findViewById(divierId);
            if(divider!=null)
                divider.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        }

        public SBCustomDialog.Builder setTitle(int textID) {
            _title = _context.getResources().getString(textID);
            return this;
        }

        public SBCustomDialog.Builder setMessage(int messageId, String... messageArguments) {
            _message = _context.getResources().getString(messageId);
            for (int i = 0; i < messageArguments.length; i++) {
                _message = _message.replace("{" + i + "}", messageArguments[i]);
            }
            return this;
        }

        public SBCustomDialog.Builder setMessage(String message) {
            _message = message;
//            for (int i = 0; i < messageArguments.length; i++) {
//                _message = _message.replace("{" + i + "}", messageArguments[i]);
//            }
            return this;
        }

        public SBCustomDialog.Builder setPositiveButton(int textId, android.view.View.OnClickListener listener) {
            _positiveButtonText = _context.getResources().getString(textId);
            _positiveButtonListener = listener;
            return this;
        }

        public SBCustomDialog.Builder setNegativeButton(int textId, android.view.View.OnClickListener listener) {
            _negativeButtonText = _context.getResources().getString(textId);
            _negativeButtonListener = listener;
            return this;
        }

        public SBCustomDialog.Builder setSingleButton(int textId, android.view.View.OnClickListener listener) {
            isSingleDialog = true;
            _singleButtonText = _context.getResources().getString(textId);
            _singleButtonListener = listener;
            return this;
        }

        public SBCustomDialog.Builder setCancelable(boolean cancelable) {
            _sbCustomDialog.setCancelable(cancelable);
            return this;
        }

        public SBCustomDialog.Builder setOutsideCancel(boolean cancelable){
            _sbCustomDialog.setCanceledOnTouchOutside(cancelable);
            return this;
        }

        public SBCustomDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
            _sbCustomDialog.setOnCancelListener(onCancelListener);
            return this;
        }

        public SBCustomDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
            _sbCustomDialog.setOnDismissListener(onDismissListener);
            return this;
        }

        public SBCustomDialog show() {
            if(isSingleDialog) {
                _sbCustomDialog.setContentView(R.layout.dialog_custom_no_title);

                // no title

                // message
                TextView contentDialog = (TextView) _sbCustomDialog.findViewById(R.id.tvContentDialogSingleNotitle);
                contentDialog.setText(_message);

                Button confirmDialog = (Button) _sbCustomDialog
                        .findViewById(R.id.btnCancelDialogSingleNoTitle);
                confirmDialog.setText(_singleButtonText);
                confirmDialog.setOnClickListener(_singleButtonListener);
            } else {
                _sbCustomDialog.setContentView(R.layout.dialog_custom_with_title);

                TextView titleDialog = (TextView) _sbCustomDialog
                        .findViewById(R.id.tvTitle);
                if(titleDialog != null)
                    titleDialog.setText(_title);

                if(_title == null || _title.equals("")){
                    titleDialog.setVisibility(View.GONE);
                }


                TextView contentDialog = (TextView) _sbCustomDialog
                        .findViewById(R.id.tvContentDialogFullTitle);
                contentDialog.setText(_message);

                Button cancelDialog = (Button) _sbCustomDialog
                        .findViewById(R.id.btnCancelDialogFullTitle);
                cancelDialog.setText(_negativeButtonText);
                cancelDialog.setOnClickListener(_negativeButtonListener);

                Button confirmDialog = (Button) _sbCustomDialog
                        .findViewById(R.id.btnConfirmDialogFullTitle);
                confirmDialog.setText(_positiveButtonText);
                confirmDialog.setOnClickListener(_positiveButtonListener);
            }

            _sbCustomDialog.show();
            return _sbCustomDialog;
        }
    }
}
