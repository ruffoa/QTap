package engsoc.qlife.database.local.courses.Course;

import engsoc.qlife.database.local.DatabaseRow;

/**
 * Created by Carson on 19/01/2017.
 * Defines the schema for the Courses table. Currently holds a field for the class title,
 * room number, class time and ID.
 * **Note** Each lecture/lab/studio needs an entry, so 'Course' is a misnomer.
 */
public class Course extends DatabaseRow {
    //table schema
    public static final String TABLE_NAME = "courses";
    public static final String COLUMN_TITLE = "title";

    //column number each field ends up in
    public static final int TITLE_POS = 1;
    public static final int ROOM_NUM_POS = 2;
    public static final int STIME_POS = 3;
    public static final int ETIME_POS = 4;
    public static final int DAY_POS = 5;
    public static final int MONTH_POS = 6;
    public static final int YEAR_POS = 7;

    //fields in database
    private String title;

    public Course(int id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
