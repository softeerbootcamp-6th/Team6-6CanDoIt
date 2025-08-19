package com.softeer.batch.forecast.mountain.reader;

import com.softeer.batch.forecast.mountain.dto.MountainIdentifier;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
@StepScope
public class MountainIdentifierReader extends JdbcPagingItemReader<MountainIdentifier> {

    private static final int PAGE_SIZE = 20;

    public MountainIdentifierReader(DataSource dataSource) {
        super();

        super.setName("mountainIdentifierReader");
        super.setDataSource(dataSource);
        super.setPageSize(PAGE_SIZE);
        super.setFetchSize(PAGE_SIZE);
        super.setRowMapper((rs, rowNum) -> new MountainIdentifier(
                rs.getLong("id"),
                rs.getInt("code"),
                rs.getInt("gridId")
        ));

        try {
            SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
            factory.setDataSource(dataSource);
            factory.setSelectClause(
                    "SELECT " +
                            "COALESCE(id, 0)          AS id, " +
                            "COALESCE(code, 0)        AS code, " +
                            "COALESCE(grid_id, 0)     AS gridId"
            );
            factory.setFromClause("FROM mountain");
            factory.setSortKeys(Map.of("id", Order.ASCENDING));

            PagingQueryProvider queryProvider = factory.getObject();

            super.setQueryProvider(queryProvider);
        } catch (Exception e) {
            throw new IllegalStateException("PagingQueryProvider 생성에 실패했습니다.", e);
        }
    }
}
