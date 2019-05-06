package habit.mvvm.koltlin1;

/**
 * @创建人 pst
 * @创建时间 $date$
 * @描述:
 * @修改:
 */
public class Student {
    private static final Student ourInstance = new Student();

    public static Student getInstance() {
        return ourInstance;
    }

    private Student() {
    }
}
