
package fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import adapter.ChatAdapter;
import callback.CustomCallback;
import keys.PubnubKeys;
import pojo.Message;
import com.example.teamnullpointer.ridesharenp.R;

/**
 * Created by Anjith Sasindran
 * on 11-Oct-15.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {

    String TAG = "ChatFragment";
    SharedPreferences sharedPreferences;
    CustomCallback callback;
    Context context;
    Pubnub pubnub;
    EditText chatMessage;
    Button send;
    RecyclerView chatList;
    ArrayList<String> chatMessageList;
    ChatAdapter chatAdapter;
    Gson gson;
    JSONObject messageObject;
    String username, host, hostusername;
    Button backbut, mapbut, usersbut, reportbut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        setHasOptionsMenu(true);


        context = getActivity();
        callback = (CustomCallback) context;
        sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);
        gson = new Gson();


        host = sharedPreferences.getString("hostEmail", null);
        username = sharedPreferences.getString("userName", null);
        hostusername = sharedPreferences.getString("hostuserName", null);
        getActivity().setTitle(hostusername + "'s room");


        chatMessageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessageList, username);

        chatList = (RecyclerView) view.findViewById(R.id.chatlist);
        chatList.setLayoutManager(new LinearLayoutManager(context));
        chatList.setAdapter(chatAdapter);

        chatMessage = (EditText) view.findViewById(R.id.message);
        send = (Button) view.findViewById(R.id.send);
        send.setOnClickListener(this);

        backbut = (Button) view.findViewById(R.id.backbutid);
        mapbut = (Button) view.findViewById(R.id.mapbutid);
        usersbut = (Button) view.findViewById(R.id.viewuserbutid);
        reportbut = (Button) view.findViewById(R.id.reportbutid);

        backbut.setText("Back");
        mapbut.setText("Map");
        usersbut.setText("Users");
        reportbut.setText("Report");

        clicks();

        pubnub = new Pubnub(PubnubKeys.PUBLISH_KEY, PubnubKeys.SUBSCRIBE_KEY);

        //SET channel name
        PubnubKeys pnk = new PubnubKeys(host);

        try {
            pubnub.subscribe(PubnubKeys.CHANNEL_NAME, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    chatMessageList.add(message.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                         //   chatAdapter.notifyItemInserted(chatMessageList.size() - 1); //BUGGED WITH RECYCLE VIEW
                            chatAdapter.notifyDataSetChanged();
                            chatList.scrollToPosition(chatMessageList.size() - 1);
                        }
                    });
                    Log.d("successCallback", "message " + message);
                }

                @Override
                public void successCallback(String channel, Object message, String timetoken) {
                    super.successCallback(channel, message, timetoken);
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    super.errorCallback(channel, error);
                    Log.d("errorCallback", "error " + error);
                }

                @Override
                public void connectCallback(String channel, Object message) {
                    super.connectCallback(channel, message);
                    Log.d("connectCallback", "message " + message);
                }

                @Override
                public void reconnectCallback(String channel, Object message) {
                    super.reconnectCallback(channel, message);
                    Log.d("reconnectCallback", "message " + message);
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    super.disconnectCallback(channel, message);
                    Log.d("disconnectCallback", "message " + message);
                }
            });
        } catch (PubnubException pe) {
            Log.d(TAG, pe.toString());
        }

        return view;

    }

    private void clicks() {

        mapbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent(ctx, Maps.class));

                /*class startBackgroundTask extends AsyncTask<Void, Void, String> {

                    String json_url;
                    String json_string;
                    @Override
                    protected void onPreExecute() {
                        //json_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_rider.php";
                    }

                    @Override
                    protected String doInBackground(Void... voids) {
                        String JSON_STRING;
                        try {
                            URL url = new URL(json_url);
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder stringBuilder = new StringBuilder();
                            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                                stringBuilder.append(JSON_STRING+"\n");
                            }
                            bufferedReader.close();
                            inputStream.close();
                            httpURLConnection.disconnect();
                            return stringBuilder.toString().trim();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    protected void onProgressUpdate (Void...values){
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute (String result){
                        // TextView textView = (TextView) findViewById(R.id.textView);
                        // textView.setText(result);
                        json_string = result;
                        //Intent intent = new Intent(ctx, ShowRiderPosts.class);
                        //intent.putExtra("json_data",json_string);
                        //startActivity(intent);


                        String zip1 = "95624";
                        String zip2 = "95824";

                        String uri = "https://www.google.com/maps/dir/" + zip1 + "/" + zip2;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                }*/


                String zip1 = "95624";
                String zip2 = "95824";

                String uri = "https://www.google.com/maps/dir/" + zip1 + "/" + zip2;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }

        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            //callback.loginActivity(1);
            //sharedPreferences.edit().remove("username").apply();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (send.getId() == view.getId()) {
            String message = chatMessage.getText().toString().trim();
            if (message.length() != 0) {
                message = gson.toJson(new Message(username, message));
                try {
                    messageObject = new JSONObject(message);
                } catch (JSONException je) {
                    Log.d(TAG, je.toString());
                }
                chatMessage.setText("");
                pubnub.publish(PubnubKeys.CHANNEL_NAME, messageObject, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        super.successCallback(channel, message);
                        Log.d("successCallback", "message " + message);
                    }

                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        super.errorCallback(channel, error);
                        Log.d("errorCallback", "error " + error);
                    }
                });
            } else {
                Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        pubnub.unsubscribe(PubnubKeys.CHANNEL_NAME);
        Log.d(TAG, "Un subscribed");
    }

}