package prudential.pobmobilecustomerappandroid.utils;

import android.os.AsyncTask;

public class AsyncTaskCustom extends AsyncTask<Void, Void, Object> {

	
	//############################################//
	//#GET SET
	//############################################//

	
	private Call call;
	private Object resp;
	
	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}
	
	
	//############################################//
	//#EXECUTE
	//############################################//


	@Override
	protected Object doInBackground(Void... params) {

		return call.onRun();
	}

	public Object getResp() {
		return resp;
	}

	public void setResp(Object resp) {
		this.resp = resp;
	}

	

	@Override
	protected void onPostExecute(Object result) {
		call.onComplete(result);
	}

	// ###################################################//
	// #INTERFACE
	// ###################################################//

	public interface Call {

		public Object onRun();
		public void onComplete(Object result);

	}

	

}
