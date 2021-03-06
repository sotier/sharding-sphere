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

package io.shardingsphere.core.parsing.antlr.extractor.statement;

import io.shardingsphere.core.metadata.table.ShardingTableMetaData;
import io.shardingsphere.core.parsing.antlr.extractor.SQLStatementExtractor;
import io.shardingsphere.core.parsing.antlr.extractor.phrase.PhraseExtractor;
import io.shardingsphere.core.parsing.parser.sql.SQLStatement;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract statement extractor, get information by each phrase extractor.
 * 
 * @author duhongjun
 */
public abstract class AbstractStatementExtractor implements SQLStatementExtractor {
    
    private List<PhraseExtractor> extractors = new LinkedList<>();
    
    @Override
    public final SQLStatement extract(final ParserRuleContext rootNode, final ShardingTableMetaData shardingTableMetaData) {
        SQLStatement result = newStatement(shardingTableMetaData);
        for (PhraseExtractor each : extractors) {
            each.visit(rootNode, result);
        }
        postVisit(result);
        return result;
    }
    
    protected void postVisit(final SQLStatement statement) {
    }
    
    /**
     * Add phrase extractor.
     * 
     * @param phraseExtractor phrase extractor for filling statement
     */
    public final void addPhraseExtractor(final PhraseExtractor phraseExtractor) {
        extractors.add(phraseExtractor);
    }
    
    protected abstract SQLStatement newStatement();
    
    protected SQLStatement newStatement(final ShardingTableMetaData shardingTableMetaData) {
        return newStatement();
    }
}
