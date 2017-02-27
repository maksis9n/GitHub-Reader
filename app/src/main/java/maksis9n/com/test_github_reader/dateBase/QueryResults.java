package maksis9n.com.test_github_reader.dateBase;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by maksis9n on 27.02.2017.
 */
@Table(database = DataBase.class)
public class QueryResults extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String name;

    @Column
    String fullName;

    @Column
    String description;

    @Column
    Date createdAt;

    @Column
    Date updateAt;

    @Column
    String stargazersCount;

    @Column
    String language;

    @ForeignKey
    QueryHistory queryHistory;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(String stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public QueryHistory getQueryHistory() {
        return queryHistory;
    }

    public void setQueryHistory(QueryHistory queryHistory) {
        this.queryHistory = queryHistory;
    }
}
