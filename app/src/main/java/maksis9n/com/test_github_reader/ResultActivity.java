package maksis9n.com.test_github_reader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle() == getString(R.string.setting_date_ascending)) {
            Collections.sort(queryResultsList, DateAscending);
            resultRecyclerView.getAdapter().notifyDataSetChanged();
            return true;
        } else if (item.getTitle() == getString(R.string.setting_date_descending)) {
            Collections.sort(queryResultsList, DateDescending);
            resultRecyclerView.getAdapter().notifyDataSetChanged();
            return true;
        } else if (item.getTitle() == getString(R.string.setting_name_ascending)) {
            Collections.sort(queryResultsList, NameAscending);
            resultRecyclerView.getAdapter().notifyDataSetChanged();
            return true;
        } else if (item.getTitle() == getString(R.string.setting_name_descending)) {
            Collections.sort(queryResultsList, NameDescending);
            resultRecyclerView.getAdapter().notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            holder.name.setText("Название: " + queryResultsList.get(position).getName());
            holder.description.setText("Описание: " + queryResultsList.get(position).getDescription());
            holder.createdAt.setText("Дата создания: " + queryResultsList.get(position).getCreatedAt());
            holder.updateAt.setText("Дата обновления: " + queryResultsList.get(position).getUpdateAt());
            holder.stargazersCount.setText("Количество звезд: " + queryResultsList.get(position).getStargazersCount());
            holder.language.setText("Язык: " + queryResultsList.get(position).getLanguage());
        }

        @Override
        public int getItemCount() {
            return queryResultsList.size();
        }
    }

    private static Comparator<QueryResults> DateAscending = new Comparator<QueryResults>() {
        @Override
        public int compare(QueryResults o1, QueryResults o2) {
            return o2.getCreatedAt().compareTo(o1.getCreatedAt());
        }
    };

    private static Comparator<QueryResults> DateDescending = new Comparator<QueryResults>() {
        @Override
        public int compare(QueryResults o1, QueryResults o2) {
            return o1.getCreatedAt().compareTo(o2.getCreatedAt());
        }
    };

    private static Comparator<QueryResults> NameAscending = new Comparator<QueryResults>() {
        @Override
        public int compare(QueryResults o1, QueryResults o2) {
            return o2.getName().compareTo(o1.getName());
        }
    };
    private static Comparator<QueryResults> NameDescending = new Comparator<QueryResults>() {
        @Override
        public int compare(QueryResults o1, QueryResults o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
}
