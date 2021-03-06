/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.parsing.antlr.extractor;

import io.shardingsphere.core.constant.DatabaseType;
import io.shardingsphere.core.parsing.antlr.extractor.registry.DatabaseExtractorRegistry;
import io.shardingsphere.core.parsing.antlr.extractor.registry.dialect.MySQLExtractorRegistry;
import io.shardingsphere.core.parsing.antlr.extractor.registry.dialect.OracleExtractorRegistry;
import io.shardingsphere.core.parsing.antlr.extractor.registry.dialect.PostgreSQLExtractorRegistry;
import io.shardingsphere.core.parsing.antlr.extractor.registry.dialect.SQLServerExtractorRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * SQL statement extractor factory.
 * 
 * @author duhongjun
 * @author zhangliang
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SQLStatementExtractorFactory {
    
    private static final Map<DatabaseType, DatabaseExtractorRegistry> EXTRACTOR_REGISTRY = new HashMap<>(5, 1);
    
    static {
        EXTRACTOR_REGISTRY.put(DatabaseType.H2, new MySQLExtractorRegistry());
        EXTRACTOR_REGISTRY.put(DatabaseType.MySQL, new MySQLExtractorRegistry());
        EXTRACTOR_REGISTRY.put(DatabaseType.PostgreSQL, new PostgreSQLExtractorRegistry());
        EXTRACTOR_REGISTRY.put(DatabaseType.SQLServer, new SQLServerExtractorRegistry());
        EXTRACTOR_REGISTRY.put(DatabaseType.Oracle, new OracleExtractorRegistry());
    }
    
    /**
     * Get SQL statement extractor.
     * 
     * @param databaseType database type
     * @param sqlStatementType SQL statement type
     * @return statement extractor
     */
    public static SQLStatementExtractor getInstance(final DatabaseType databaseType, final SQLStatementType sqlStatementType) {
        return EXTRACTOR_REGISTRY.get(databaseType).getSQLStatementExtractor(sqlStatementType);
    }
}
