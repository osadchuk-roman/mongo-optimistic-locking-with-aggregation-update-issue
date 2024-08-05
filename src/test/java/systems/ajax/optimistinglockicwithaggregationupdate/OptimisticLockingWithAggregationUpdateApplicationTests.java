package systems.ajax.optimistinglockicwithaggregationupdate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.test.StepVerifier;
import systems.ajax.optimistinglockicwithaggregationupdate.model.MongoFoo;

@SpringBootTest
class OptimisticLockingWithAggregationUpdateApplicationTests {

    @Autowired
    private ReactiveMongoOperations mongoOperations;

    @Test
    void testOptimisticLockingWithAggregationUpdate() {
        var initial = new MongoFoo(null, "foo-" + System.currentTimeMillis(), 0L);
        mongoOperations.insert(initial).block();

        var updateresult = mongoOperations.updateFirst(
            new Query(Criteria.where("name").is("not-existing-foo-name").and("version").is(initial.getVersion())),
            AggregationUpdate.update()
                .set("name").toValue("new-foo-name"),
            MongoFoo.class
        );

        StepVerifier.create(updateresult)
            .expectError(OptimisticLockingFailureException.class)
            .verify();
    }

    @Test
    void testOptimisticLockingWithSimpleUpdate() {
        var initial = new MongoFoo(null, "foo-" + System.currentTimeMillis(), 0L);
        mongoOperations.insert(initial).block();

        var updateresult = mongoOperations.updateFirst(
            new Query(Criteria.where("name").is("not-existing-foo-name").and("version").is(initial.getVersion())),
            Update.update("name", "new-foo-name"),
            MongoFoo.class
        );

        StepVerifier.create(updateresult)
            .expectError(OptimisticLockingFailureException.class)
            .verify();
    }

}
