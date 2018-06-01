package com.nn.kovaleva.irina.schooltutor.Model;

public class Themes{
    public enum Theme {
        MATHS("Mathematics"),
        INF("Informatics"),
        PROG("Programming"),
        HISTORY("History"),
        GEOGRAPHY("Geography"),
        BIO("Biology"),
        PHYSICS("Physics"),
        ECONOMY("Economy"),
        CHTMISTRY("Chemistry"),
        SOCSTUD("Social studies"),
        NATLAN("Native language"),
        FORLANG("Foreign language");

        private String description;

        Theme(String description) {
            this.description = description;
        }

        public String getDescription() {return description;}
    }
}
