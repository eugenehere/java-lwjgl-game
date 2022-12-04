package lwjgl.gradle.engine;

public class Constants {
    public static float TIME_TO_CHANGE_SCENE = 0.2f;
    public static float MOVE_STEP = 0.01f;
    public static float BOT_MOVE_STEP = 0.005f;
    public static int BOT_COUNT = 100;
    public static float OBJECT_SIZE = 0.5f;
    public static float PLAYER_MOVEMENT_RANGE = 0.25f;
    public static int WINDOW_SIZE = 400;
    public static int SKINS_COUNT = 7;
    public static boolean ENABLE_HOUSE_RANDOM_ROTATION = false;

    public static String TEXTURE_PACK = "texture-pack-1";
    public static String[][] MAP_SCHEME = new String[][] { // 27 x 27
            new String[] {"17u", "17u",     "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u",     "17u", "17u"},
            new String[] {"17u", "17u",     "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u",     "17u", "17u"},

            new String[] {"17u", "17u",     "21u", "08u", "17u", "21u", "08u", "08u", "08u", "08u", "05r", "01r", "01r", "01r", "01r", "01r", "01r", "01r", "01r", "01r", "01r", "01r", "01r", "05d", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "08u", "02u", "08u", "08u", "05r", "01r", "05d", "08u", "01u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "01u", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "05r", "03u", "01r", "03d", "05l", "08u", "05u", "01r", "03u", "01r", "01r", "05d", "08u", "08u", "08u", "17u", "21u", "08u", "08u", "08u", "05r", "03u", "05d",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "17u", "08u", "08u", "08u", "08u", "08u", "01u", "08u", "02l", "05d", "08u", "08u", "05r", "01r", "01r", "03l", "21u", "01u",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "21u", "08u", "08u", "08u", "21u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "05u", "03d", "05l",     "17u", "17u"},
            new String[] {"17u", "17u",     "03r", "01r", "01r", "03l", "08u", "08u", "02l", "01r", "05d", "08u", "08u", "01u", "08u", "02l", "03l", "08u", "08u", "01u", "08u", "05r", "01r", "03l", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "17u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "01u", "08u", "01u", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "08u", "02l", "01r", "04u", "01r", "01r", "04u", "01r", "01r", "04u", "01r", "01r", "05l", "08u", "05u", "01r", "03u", "05d",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "08u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "01u",     "17u", "17u"},
            new String[] {"17u", "17u",     "02d", "08u", "08u", "03r", "01r", "01r", "01r", "01r", "03l", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "02u", "08u", "02u", "08u", "02u", "08u", "01u",     "17u", "17u"},
            new String[] {"17u", "17u",     "08u", "17u", "08u", "01u", "08u", "08u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "01u", "08u", "01u", "08u", "01u", "08u", "01u",     "17u", "17u"},
            new String[] {"17u", "17u",     "21u", "08u", "02l", "03l", "08u", "05r", "01r", "01r", "04u", "01r", "01r", "04u", "01r", "01r", "04u", "01r", "04u", "01r", "04u", "01r", "04u", "01r", "05l",     "17u", "17u"},
            new String[] {"17u", "17u",     "08u", "17u", "08u", "03r", "01r", "03l", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "01u", "08u", "01u", "08u", "01u", "08u", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "02u", "08u", "08u", "01u", "08u", "05u", "01r", "01r", "03l", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "02d", "08u", "02d", "08u", "02d", "08u", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "08u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "02u",     "17u", "17u"},
            new String[] {"17u", "17u",     "03r", "01r", "01r", "03l", "08u", "08u", "02l", "01r", "03u", "01r", "01r", "03u", "01r", "01r", "03u", "01r", "01r", "01r", "01r", "05d", "08u", "08u", "01u",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "21u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "01u", "08u", "08u", "01u",     "17u", "17u"},
            new String[] {"17u", "17u",     "01u", "08u", "08u", "01u", "08u", "08u", "08u", "17u", "17u", "08u", "21u", "17u", "08u", "21u", "08u", "08u", "08u", "08u", "05r", "03u", "05d", "08u", "01u",     "17u", "17u"},
            new String[] {"17u", "17u",     "05u", "03d", "01r", "03u", "01r", "01r", "05d", "08u", "08u", "02u", "08u", "08u", "02u", "08u", "02l", "03d", "01r", "01r", "03l", "21u", "03r", "01r", "03l",     "17u", "17u"},
            new String[] {"17u", "17u",     "08u", "01u", "08u", "08u", "08u", "08u", "01u", "08u", "08u", "03r", "01r", "01r", "03l", "08u", "08u", "01u", "08u", "08u", "05u", "03d", "05l", "08u", "02d",     "17u", "17u"},
            new String[] {"17u", "17u",     "08u", "01u", "08u", "08u", "08u", "08u", "01u", "08u", "08u", "01u", "08u", "08u", "03r", "01r", "01r", "05l", "08u", "08u", "08u", "01u", "08u", "08u", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "08u", "05u", "01r", "01r", "01r", "01r", "03u", "01r", "01r", "05l", "08u", "08u", "02d", "08u", "08u", "08u", "08u", "08u", "08u", "03r", "01r", "02r", "08u",     "17u", "17u"},
            new String[] {"17u", "17u",     "21u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "08u", "17u", "21u", "08u", "21u", "08u", "02l", "01r", "01r", "01r", "05l", "08u", "08u", "21u",     "17u", "17u"},

            new String[] {"17u", "17u",     "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u",     "17u", "17u"},
            new String[] {"17u", "17u",     "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u", "17u",     "17u", "17u"},
    };
}
