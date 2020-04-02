package me.frostythedev.frostengine.data.core;

import me.frostythedev.frostengine.util.Builder;

public class SQLSyntax {

    public static final String AUTO_INCREMENT = "int NOT NULL PRIMARY KEY AUTO_INCREMENT";
    private String statement;

    public SQLSyntax() {
        this.statement = "";
    }

    public SQLSyntax autoIncrement(String colum) {
        with(colum + " " + AUTO_INCREMENT);
        return this;
    }

    public SQLSyntax with(String more) {
        this.statement = (this.statement + more + " ");
        return this;
    }

    public SQLSyntax delete(String table) {
        this.statement = (this.statement + "DROP TABLE " + table + " ");
        return this;
    }

    public SQLSyntax and(String field) {
        this.statement = (this.statement + "AND " + field + " ");
        return this;
    }

    public SQLSyntax insert(String where, String fields) {
        this.statement = (this.statement + "INSERT INTO " + where + " (" + fields + ") ");
        return this;
    }

    public SQLSyntax values(Object[] parms) {
        String parm = "";
        for (Object obj : parms) {
            if ((obj instanceof String)) {
                parm = parm + "" + String.valueOf(obj) + "";
            } else {
                parm = parm + obj;
            }
            if (!parms[(parms.length - 1)].equals(obj)) {
                parm = parm + ", ";
            }
        }
        this.statement = (this.statement + "VALUES (" + parm + ") ");
        return this;
    }

    public SQLSyntax orderBy(String field) {
        this.statement = (this.statement + "ORDER BY " + field + " ");
        return this;
    }

    public SQLSyntax create(String table, String fields, boolean overwrite) {
        if (!overwrite) {
            this.statement = (this.statement + "CREATE TABLE IF NOT EXISTS `" + table + "` (" + fields + ") ");
        }
        return this;
    }

    public SQLSyntax update(String identif) {
        this.statement = (this.statement + "UPDATE " + identif + " ");
        return this;
    }

    public SQLSyntax set(String field) {
        this.statement = (this.statement + "SET " + field + " ");
        return this;
    }

    public SQLSyntax where(String field) {
        this.statement = (this.statement + "WHERE `" + field + "` ");
        return this;
    }

    public SQLSyntax select(String field) {
        this.statement = (this.statement + "SELECT " + field + " ");
        return this;
    }

    public SQLSyntax selectAll() {
        return select("*");
    }

    public SQLSyntax from(String where) {
        this.statement = (this.statement + "FROM " + where + " ");
        return this;
    }

    public SQLSyntax equalsTo(Object equals) {
        if ((equals instanceof String)) {
            this.statement = (this.statement + "= \"" + String.valueOf(equals) + "\" ");
        } else {
            this.statement = (this.statement + "= " + equals + " ");
        }
        return this;
    }

    public static SyntaxBuilder builder() {
        return new SyntaxBuilder();
    }

    public static class SyntaxBuilder implements Builder<String> {

        private SQLSyntax syntax;

        public SyntaxBuilder() {
            this.syntax = new SQLSyntax();
        }

        public SyntaxBuilder autoIncrement(String column) {
            this.syntax.autoIncrement(column);
            return this;
        }

        public SyntaxBuilder with(String more) {
            this.syntax.with(more);
            return this;
        }

        public SyntaxBuilder delete(String table) {
            this.syntax.delete(table);
            return this;
        }

        public SyntaxBuilder and(String field) {
            this.syntax.and(field);
            return this;
        }

        public SyntaxBuilder insert(String where, String fields) {
            this.syntax.insert(where, fields);
            return this;
        }

        public SyntaxBuilder values(Object[] parms) {
            this.syntax.values(parms);
            return this;
        }

        public SyntaxBuilder orderBy(String field) {
            this.syntax.orderBy(field);
            return this;
        }

        public SyntaxBuilder create(String table, String fields, boolean overwrite) {
            this.syntax.create(table, fields, overwrite);
            return this;
        }

        public SyntaxBuilder update(String identif) {
            this.syntax.update(identif);
            return this;
        }

        public SyntaxBuilder set(String field) {
            this.syntax.set(field);
            return this;
        }

        public SyntaxBuilder where(String field) {
            this.syntax.where(field);
            return this;
        }

        public SyntaxBuilder select(String field) {
            this.syntax.select(field);
            return this;
        }

        public SyntaxBuilder selectAll() {
            return select("*");
        }

        public SyntaxBuilder from(String where) {
            this.syntax.from(where);
            return this;
        }

        public SyntaxBuilder equalsTo(Object equals) {
            this.syntax.equalsTo(equals);
            return this;
        }
        
        public String build() {
            return this.syntax.statement + ";";
        }
    }
}
