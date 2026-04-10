package config;
public class Session {
    private static int uid;
    private static String fname;
    private static String lname;
    private static String email;
    private static String type;
    public static String currentRole;

    // Getters and Setters
    public static int getUid() { return uid; }
    public static void setUid(int uid) { Session.uid = uid; }

    public static String getFname() { return fname; }
    public static void setFname(String fname) { Session.fname = fname; }

    public static String getLname() { return lname; }
    public static void setLname(String lname) { Session.lname = lname; }

    public static String getEmail() { return email; }
    public static void setEmail(String email) { Session.email = email; }

    public static String getType() { return type; }
    public static void setType(String type) { 
        Session.type = type; 
        Session.currentRole = type; // keeps currentRole in sync automatically
    }

    public static void clearSession() {
        uid = 0;
        fname = null;
        lname = null;
        email = null;
        type = null;
        currentRole = null;
    }
}