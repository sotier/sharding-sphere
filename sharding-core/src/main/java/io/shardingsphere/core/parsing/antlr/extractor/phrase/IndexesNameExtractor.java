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

package io.shardingsphere.core.parsing.antlr.extractor.phrase;

import io.shardingsphere.core.parsing.antlr.extractor.util.ASTUtils;
import io.shardingsphere.core.parsing.antlr.extractor.util.ExtractorUtils;
import io.shardingsphere.core.parsing.parser.sql.SQLStatement;
import io.shardingsphere.core.parsing.parser.sql.ddl.DDLStatement;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Extract multiple index name phrase.
 * 
 * @author duhongjun
 */
public final class IndexesNameExtractor implements PhraseExtractor {
    
    @Override
    public void visit(final ParserRuleContext ancestorNode, final SQLStatement statement) {
        DDLStatement ddlStatement = (DDLStatement) statement;
        String tableName = ddlStatement.getTables().isEmpty() ? null : ddlStatement.getTables().getSingleTableName();
        for (ParserRuleContext each : ASTUtils.getAllDescendantNodes(ancestorNode, RuleName.INDEX_NAME)) {
            statement.getSQLTokens().add(ExtractorUtils.extractIndex(each, tableName));
        }
    }
}
