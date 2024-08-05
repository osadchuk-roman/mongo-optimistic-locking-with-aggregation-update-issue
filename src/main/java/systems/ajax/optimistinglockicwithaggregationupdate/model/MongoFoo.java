package systems.ajax.optimistinglockicwithaggregationupdate.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@TypeAlias("Foo")
@Document(collection = "foo")
public class MongoFoo {
    @Field("_id")
    @Id
    private ObjectId id;

    @Field("name")
    private String name;

    @Version
    @Field("version")
    private Long version;

    public MongoFoo(ObjectId id, String name, Long version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
