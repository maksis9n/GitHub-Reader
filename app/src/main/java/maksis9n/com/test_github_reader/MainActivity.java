package maksis9n.com.test_github_reader;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import maksis9n.com.test_github_reader.DateBase.QueryHistory;
import maksis9n.com.test_github_reader.DateBase.QueryHistory_Table;
import maksis9n.com.test_github_reader.DateBase.QueryResults;


public class MainActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private Button buttonSearch;
    private EditText editTextNameUser;
    private Spinner spinnerTypeRepositories;

    private QueryHistory queryHistory;

    private HistoryAdapter historyAdapter;
    private String login;
    private int selectedItemPosition;
    private String type;

    static final String BASE_URL = "https://api.github.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] typesRepositories = {"Все", "Владелец", "Участник"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesRepositories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTypeRepositories = (Spinner) findViewById(R.id.spinner_type_repositories);
        spinnerTypeRepositories.setAdapter(adapter);

        historyRecyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter();
        historyRecyclerView.setAdapter(historyAdapter);

        editTextNameUser = (EditText) findViewById(R.id.edit_text_name_user);

        buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitializationData();
                CreateListRepositories(login, type);
            }
        });
    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(BASE_URL + "/" + login + "/repos?type=" + type);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();

                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null) {
                response = "ERROR";
            }
            response = "{\"f\":" + response + "}";
            Log.i("INFO", response);

            JSONObject dataJsonObj = null;
            try {
                dataJsonObj = new JSONObject(response);
                JSONArray jsonArray = dataJsonObj.getJSONArray("f");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    QueryResults queryResults = new QueryResults();
                    queryResults.setName(jsonObject.getString("name"));
                    queryResults.setDescription(jsonObject.getString("description"));
                    queryResults.setFullName(jsonObject.getString("full_name"));
                    queryResults.setCreatedAt(jsonObject.getString("created_at"));
                    queryResults.setUpdateAt(jsonObject.getString("updated_at"));
                    queryResults.setStargazersCount(jsonObject.getString("stargazers_count"));
                    queryResults.setLanguage(jsonObject.getString("language"));
                    queryResults.setQueryHistory(queryHistory);
                    queryResults.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = ResultActivity.newIntent(MainActivity.this, queryHistory.getId());
            startActivity(intent);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void InitializationData() {
        login = editTextNameUser.getText().toString();
        selectedItemPosition = spinnerTypeRepositories.getSelectedItemPosition();

        switch (selectedItemPosition) {
            case 0:
                type = "all";
                break;
            case 1:
                type = "owner";
                break;
            case 2:
                type = "member";
                break;
            default:
                type = "owner";
                break;
        }
    }

    private void NewThread() {
        InitializationData();
        queryHistory = new QueryHistory();
        queryHistory.setNameUser(login);
        queryHistory.setTypeRepository(type);
        Date curretDate = (Date) Calendar.getInstance().getTime();
        queryHistory.setTimeRequest(curretDate);
        queryHistory.save();

        historyRecyclerView.getAdapter().notifyDataSetChanged();

        new RetrieveFeedTask().execute();

    }

    private void CreateListRepositories(String username, String typeRep) {
        boolean notFound = true;
        List<QueryHistory> queryHistoryList = new Select()
                .from(QueryHistory.class)
                .where(QueryHistory_Table.nameUser.eq(username))
                .and(QueryHistory_Table.typeRepository.eq(typeRep))
                .queryList();
        if (queryHistoryList.size() == 0) {
            NewThread();
            notFound = false;
        }

        Date curretDate = Calendar.getInstance().getTime();
        Date requestDate;
        long tmp;

        for (int i = 0; i < queryHistoryList.size(); i++) {
            requestDate = queryHistoryList.get(i).getTimeRequest();
            tmp = ((curretDate.getTime() - requestDate.getTime()) / 1000) / 60;
            if (tmp < 5) {
                Intent intent = ResultActivity.newIntent(MainActivity.this, queryHistoryList.get(i).getId());

                QueryHistory queryHistoryTmp = new QueryHistory();
                queryHistoryTmp.setNameUser(queryHistoryList.get(i).getNameUser());
                queryHistoryTmp.setTypeRepository(queryHistoryList.get(i).getTypeRepository());
                Date date = (Date) Calendar.getInstance().getTime();
                queryHistoryTmp.setTimeRequest(date);
                queryHistoryTmp.save();

                historyRecyclerView.getAdapter().notifyDataSetChanged();

                startActivity(intent);
                notFound = false;
                break;
            }
        }
        if (notFound) NewThread();
    }

    private class HistoryHolder extends RecyclerView.ViewHolder {

        private TextView nameUserTextView;
        private TextView typeRepositoriesTextView;

        public HistoryHolder(View itemView) {
            super(itemView);
            nameUserTextView = (TextView) itemView.findViewById(R.id.list_item_type_repositories);
            typeRepositoriesTextView = (TextView) itemView.findViewById(R.id.list_item_name_user);
        }
    }

    private class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {

        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_query_history, parent, false);
            return new HistoryHolder(view);
        }

        @Override
        public void onBindViewHolder(final HistoryHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QueryHistory tmpHistory = new Select()
                            .from(QueryHistory.class)
                            .where(QueryHistory_Table.nameUser.eq(holder.nameUserTextView.getText().toString()))
                            .and(QueryHistory_Table.typeRepository.eq(holder.typeRepositoriesTextView.getText().toString()))
                            .querySingle();

                    CreateListRepositories(tmpHistory.getNameUser(), tmpHistory.getTypeRepository());
                }
            });
            List<QueryHistory> queryHistory = new Select().from(QueryHistory.class).orderBy(QueryHistory_Table.id, false).limit(2).queryList();
            if (queryHistory.size() == 2) {
                holder.nameUserTextView.setText(queryHistory.get(position).getNameUser().toString());
                holder.typeRepositoriesTextView.setText(queryHistory.get(position).getTypeRepository().toString());
            } else if (queryHistory.size() == 1 && position == 0) {
                holder.nameUserTextView.setText(queryHistory.get(position).getNameUser().toString());
                holder.typeRepositoriesTextView.setText(queryHistory.get(position).getTypeRepository().toString());
            } else {
                holder.nameUserTextView.setText("user");
                holder.typeRepositoriesTextView.setText("type");
            }

        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
