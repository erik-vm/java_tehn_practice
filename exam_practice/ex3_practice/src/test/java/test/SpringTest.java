package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import runner.NoPointsIfThisTestFails;
import runner.Points;
import spring.*;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Config.class })
public class SpringTest {

    @Test
    @Points(5)
    public void constructDataSourceWidthBuilder() {
        var ctx = new AnnotationConfigApplicationContext(Config.class);

        DataSource ds = ctx.getBean(DataSource.class);

        assertTrue(DataSourceBuilder.isFromBuilder(ds));

        assertThat(DataSourceBuilder.getUrl(ds), is("my db url"));
    }

    @Test
    @Points(4)
    public void constructProductDaoWithCorrectDataSource() {
        var ctx = new AnnotationConfigApplicationContext(Config.class);

        ProductDao1 dao = ctx.getBean(ProductDao1.class);

        assertTrue(DataSourceBuilder.isFromBuilder(dao.getDataSource()));
    }

    @Test
    @Points(4)
    public void constructProductDaoWithPropertiesOfTheSameType() {
        var ctx = new AnnotationConfigApplicationContext(Config.class);

        ProductDao2 dao = ctx.getBean(ProductDao2.class);

        assertThat(dao.getKey1(), is("key 1 value"));

        assertThat(dao.getKey2(), is("key 1 value"));
    }

    @Test
    @Points(2)
    public void constructJustOneMatchingBean() {
        // should not throw
        getContextWithProfile("p2").getBean(Dao.class);
    }

    @Test
    @NoPointsIfThisTestFails
    public void usesOnlyAllowedConstructorParameters() {
        var params = Stream.of(ProductDao1.class, ProductDao2.class)
                .flatMap(c -> Arrays.stream(c.getDeclaredConstructors()))
                .flatMap(c -> Arrays.stream(c.getParameterTypes()))
                .toList();

        var allowed = List.of(DataSource.class, String.class);

        for (Class<?> param : params) {
            assertTrue("Constructor parameter of type "
                    + param +
                    " is not allowed",  allowed.contains(param));
        }
    }

    private AnnotationConfigApplicationContext getContextWithProfile(String profileName) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles(profileName);
        ctx.register(Config.class);
        ctx.refresh();
        return ctx;
    }

}