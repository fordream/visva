package com.japanappstudio.IDxPassword.activities;

import java.io.File;

import com.japanappstudio.IDxPassword.activities.homescreen.HomeScreeenActivity;
import com.japanappstudio.IDxPassword.contants.Contants;
import com.japanappstudio.IDxPassword.database.IdManagerPreference;
import com.japanappstudio.IDxPassword.idxpwdatabase.IDxPWDataBaseHandler;
import com.japanappstudio.IDxPassword.idxpwdatabase.UserDB;

import net.sqlcipher.database.SQLiteDatabase;
import com.japanappstudio.IDxPassword.activities.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

@SuppressWarnings("deprecation")
public class MasterPasswordActivity extends BaseActivity implements
		OnClickListener,IParent {

	// =========================Control Define =====================
	private Button mBtnDone;
	private EditText mEditTextMasterPW;
	// private EditText mEditText
	// ========================Class Define =======================
	private IdManagerPreference mIdManagerPreference;
	// private DataBaseHandler mDataBaseHandler;
	private IDxPWDataBaseHandler mIDxPWDataBaseHandler;
	// ==========================Variable Define ===================
	private int mRemoveDataTimes;
	private int mNumberAtemppt = 0;
	private String mMasterPW;
	public Button btn_switch_keyboard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// init idmanager preference
		mIdManagerPreference = IdManagerPreference.getInstance(this);
		if (mIdManagerPreference.getValuesRemoveData() == Contants.KEY_OFF
				|| !mIdManagerPreference.isApplicationFirstTimeInstalled()) {
			/* go to HomeScreen activity */
			Intent intent = new Intent(MasterPasswordActivity.this,
					HomeScreeenActivity.class);
			startActivity(intent);
			finish();
		} else {
			setContentView(R.layout.master_password_2);
			getWindow().setBackgroundDrawable(
					getResources().getDrawable(R.drawable.setup_master_password));
			mIdManagerPreference.setSecurityLoop(true);
			// button
			mBtnDone = (Button) findViewById(R.id.btn_confirm_master_pw);
			mBtnDone.setOnClickListener(this);
			mEditTextMasterPW = (EditText) findViewById(R.id.editText_master_pw);
			mEditTextMasterPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
			btn_switch_keyboard = (Button) findViewById(R.id.btn_switch_keyboard);
			InputMethodManager imeManager = (InputMethodManager) getApplicationContext()
					.getSystemService(INPUT_METHOD_SERVICE);
			imeManager.showSoftInput(mEditTextMasterPW,
					InputMethodManager.SHOW_IMPLICIT);
			/* init database */
			SQLiteDatabase.loadLibs(this);
			// mDataBaseHandler = new DataBaseHandler(this);
			mIDxPWDataBaseHandler = new IDxPWDataBaseHandler(this);
			UserDB userTemp = mIDxPWDataBaseHandler
					.getUser(Contants.MASTER_PASSWORD_ID);
			mMasterPW = userTemp.getPassword();
			MyRelativeLayout activityRootView = (MyRelativeLayout) findViewById(R.id.root_view);
			activityRootView.setIParent(this);
		}
	}

	public void confirmMaster(View v) {

	}

	public void onReturn(View v) {
		finish();
	}

	public static void startActivity(Activity activity) {
		Intent i = new Intent(activity, MasterPasswordActivity.class);
		activity.startActivity(i);
	}


	@Override
	public void onClick(View v) {
		if (v == mBtnDone) {
			if ("".equals(mEditTextMasterPW.getText().toString())) {
				showDialog(Contants.DIALOG_MESSAGE_PW_EMPTY);
			} else if (mEditTextMasterPW.getText().toString().length() > Contants.MAX_LENGTH_PW) {

			} else {
				/* check remove data values */
				checkRemoveDataValues();
			}
			/* reset edit text master pw */
			mEditTextMasterPW.setText("");
		}
	}


	private void checkRemoveDataValues() {
		// TODO Auto-generated method stub
		if (!mMasterPW.equals(mEditTextMasterPW.getText().toString())) {
			if (mRemoveDataTimes == Contants.KEY_OFF) {
				showDialog(Contants.DIALOG_WRONG_PASS_NO_SECURE);
			} else {
				mNumberAtemppt++;
				if (mNumberAtemppt >= mRemoveDataTimes) {
					showDialog(Contants.DIALOG_REMOVED_DATA);
					mNumberAtemppt = 0;

				} else {
					showDialog(Contants.DIALOG_LOGIN_WRONG_PASS);
				}
			}
		} else {
			/* check security service */
			Intent intent = new Intent(MasterPasswordActivity.this,
					HomeScreeenActivity.class);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * remove data after type master password times limit to remove data values
	 */

	private void removeData() {
		/* remove database */
		File db = getDatabasePath(Contants.DATA_IDMANAGER_NAME);
		if (db.exists())
			db.delete();

		/* reset share preference */
		mIdManagerPreference.setApplicationFirstTimeInstalled(true);
		mIdManagerPreference.setMasterPW("");
		mIdManagerPreference.setSecurityMode(Contants.KEY_OFF);
		mIdManagerPreference.setValuesremoveData(Contants.KEY_OFF);
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText("");
		// finish activity
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mRemoveDataTimes = mIdManagerPreference.getValuesRemoveData();
		// mMasterPW = mDataBaseHandler.getUser(Contants.MASTER_PASSWORD_ID)
		// .getUserPassword();
		mMasterPW = mIDxPWDataBaseHandler.getUser(Contants.MASTER_PASSWORD_ID)
				.getPassword();
		mNumberAtemppt = 0;
	}

	/**
	 * connection to service to add security mode
	 */

	protected void onDestroy() {
		super.onDestroy();
		mIdManagerPreference.setSecurityLoop(false);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Contants.DIALOG_LOGIN_WRONG_PASS:
			return createDialog(Contants.DIALOG_LOGIN_WRONG_PASS);
		case Contants.DIALOG_WRONG_PASS_NO_SECURE:
			return createDialog(Contants.DIALOG_WRONG_PASS_NO_SECURE);
		case Contants.DIALOG_REMOVED_DATA:
			return createDialog(Contants.DIALOG_REMOVED_DATA);
		case Contants.DIALOG_MESSAGE_PW_EMPTY:
			return createDialog(Contants.DIALOG_MESSAGE_PW_EMPTY);
		case Contants.DIALOG_MESSAGE_PW_TOO_LONG:
			return createDialog(Contants.DIALOG_MESSAGE_PW_TOO_LONG);
		default:
			return null;
		}
	}

	/**
	 * Create and return an example alert dialog with an edit text box.
	 */
	private Dialog createDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch (id) {
		case Contants.DIALOG_LOGIN_WRONG_PASS:
			builder.setTitle(getResources().getString(R.string.app_name));
			builder.setMessage(getResources().getString(
					R.string.limit_login_msg, mNumberAtemppt));
			// builder.setMessage("Type the name of new folder:");
			builder.setIcon(R.drawable.icon);

			builder.setPositiveButton(
					getResources().getString(R.string.confirm_ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							return;
						}

					});
			return builder.create();
		case Contants.DIALOG_MESSAGE_PW_TOO_LONG:
			builder.setTitle(getResources().getString(R.string.app_name));
			builder.setMessage(getResources().getString(R.string.PassTooLong));
			builder.setIcon(R.drawable.icon);

			builder.setPositiveButton(
					getResources().getString(R.string.confirm_ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							return;
						}

					});
			return builder.create();
		case Contants.DIALOG_WRONG_PASS_NO_SECURE:
			builder.setTitle(getResources().getString(R.string.app_name));
			builder.setMessage(getResources()
					.getString(R.string.wrong_pw_login));
			// builder.setMessage("Type the name of new folder:");
			builder.setIcon(R.drawable.icon);

			builder.setPositiveButton(
					getResources().getString(R.string.confirm_ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							return;
						}

					});
			return builder.create();
		case Contants.DIALOG_MESSAGE_PW_EMPTY:
			builder.setTitle(getResources().getString(R.string.app_name));
			builder.setMessage(getResources()
					.getString(R.string.password_empty));
			// builder.setMessage("Type the name of new folder:");
			builder.setIcon(R.drawable.icon);

			builder.setPositiveButton(
					getResources().getString(R.string.confirm_ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							return;
						}

					});
			return builder.create();
		case Contants.DIALOG_REMOVED_DATA:
			builder.setTitle(getResources().getString(R.string.app_name));
			builder.setMessage(getResources()
					.getString(R.string.data_destroyed));
			// builder.setMessage("Type the name of new folder:");
			builder.setIcon(R.drawable.icon);

			builder.setPositiveButton(
					getResources().getString(R.string.confirm_ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							/* remove data */
							removeData();
							return;
						}

					});
			return builder.create();
		default:
			return null;
		}
	}
	public void onChangeKeyBoard(View v) {
		if (mEditTextMasterPW.getInputType() == InputType.TYPE_CLASS_NUMBER) {
			mEditTextMasterPW.setInputType(InputType.TYPE_CLASS_TEXT);
		} else {
			mEditTextMasterPW.setInputType(InputType.TYPE_CLASS_NUMBER);
		}

		InputMethodManager imeManager = (InputMethodManager) getApplicationContext()
				.getSystemService(INPUT_METHOD_SERVICE);
		imeManager.showSoftInput(mEditTextMasterPW,
				InputMethodManager.SHOW_IMPLICIT);
		mEditTextMasterPW.setTransformationMethod(PasswordTransformationMethod.getInstance());
	}
	@Override
	public void show_keyboard() {
		// TODO Auto-generated method stub
		btn_switch_keyboard.setVisibility(View.VISIBLE);
	}

	@Override
	public void hide_keyboard() {
		// TODO Auto-generated method stub
		btn_switch_keyboard.setVisibility(View.GONE);
	}

}
