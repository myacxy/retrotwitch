package net.myacxy.retrotwitch.v5.api.games;

import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("_id")
    private long id;
    @SerializedName("box")
    private Box box;
    @SerializedName("giantbomb_id")
    private long giantbombId;
    @SerializedName("logo")
    private Logo logo;
    @SerializedName("name")
    private String name;
    @SerializedName("popularity")
    private int popularity;

    public long getId() {
        return id;
    }

    public Box getBox() {
        return box;
    }

    public long getGiantbombId() {
        return giantbombId;
    }

    public Logo getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", box=" + box +
                ", giantbombId=" + giantbombId +
                ", logo=" + logo +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                '}';
    }

    public class Box {

        @SerializedName("large")
        private String large;
        @SerializedName("medium")
        private String medium;
        @SerializedName("small")
        private String small;
        @SerializedName("template")
        private String template;

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }

        public String getSmall() {
            return small;
        }

        public String getTemplate() {
            return template;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "large='" + large + '\'' +
                    ", medium='" + medium + '\'' +
                    ", small='" + small + '\'' +
                    ", template='" + template + '\'' +
                    '}';
        }
    }

    public class Logo {

        @SerializedName("large")
        private String large;
        @SerializedName("medium")
        private String medium;
        @SerializedName("small")
        private String small;
        @SerializedName("template")
        private String template;

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }

        public String getSmall() {
            return small;
        }

        public String getTemplate() {
            return template;
        }

        @Override
        public String toString() {
            return "Logo{" +
                    "large='" + large + '\'' +
                    ", medium='" + medium + '\'' +
                    ", small='" + small + '\'' +
                    ", template='" + template + '\'' +
                    '}';
        }
    }
}
