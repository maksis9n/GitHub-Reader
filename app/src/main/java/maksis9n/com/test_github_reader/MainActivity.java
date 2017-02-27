package maksis9n.com.test_github_reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import maksis9n.com.test_github_reader.dateBase.QueryResults;


public class MainActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private Button buttonSearch;

    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] typesRepositories = {"Все", "Владелец", "Участник"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typesRepositories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerTypeRepositories = (Spinner) findViewById(R.id.spinner_type_repositories);
        spinnerTypeRepositories.setAdapter(adapter);

        spinnerTypeRepositories.setPrompt(getString(R.string.textViewTypeRepositories));

        historyRecyclerView = (RecyclerView) findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter();
        historyRecyclerView.setAdapter(historyAdapter);

        buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                startActivity(intent);
            }
        });

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
        public void onBindViewHolder(HistoryHolder holder, int position) {
            holder.nameUserTextView.setText("user1");
            holder.typeRepositoriesTextView.setText("all");
        }


        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
