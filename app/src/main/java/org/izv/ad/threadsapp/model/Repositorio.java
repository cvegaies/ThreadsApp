package org.izv.ad.threadsapp.model;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.net.ssl.HttpsURLConnection;

public class Repositorio {

    //https://stackoverflow.com/questions/61023968/what-do-i-use-now-that-handler-is-deprecated
    
    private MutableLiveData<String> liveResult = new MutableLiveData<>();

    class AsynchronousTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            String result = "AsyncTask (deprecated): " + parseString(getData());
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            String result = (String) o;
            //setValue in MainThread
            liveResult.setValue(result);
        }
    }

    class MessageHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message message) {
            super.handleMessage(message);
            String result = (String) message.obj;
            //setValue in MainThread
            liveResult.setValue(result);
        }
    }

    public Repositorio() {
        requestWithAsyncTask();
        requestWithExecutor();
        requestWithExecutorService();
        requestWithHandlerMessage();
        requestWithHandlerPost();
        requestWithRunnable();
        requestWithThread();
    }

    private String getData() {
        String baseUrl = "https://jsonplaceholder.typicode.com/todos/";
        Random ran = new Random();
        int randomNum = ran.nextInt(100) + 1;
        //return processPageUrl(baseUrl + randomNum);
        return processPageConnection(baseUrl + randomNum);
    }

    public LiveData<String> getResult() {
        return liveResult;
    }

    private String parseString(String json) {
        JSONObject jsonObject = null;
        String result;
        try {
            jsonObject = new JSONObject(json);
            result = (String)jsonObject.get("title");
        } catch (JSONException e) {
            result = e.getLocalizedMessage();
        }
        return result;
    }

    /* request a page using the GET method */
    private String processPageConnection(String src) {
        StringBuffer out = new StringBuffer();
        try {
            URL url = new URL(src);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");
            //connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                out.append(line + "\n");
            }
            in.close();
        } catch (IOException e) {
        }
        return out.toString();
    }

    /* request a page using the GET method */
    private String processPageUrl(String src) {
        StringBuffer out = new StringBuffer();
        try {
            URL url = new URL(src);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                out.append(line + "\n");
            }
            in.close();
        } catch (IOException e) {
        }
        return out.toString();
    }

    public void requestWithAsyncTask() {
        AsynchronousTask task = new AsynchronousTask();
        task.execute();
    }

    public void requestWithExecutor() {
        //Executor mainThread = ContextCompat.getMainExecutor(context);
        ScheduledExecutorService backgroundThread = Executors.newSingleThreadScheduledExecutor();
        backgroundThread.execute(new Runnable() {
            @Override
            public void run() {
                String result = "ScheduledExecutorService: " + parseString(getData());
                liveResult.postValue(result);
                /*mainThread.execute(new Runnable() {
                    @Override
                    public void run() {
                        //setValue in MainThread
                        liveResult.setValue(result);
                    }
                });*/
            }
        });
    }

    public void requestWithExecutorService() {
        int threadsNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService threadExecutor = Executors.newFixedThreadPool(threadsNumber);
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //postValue in Threads
                liveResult.postValue("ExecutorService: " + parseString(getData()));
            }
        });
    }

    public void requestWithHandlerMessage() {
        Handler handler = new MessageHandler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message mensaje = new Message();
                mensaje.obj = "Handler (Message): " + parseString(getData());
                handler.sendMessage(mensaje);
            }
        };
        new Thread(runnable).start();
    }

    public void requestWithHandlerPost() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String result = "Handler (post()): " + parseString(getData());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //setValue in MainThread
                        liveResult.setValue(result);
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    public void requestWithRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //postValue in Threads
                liveResult.postValue("Runnable: " + parseString(getData()));
            }
        };
        new Thread(runnable).start();
    }

    public void requestWithThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String result = getData();
                String title = parseString(result);
                //postValue in Threads
                liveResult.postValue("Thread: " + title);
                Log.v("xyz", title);
            }
        };
        thread.start();
    }

}