package com.pratham.prathamdigital.dbclasses;

import android.content.Context;
import android.database.Cursor;

import com.pratham.prathamdigital.models.GoogleCredentials;

public class GoogleDBHelper extends DBHelper {

    Cursor cursor;
    final String TABLENAME = "GoogleData";
    Context c;
    int status;

    public GoogleDBHelper(Context context) {
        super(context);
        c = context;
        database = getWritableDatabase();

    }

    // Login Status of User
    public boolean CheckLogin(String uname) {

        database = getWritableDatabase();

        cursor = database.rawQuery("SELECT * FROM GoogleData WHERE GoogleID = ? ", new String[]{uname});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                cursor.close();
                database.close();
                return true;
            }
        }
        return false;
    }


    // Insert New User
    public void insertNewUser(GoogleCredentials obj) {

        database = getWritableDatabase();

        contentValues.put("GoogleID", obj.GoogleID);
        contentValues.put("PersonPhotoUrl", obj.PersonPhotoUrl);
        contentValues.put("Email", obj.Email);
        contentValues.put("PersonName", obj.PersonName);
        contentValues.put("IntroShown", obj.IntroShown);

        database.insert("GoogleData", null, contentValues);
        database.close();
    }

    // Get Intro info for User
    public boolean CheckIntroShownStatus(String userID) {

        database = getWritableDatabase();

        cursor = database.rawQuery("SELECT IntroShown FROM GoogleData WHERE GoogleID = ? ", new String[]{String.valueOf(userID)});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    status = cursor.getInt((cursor.getColumnIndex("IntroShown")));
                    cursor.moveToNext();
                }
                if (status == 1) {
                    cursor.close();
                    database.close();
                    return true;
                } else if (status == 0) {
                    cursor.close();
                    database.close();
                    return false;
                }


            }
        }
        return false;
    }

    // Update Intro to true after showing Intro
    public void SetFlagTrue(int i, String userID) {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("update GoogleData set IntroShown = " + i + " where GoogleID = '" + userID + "'", null);
            cursor.moveToFirst();
            cursor.close();
            database.close();
        } catch (Exception e) {
        }
    }

    /*public int GetStudentCount(String GroupID) {
        database = getReadableDatabase();

        String count = "SELECT count(StudentID) FROM Student where GroupID = ? ";
        Cursor mcursor = database.rawQuery(count, new String[]{GroupID});
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return icount;
        } else {
            return icount;
        }
    }

    // Get Students Based on ChildID & StudentUniqueID 
    public List<Student> GetAllStudentsByChildIDnStudentUniqID(String ChildID, String StudentUID) {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from Student where StudentUID = ? AND StudentID = ?", new String[]{ChildID, StudentUID});
            return _PopulateListFromCursor(cursor);
        } catch (Exception ex) {
//            _PopulateLogValues(ex, "GetAll-Student");
            return null;
        }
    }


    // set Flag to false
    public void SetFlagFalse(String studentID) {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("update Student set NewFlag=false where StudentID = ? ", new String[]{studentID});
            cursor.moveToFirst();
            database.close();
        } catch (Exception e) {
        }
    }


    public Student GetStudentDataByStdID(String studentID) {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from Student where StudentID = ? ", new String[]{studentID});
            return _PopulateObjectFromCursor(cursor);
        } catch (Exception ex) {
            return null;
        }
    }


    public List<StudentList> GetAllStudentsByGroupID(String GroupID) {
        try {
            database = getWritableDatabase();
            List<StudentList> list = new ArrayList<StudentList>();
            {
                Cursor cursor = database.rawQuery("SELECT StudentID,FirstName FROM Student WHERE GroupID = ? ", new String[]{GroupID});
                list.add(new StudentList("0", "--Select Student--"));
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {

                    list.add(new StudentList(cursor.getString(cursor.getColumnIndex("StudentID")), cursor.getString(cursor.getColumnIndex("FirstName"))));

                    cursor.moveToNext();
                }
                database.close();

            }
            return list;

        } catch (Exception ex) {
            return null;
        }
    }

    *//*//*/ /*Get Students Based on ChildID & GroupID (UnitID)
    public List<Student> GetAllStudentsByGroupID(String GroupID) {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from Student where GroupID = ?", new String[]{GroupID});
            return _PopulateListFromCursor(cursor);
        } catch (Exception ex) {
//            _PopulateLogValues(ex, "GetAll-Student");
            return null;
        }
    }*//*


    // Get Students Based on ChildID & GroupID (UnitID)
    public List<Student> GetAllStudentsByChildID(String ChildID, String UnitID) {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from Student where StudentUID = ? AND GroupID = ?", new String[]{ChildID, UnitID});
            return _PopulateListFromCursor(cursor);
        } catch (Exception ex) {
//            _PopulateLogValues(ex, "GetAll-Student");
            return null;
        }
    }


    public void insertUniversalChildData(Student obj) {

        database = getWritableDatabase();

        contentValues.put("StudentID", obj.StudentID);
        contentValues.put("FirstName", obj.FirstName);
        contentValues.put("MiddleName", obj.MiddleName);
        contentValues.put("LastName", obj.LastName);
        contentValues.put("Age", obj.Age);
        contentValues.put("Class", obj.Class);
        contentValues.put("UpdatedDate", obj.UpdatedDate);
        contentValues.put("Gender", obj.Gender);
        contentValues.put("GroupID", obj.GroupID);
        contentValues.put("CreatedBy", obj.CreatedBy);
        contentValues.put("NewFlag", obj.newStudent);
        contentValues.put("StudentUID", obj.StudentUID);
        contentValues.put("IsSelected", obj.IsSelected);

        database.replace("Student", null, contentValues);
        database.close();
    }

    public void replaceData(Student obj) {

        database = getWritableDatabase();

        contentValues.put("StudentID", obj.StudentID);
        contentValues.put("FirstName", obj.FirstName);
        contentValues.put("MiddleName", obj.MiddleName);
        contentValues.put("LastName", obj.LastName);
        contentValues.put("Age", obj.Age);
        contentValues.put("Class", obj.Class);
        contentValues.put("UpdatedDate", obj.UpdatedDate);
        contentValues.put("Gender", obj.Gender);
        contentValues.put("GroupID", obj.GroupID);
        contentValues.put("CreatedBy", obj.CreatedBy);
        contentValues.put("StudentUID", obj.StudentUID);
        contentValues.put("NewFlag", obj.newStudent);


        database.replace("Student", null, contentValues);

        database.close();
    }

    public void insertData(Student obj) {

        database = getWritableDatabase();

        contentValues.put("StudentID", obj.StudentID);
        contentValues.put("FirstName", obj.FirstName);
        contentValues.put("MiddleName", obj.MiddleName);
        contentValues.put("LastName", obj.LastName);
        contentValues.put("Age", obj.Age);
        contentValues.put("Class", obj.Class);
        contentValues.put("UpdatedDate", obj.UpdatedDate);
        contentValues.put("Gender", obj.Gender);
        contentValues.put("GroupID", obj.GroupID);
        contentValues.put("CreatedBy", obj.CreatedBy);
        contentValues.put("NewFlag", obj.newStudent);
        contentValues.put("StudentUID", obj.StudentUID);
        contentValues.put("IsSelected", obj.IsSelected);

        database.insert("Student", null, contentValues);
        database.close();
    }


    // Pravin
    public JSONArray getStudentsList(String groupId) {
        JSONArray arr = new JSONArray();
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("SELECT StudentID,FirstName,LastName,Gender FROM " + TABLENAME + " WHERE GroupID = ? ORDER BY LastName ASC", new String[]{String.valueOf(groupId)});
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                JSONObject obj = new JSONObject();
                obj.put("studentId", cursor.getString(cursor.getColumnIndex("StudentID")));
                String fname = cursor.getString(cursor.getColumnIndex("FirstName")) == null ? "" : cursor.getString(cursor.getColumnIndex("FirstName"));
                String lname = cursor.getString(cursor.getColumnIndex("LastName")) == null ? "" : cursor.getString(cursor.getColumnIndex("LastName"));
                String fullName = fname +" "+ lname;
                obj.put("studentName", fullName);
                obj.put("groupId", groupId);
                obj.put("gender", cursor.getString(cursor.getColumnIndex("Gender")));
                arr.put(obj);
                cursor.moveToNext();
            }
            database.close();
        } catch (Exception ex) {
            ex.getStackTrace();
            _PopulateLogValues(ex, "getStudetntsList-Student");
            return null;
        }
        return arr;
    }


    public boolean Add(Student student, SQLiteDatabase database1) {
        try {
            _PopulateContentValues(student);
            long resultCount = database1.insert(TABLENAME, null, contentValues);
            database1.close();
            return true;
            //return resultCount;
        } catch (Exception ex) {
            _PopulateLogValues(ex, "Add-Student");
            //return 0;
            return false;
        }
    }

    public boolean Update(Student student) {
        try {
            database = getWritableDatabase();
            _PopulateContentValues(student);
            long resultCount = database.update(TABLENAME, contentValues, "StudentID = ?", new String[]{(student.StudentID)});
            database.close();
            return true;
        } catch (Exception ex) {
            _PopulateLogValues(ex, "Update-Student");
            return false;
        }
    }

    public boolean Delete(String studentID) {
        try {
            database = getWritableDatabase();
            long resultCount = database.delete(TABLENAME, "StudentID = ?", new String[]{studentID});
            database.close();
            return true;
        } catch (Exception ex) {
            _PopulateLogValues(ex, "Delete-Student");

            return false;
        }
    }

    public boolean DeleteAll() {
        try {
            database = getWritableDatabase();
            long resultCount = database.delete(TABLENAME, null, null);
            database.close();
            return true;
        } catch (Exception ex) {
            _PopulateLogValues(ex, "DeleteAll-Student");
            return false;
        }
    }

    public Student Get(String studentID) {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from " + TABLENAME + " where StudentID='" + studentID + "'", null);
            return _PopulateObjectFromCursor(cursor);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Student> GetAll() {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from " + TABLENAME + "", null);
            return _PopulateListFromCursor(cursor);
        } catch (Exception ex) {
            _PopulateLogValues(ex, "GetAll-Student");
            return null;
        }
    }


    public List<Student> GetAllNewStudents() {
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from Student where NewFlag = 1", null);
            return _PopulateListFromCursor(cursor);
        } catch (Exception ex) {
            //_PopulateLogValues(ex, "GetAll-Student");
            return null;
        }
    }

    private void _PopulateContentValues(Student student) {

        contentValues.put("StudentID", student.StudentID);
        contentValues.put("FirstName", student.FirstName);
        contentValues.put("LastName", student.LastName);
        contentValues.put("Gender", student.Gender);
        contentValues.put("GroupID", student.GroupID);
        contentValues.put("MiddleName", student.MiddleName);
        contentValues.put("Age", student.Age);
        contentValues.put("Class", student.Class);
        contentValues.put("UpdatedDate", student.UpdatedDate);


    }

    private Student _PopulateObjectFromCursor(Cursor cursor) {
        try {
            database = getWritableDatabase();
            Student student = new Student();
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                student.StudentID = cursor.getString(cursor.getColumnIndex("StudentID"));
                student.FirstName = cursor.getString(cursor.getColumnIndex("FirstName"));
                student.LastName = cursor.getString((cursor.getColumnIndex("LastName")));
                student.Gender = cursor.getString((cursor.getColumnIndex("Gender")));
                student.GroupID = cursor.getString(cursor.getColumnIndex("GroupID"));
                student.MiddleName = cursor.getString(cursor.getColumnIndex("MiddleName"));
                student.Age = cursor.getInt(cursor.getColumnIndex("Age"));
                student.Class = cursor.getInt(cursor.getColumnIndex("Class"));
                student.UpdatedDate = cursor.getString(cursor.getColumnIndex("UpdatedDate"));
                student.newStudent = Boolean.valueOf(cursor.getString(cursor.getColumnIndex("NewFlag")));
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
            return student;
        } catch (Exception ex) {
            _PopulateLogValues(ex, "PopulateObjectFromCursor-Student");

            return null;
        }
    }

    private List<Student> _PopulateListFromCursor(Cursor cursor) {
        try {
            database = getWritableDatabase();
            List<Student> students = new ArrayList<Student>();
            Student student;
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {

                student = new Student();

                student.StudentID = cursor.getString(cursor.getColumnIndex("StudentID"));
                student.FirstName = cursor.getString(cursor.getColumnIndex("FirstName"));
                student.LastName = cursor.getString(cursor.getColumnIndex("LastName"));
                student.Gender = cursor.getString(cursor.getColumnIndex("Gender"));
                student.GroupID = cursor.getString(cursor.getColumnIndex("GroupID"));
                student.MiddleName = cursor.getString(cursor.getColumnIndex("MiddleName"));
                student.Age = cursor.getInt(cursor.getColumnIndex("Age"));
                student.Class = cursor.getInt(cursor.getColumnIndex("Class"));
                student.UpdatedDate = cursor.getString(cursor.getColumnIndex("UpdatedDate"));
                student.CreatedBy = cursor.getString(cursor.getColumnIndex("CreatedBy"));
                student.newStudent = Boolean.valueOf(cursor.getString(cursor.getColumnIndex("NewFlag")));
                student.StudentUID = cursor.getString(cursor.getColumnIndex("StudentUID"));
                student.IsSelected = Boolean.valueOf(cursor.getString(cursor.getColumnIndex("IsSelected")));

                students.add(student);
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
            return students;
        } catch (Exception ex) {
            _PopulateLogValues(ex, "PopulateListFromCursor-Student");

            return null;
        }
    }

    public JSONArray getStudetntsList(String groupId) {
        List<StudentList> list = new ArrayList<StudentList>();
        JSONArray arr = new JSONArray();
        try {
            database = getWritableDatabase();
            Cursor cursor = database.rawQuery("SELECT StudentID,FirstName || \" \" ||LastName as LastName FROM " + TABLENAME + " WHERE GroupID = ? ORDER BY LastName ASC", new String[]{String.valueOf(groupId)});

            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                JSONObject obj = new JSONObject();
                obj.put("studentId", cursor.getString(cursor.getColumnIndex("StudentID")));
                obj.put("studentName", cursor.getString(cursor.getColumnIndex("LastName")));
                arr.put(obj);
                cursor.moveToNext();
            }
            database.close();
        } catch (Exception ex) {
            ex.getStackTrace();
            _PopulateLogValues(ex, "getStudetntsList-Student");
            return null;
        }
        return arr;
    }

    private void _PopulateLogValues(Exception ex, String method) {
        database = getWritableDatabase();
        Logs logs = new Logs();
        logs.currentDateTime = Util.GetCurrentDateTime();
        logs.errorType = "Error";
        logs.exceptionMessage = ex.getMessage();
        logs.exceptionStackTrace = ex.getStackTrace().toString();
        logs.methodName = method;

        contentValues.put("CurrentDateTime", logs.currentDateTime);
        contentValues.put("ExceptionMsg", logs.exceptionMessage);
        contentValues.put("ExceptionStackTrace", logs.exceptionStackTrace);
        contentValues.put("MethodName", logs.methodName);
        contentValues.put("Type", logs.errorType);
        contentValues.put("GroupId", logs.groupId);
        contentValues.put("DeviceId", logs.deviceId);
        contentValues.put("LogDetail", "StudentLog");
        database.insert(ERRORTABLENAME, null, contentValues);
        database.close();
        BackupDatabase.backup(c);
    }*/

}