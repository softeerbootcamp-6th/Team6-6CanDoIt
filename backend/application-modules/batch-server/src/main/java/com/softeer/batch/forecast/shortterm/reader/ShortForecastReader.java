package com.softeer.batch.forecast.shortterm.reader;

import com.softeer.domain.Grid;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@StepScope
public class ShortForecastReader extends JdbcPagingItemReader<Grid> {

    private final int PAGE_SIZE = 100;

    public ShortForecastReader(DataSource dataSource) {
        super();

        super.setName("shortForecastIdentifierReader");
        super.setDataSource(dataSource);
        super.setPageSize(PAGE_SIZE);
        super.setFetchSize(PAGE_SIZE);
        super.setRowMapper((rs, rowNum) -> new Grid(
                rs.getInt("gridId"),
                rs.getInt("x"),
                rs.getInt("y")
        ));

        try {
            SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
            factory.setDataSource(dataSource);
            factory.setSelectClause("SELECT g.id AS gridId, g.x, g.y");
            factory.setFromClause(
                    "FROM grid AS g " +
                            "WHERE EXISTS (SELECT 1 FROM course_point AS cp WHERE cp.grid_id = g.id)"
            );
            factory.setSortKey("gridId");

            PagingQueryProvider queryProvider = factory.getObject();

            super.setQueryProvider(queryProvider);
        } catch (Exception e) {
            throw new IllegalStateException("PagingQueryProvider 생성에 실패했습니다.", e);
        }
    }
}
