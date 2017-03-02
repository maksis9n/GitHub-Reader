package maksis9n.com.test_github_reader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

import maksis9n.com.test_github_reader.DateBase.QueryResults;
import maksis9n.com.test_github_reader.DateBase.QueryResults_Table;

public class ResultActivity extends AppCompatActivity {

    private RecyclerView resultRecyclerView;
    private ResultAdapter resultAdapter;
    private List<QueryResults> queryResultsList;

    private static final String EXTRA_ID =
            "maksis9n.com.criminalintent.extraid";

    public static Intent newIntent(Context packagecontext, int extraId) {
        Intent intent = new Intent(packagecontext, ResultActivity.class);
        intent.putExtra(EXTRA_ID, extraId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int extraId = (int) getIntent().getSerializableExtra(EXTRA_ID);

        queryResultsList = new Select()
                .from(QueryResults.class)
                .where(QueryResults_Table.queryHistory_id.eq(extraId))
                .queryList();

        resultRecyclerView = (RecyclerView) findViewById(R.id.result_recycler_view);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapter();
        resultRecyclerView.setAdapter(resultAdapter);

    }


    private class ResultHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView description;
        private TextView createdAt;
        private TextView updateAt;
        private TextView stargazersCount;
        private TextView language;

        public ResultHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.list_item_name);
            description = (TextView) itemView.findViewById(R.id.list_item_description);
            createdAt = (TextView) itemView.findViewById(R.id.list_item_created_at);
            updateAt = (TextView) itemView.findViewById(R.id.list_item_update_at);
            stargazersCount = (TextView) itemView.findViewById(R.id.list_item_stargazers_count);
            language = (TextView) itemView.findViewById(R.id.list_item_language);
        }

    }

    private class ResultAdapter extends RecyclerView.Adapter<ResultHolder> {

        @Override
        public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ResultActivity.this);
            View view = layoutInflater.inflate(R.layout.list_item_query_results, parent, false);
            return new ResultHolder(view);
        }

        @Override
        public void onBindViewHolder(ResultHolder holder, int position) {
            holder.name.setText(queryResultsList.get(position).getName());
            holder.description.setText(queryResultsList.get(position).getDescription());
            holder.createdAt.setText(queryResultsList.get(position).getCreatedAt());
            holder.updateAt.setText(queryResultsList.get(position).getUpdateAt());
            holder.stargazersCount.setText(queryResultsList.get(position).getStargazersCount());
            holder.language.setText(queryResultsList.get(position).getLanguage());
        }

        @Override
        public int getItemCount() {
            return queryResultsList.size();
        }
    }
}
