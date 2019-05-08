package net.ukr.dreamsicle.consts;

public class Constants {
    public static final String ADD = "Add";
    public static final String EDIT = "Edit";
    public static final String REMOVE = "Remove";
    public static final String EMPLOYEES_LIST = "List of employees";

        public static final String REGEX_CHECK_VALID_EMAIL_ADDRESS = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
//    public static final String REGEX_CHECK_VALID_EMAIL_ADDRESS = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\" +\n\"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
    public static final String REGEX_CHECK_VALID_NAME_SURNAME_EMPLOYEE = "[A-Za-zА-Яа-яЁё]{1,50}";
    public static final String REGEX_CHECK_VALID_NAME_SURNAME_DEPARTMENT = "[A-Za-zА-Яа-яЁё0-9-_]{1,50}";
}
