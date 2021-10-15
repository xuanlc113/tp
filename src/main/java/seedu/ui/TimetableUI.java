package seedu.ui;

import seedu.module.Module;
import seedu.timetable.TimetableItem;
import seedu.timetable.TimetableLesson;

import java.util.List;

public class TimetableUI {

    public enum LineType {
        CODE, LESSONTYPE, VENUE,
    }

    private static final String FIXED_LENGTH_FORMAT = "%-16.16s";
    private static final String DIVIDER = "----------------";
    private static final String MODULES_HEADER = "Modules taken this semester: \n";
    private static final String CREDIT_COUNT_HEADER = "\nTotal MCs taken this semester: ";
    private static final String STAR_DIVIDER = "\n*******************";

    /**
     * Prints the list of modules taken in the timetable, and the total number of
     * MCs.
     * 
     * @param modules the list of modules taken in the timetable
     */
    public static void printModules(List<Module> modules) {
        double counter = 0;
        System.out.println(STAR_DIVIDER);
        System.out.println(MODULES_HEADER);
        for (Module module : modules) {
            System.out.println(module);
            counter += module.getModuleCredit();
        }

        System.out.println(CREDIT_COUNT_HEADER + counter);
        System.out.println(STAR_DIVIDER);
    }

    /**
     * Displays the first row in the timetable grid starting from the earliest hour
     * in the timetable to the last.
     * 
     * @param start the starting hour of the timetable
     * @param end   the last hour of the timetable
     */
    public static void printScheduleHours(int start, int end) {
        String infoLine = "\t\t\t\t";
        for (int u = start; u <= end; u++) {
            infoLine += String.format(FIXED_LENGTH_FORMAT, u + "00");
        }
        System.out.println(infoLine);
    }

    /**
     * Displays the full-day schedule for a specific day. Prints three lines :
     * Module Code, Lesson Type, and Venue.
     * 
     * @param day      The day to be printed (Monday/Tuesday/Wednesday... etc.)
     * @param schedule The schedule to be printed for that day
     * @param start    the earliest hour that has any activity
     * @param end      the last hour that has any activity
     */
    public static void printDaySchedule(String day, TimetableItem[] schedule, int start, int end) {
        for (int u = start; u <= end; u++) {
            System.out.print(DIVIDER);
        }
        System.out.println();
        printLine(day, schedule, start, end, LineType.CODE);
        printLine(day, schedule, start, end, LineType.LESSONTYPE);
        printLine(day, schedule, start, end, LineType.VENUE);

    }

    private static void printLine(String day, TimetableItem[] schedule, int start, int end, LineType type) {
        String infoLine = addHeader(day, type);
        TimetableLesson prevTimetableLesson = null;
        for (int i = start; i <= end; i++) {
            if (schedule[i] instanceof TimetableLesson) {
                TimetableLesson modTimetableLesson = (TimetableLesson) schedule[i];
                infoLine += addInfoToString(modTimetableLesson, prevTimetableLesson, type);
                prevTimetableLesson = modTimetableLesson;
            } else if (schedule[i] == null) {
                infoLine += addInfoToString(null, prevTimetableLesson, type);
                prevTimetableLesson = null;
            }
        }
        System.out.println(infoLine);
    }

    private static String addHeader(String day, LineType type) {
        if (type.equals(LineType.LESSONTYPE)) {
            return "\t\t" + day + "\t\t";
        } else {
            return "\t\t\t\t";
        }
    }

    private static String addInfoToString(TimetableLesson lesson, TimetableLesson prevLesson, LineType type) {
        String str = "";
        if ((lesson == null && prevLesson != null) || (lesson != null && !lesson.equals(prevLesson))) {
            str = "|   ";
            switch (type) {
            case CODE:
                str += addModuleCode(lesson);
                break;
            case LESSONTYPE:
                str += addLessonType(lesson);
                break;
            case VENUE:
                str += addVenue(lesson);
                break;
            default:
                str += "";
            }
        }
        return String.format(FIXED_LENGTH_FORMAT, str);
    }

    private static String addModuleCode(TimetableLesson timetableLesson) {
        String str = "";
        if (timetableLesson != null) {
            str = timetableLesson.getTitle();
        }
        return str;
    }

    private static String addLessonType(TimetableLesson timetableLesson) {
        String str = "";
        if (timetableLesson != null) {
            str += timetableLesson.getLessonType().toString();
            str += "[" + timetableLesson.getClassNo() + "]";
        }
        return str;
    }

    private static String addVenue(TimetableLesson timetableLesson) {
        String str = "";
        if (timetableLesson != null) {
            str = timetableLesson.getVenue();
        }
        return str;
    }

    /*------------- Timetable Storage ----------- */
    // TODO ADD UI TO SAVE AND LOAD TIMETABLE

}
