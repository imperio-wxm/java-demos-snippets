package com.wxmimperio.sqlparser.visitor;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.List;

public class SelectSqlVisitor implements SelectVisitor, FromItemVisitor, ExpressionVisitor {

    private Long offset; // 起始列
    private Long rowCount;// limit 行数
    private List<SelectItem> selectItems; // select from 之间要查出来的字段


    public SelectSqlVisitor(Select select) {
        this.offset = 0L;
        this.rowCount = 0L;
        this.selectItems = new ArrayList<SelectItem>();
        SelectBody selectBody = select.getSelectBody();
        selectBody.accept(this);
    }

    public void visit(PlainSelect plainSelect) {
        if (plainSelect == null) {
            return;
        }
        // 解析出要select 的字段
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        this.selectItems = selectItems;

        // 解析出limit
        Limit limit = plainSelect.getLimit();
        if (limit != null) {
            if (limit.getOffset() != null) {
                offset = Long.parseLong(limit.getOffset().toString());
            }
            if (limit.getRowCount() != null) {
                rowCount = Long.parseLong(limit.getRowCount().toString());
            }
        }
        plainSelect.getFromItem().accept(this);
        if (plainSelect.getWhere() != null) {
            plainSelect.getWhere().accept(this);
        }
    }


    public void visit(Table tableName) {

    }

    public void visit(SubJoin subjoin) {

    }

    public void visit(LateralSubSelect lateralSubSelect) {

    }

    public void visit(ValuesList valuesList) {

    }

    public void visit(TableFunction tableFunction) {

    }

    public void visit(SetOperationList setOpList) {

    }

    public void visit(WithItem withItem) {

    }

    public void visit(NullValue nullValue) {

    }

    public void visit(Function function) {

    }

    public void visit(SignedExpression signedExpression) {

    }

    public void visit(JdbcParameter jdbcParameter) {

    }

    public void visit(JdbcNamedParameter jdbcNamedParameter) {

    }

    public void visit(DoubleValue doubleValue) {

    }

    public void visit(LongValue longValue) {

    }

    public void visit(HexValue hexValue) {

    }

    public void visit(DateValue dateValue) {

    }

    public void visit(TimeValue timeValue) {

    }

    public void visit(TimestampValue timestampValue) {

    }

    public void visit(Parenthesis parenthesis) {

    }

    public void visit(StringValue stringValue) {

    }

    public void visit(Addition addition) {

    }

    public void visit(Division division) {

    }

    public void visit(Multiplication multiplication) {

    }

    public void visit(Subtraction subtraction) {

    }

    public void visit(AndExpression andExpression) {
        System.out.println(andExpression.getLeftExpression().toString());
        System.out.println(andExpression.getStringExpression());
        System.out.println(andExpression.getRightExpression().toString());
        andExpression.getLeftExpression().accept(this);
        andExpression.getRightExpression().accept(this);
    }

    public void visit(OrExpression orExpression) {

    }

    public void visit(Between between) {

    }

    public void visit(EqualsTo equalsTo) {
        System.out.println(equalsTo.getLeftExpression().toString());
        System.out.println(equalsTo.getRightExpression().toString());
    }

    public void visit(GreaterThan greaterThan) {

    }

    public void visit(GreaterThanEquals greaterThanEquals) {

    }

    public void visit(InExpression inExpression) {

    }

    public void visit(IsNullExpression isNullExpression) {

    }

    public void visit(LikeExpression likeExpression) {

    }

    public void visit(MinorThan minorThan) {

    }

    public void visit(MinorThanEquals minorThanEquals) {

    }

    public void visit(NotEqualsTo notEqualsTo) {

    }

    public void visit(Column tableColumn) {

    }

    public void visit(SubSelect subSelect) {

    }

    public void visit(CaseExpression caseExpression) {

    }

    public void visit(WhenClause whenClause) {

    }

    public void visit(ExistsExpression existsExpression) {

    }

    public void visit(AllComparisonExpression allComparisonExpression) {

    }

    public void visit(AnyComparisonExpression anyComparisonExpression) {

    }

    public void visit(Concat concat) {

    }

    public void visit(Matches matches) {

    }

    public void visit(BitwiseAnd bitwiseAnd) {

    }

    public void visit(BitwiseOr bitwiseOr) {

    }

    public void visit(BitwiseXor bitwiseXor) {

    }

    public void visit(CastExpression cast) {

    }

    public void visit(Modulo modulo) {

    }

    public void visit(AnalyticExpression aexpr) {

    }

    public void visit(WithinGroupExpression wgexpr) {

    }

    public void visit(ExtractExpression eexpr) {

    }

    public void visit(IntervalExpression iexpr) {

    }

    public void visit(OracleHierarchicalExpression oexpr) {

    }

    public void visit(RegExpMatchOperator rexpr) {

    }

    public void visit(JsonExpression jsonExpr) {

    }

    public void visit(JsonOperator jsonExpr) {

    }

    public void visit(RegExpMySQLOperator regExpMySQLOperator) {

    }

    public void visit(UserVariable var) {

    }

    public void visit(NumericBind bind) {

    }

    public void visit(KeepExpression aexpr) {

    }

    public void visit(MySQLGroupConcat groupConcat) {

    }

    public void visit(RowConstructor rowConstructor) {

    }

    public void visit(OracleHint hint) {

    }

    public void visit(TimeKeyExpression timeKeyExpression) {

    }

    public void visit(DateTimeLiteralExpression literal) {

    }

    public void visit(NotExpression aThis) {

    }
}
