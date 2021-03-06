/*
 Copyright 2016 Goldman Sachs.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

package com.gs.fw.common.mithra.finder.byteop;

import com.gs.fw.common.mithra.attribute.ByteAttribute;
import com.gs.fw.common.mithra.extractor.ByteExtractor;
import com.gs.fw.common.mithra.extractor.Extractor;
import com.gs.fw.common.mithra.finder.AtomicNotEqualityOperation;
import com.gs.fw.common.mithra.finder.SqlParameterSetter;
import com.gs.fw.common.mithra.finder.SqlQuery;
import com.gs.fw.common.mithra.finder.paramop.OpWithByteParam;
import com.gs.fw.common.mithra.finder.paramop.OpWithByteParamExtractor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ByteNotEqOperation  extends AtomicNotEqualityOperation implements SqlParameterSetter, OpWithByteParam
{
    private byte parameter;

    public ByteNotEqOperation(ByteAttribute attribute, byte parameter)
    {
        super(attribute);
        this.parameter = parameter;
    }

    @Override
    protected Extractor getStaticExtractor()
    {
        return OpWithByteParamExtractor.INSTANCE;
    }

    @Override
    protected boolean matchesWithoutDeleteCheck(Object o, Extractor extractor)
    {
        ByteExtractor byteAttribute = (ByteExtractor) extractor;
        if (byteAttribute.isAttributeNull(o)) return false;
        return byteAttribute.byteValueOf(o) != parameter;
    }

    public int setSqlParameters(PreparedStatement pstmt, int startIndex, SqlQuery query) throws SQLException
    {
        pstmt.setByte(startIndex, parameter);
        return 1;
    }

    public int hashCode()
    {
        return ~(this.getAttribute().hashCode() ^ this.parameter);
    }

    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj instanceof ByteNotEqOperation)
        {
            ByteNotEqOperation other = (ByteNotEqOperation) obj;
            return this.parameter == other.parameter && this.getAttribute().equals(other.getAttribute());
        }
        return false;
    }

    @Override
    public Object getParameterAsObject()
    {
        return Byte.valueOf(this.parameter);
    }

    public byte getParameter()
    {
        return parameter;
    }
}
