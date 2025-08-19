package com.softeer.batch.forecast.shortterm.reader;

import com.softeer.domain.Grid;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@StepScope
public class ShortForecastReader extends JdbcPagingItemReader<Grid> {

    private final int PAGE_SIZE = 20;

    public ShortForecastReader(DataSource dataSource) {
        super();

        super.setName("shortForecastIdentifierReader");
        super.setDataSource(dataSource);
        super.setPageSize(PAGE_SIZE);
        super.setFetchSize(PAGE_SIZE);
        super.setRowMapper((rs, rowNum) -> new Grid(
                rs.getInt("id"),
                rs.getInt("x"),
                rs.getInt("y")
        ));

        try {
            SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
            factory.setDataSource(dataSource);
            factory.setSelectClause("distinct g.id, g.x, g.y ");

            factory.setFromClause("from grid g inner join course_point cp on g.id = cp.grid_id");
            factory.setSortKeys(Map.of("g.id", Order.ASCENDING));

            PagingQueryProvider queryProvider = factory.getObject();

            super.setQueryProvider(queryProvider);
        } catch (Exception e) {
            throw new IllegalStateException("PagingQueryProvider 생성에 실패했습니다.", e);
        }
    }
}
