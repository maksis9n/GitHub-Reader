package maksis9n.com.test_github_reader.DateBase;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by maksis9n on 27.02.2017.
 */
@Table(database = DataBase.class)
public class QueryHistory extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String nameUser;

    @Column
    String typeRepository;

    @Column
    Date timeRequest;

    public int getId() {
        return id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getTypeRepository() {
        return typeRepository;
    }

    public void setTypeRepository(String typeRepository) {
        this.typeRepository = typeRepository;
    }

    public Date getTimeRequest() {
        return timeRequest;
    }

    public void setTimeRequest(Date timeRequest) {
        this.timeRequest = timeRequest;
    }
}
