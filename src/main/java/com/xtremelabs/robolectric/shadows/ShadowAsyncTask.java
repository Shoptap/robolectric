package com.xtremelabs.robolectric.shadows;

import android.os.AsyncTask;
import android.os.ShadowAsyncTaskBridge;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.internal.Implementation;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;

import java.util.concurrent.*;

@Implements(AsyncTask.class)
public class ShadowAsyncTask<Params, Progress, Result> {

	@RealObject private AsyncTask<Params, Progress, Result> realAsyncTask;
    
	private final FutureTask<Result> future;
	private final BackgroundWorker worker;
	
	public ShadowAsyncTask() {
		worker = new BackgroundWorker();
		future = new FutureTask<Result>(worker) {
        	@Override
        	protected void done() {
				try {
					final Result result = get();
					Robolectric.getUiThreadScheduler().post(new Runnable() {
						@Override public void run() {
							getBridge().onPostExecute(result);
						}
					});
				} catch (CancellationException e) {
					Robolectric.getUiThreadScheduler().post(new Runnable() {
						@Override public void run() {
							getBridge().onCancelled();
						}
					});
					return;
				} catch (InterruptedException e) {
					// Ignore.
				} catch (Throwable t) {
					throw new RuntimeException("An error occured while executing doInBackground()",
							t.getCause());
				}
        	}
        };
	}

	@Implementation
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Implementation
    public boolean cancel(boolean mayInterruptIfRunning) {
        return future.cancel(mayInterruptIfRunning);
    }

    @Implementation
    public Result get() throws InterruptedException, ExecutionException {
        return future.get();
    }

    @Implementation
    public Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(timeout, unit);
    }

    @Implementation
    public AsyncTask<Params, Progress, Result> execute(final Params... params) {
        getBridge().onPreExecute();
        
        worker.params = params;

        Robolectric.getBackgroundScheduler().post(new Runnable() {
            @Override public void run() {
            	future.run();
            }
        });
        
        return null;
    }

    /**
     * Enqueue a call to {@link AsyncTask#onProgressUpdate(Object[])} on UI looper (or run it immediately
     * if the looper it is not paused).
     *
     * @param values The progress values to update the UI with.
     * @see AsyncTask#publishProgress(Object[])
     */
    @Implementation
    public void publishProgress(final Progress... values) {
        Robolectric.getUiThreadScheduler().post(new Runnable() {
            @Override public void run() {
                getBridge().onProgressUpdate(values);
            }
        });
    }

    private ShadowAsyncTaskBridge<Params, Progress, Result> getBridge() {
        return new ShadowAsyncTaskBridge<Params, Progress, Result>(realAsyncTask);
    }
    
    private final class BackgroundWorker implements Callable<Result> {
    	Params[] params;
		@Override
		public Result call() throws Exception {
			return getBridge().doInBackground(params);
		}
	}
}
