package game.client;

public class TextureStorage {

    //Engine: чем больше глубина, тем больше объектов будет отрисовыватсья поверх
    //Engine: объекты отрисовываются в порядке уменьшения глубины

	public static final String pathImageRoot = "res/image/"; //Engine: Путь к корню папки с текстурами
    public static final String[][] image = //Engine: Описание картинки (Путь от корня, тип объекта, глубина)
            {
                {"Background/grass.png",            "background",   "1000"},
                {"Background/sand.png",             "background",   "1000"},
                {"Background/snow.png",             "background",   "1000"},

                {"Backwall/road_g.png",             "road",         "100"},
                {"Backwall/road_g_fork.png",        "road",         "100"},
                {"Backwall/road_g_turn.png",        "road",         "100"},
                {"Backwall/road_g_inter.png",       "road",         "100"},
                {"Backwall/road_g_end.png",         "road",         "100"},
                {"Backwall/road_a.png",             "road",         "100"},
                {"Backwall/road_a_big.png",         "road",         "100"},
                {"Backwall/road_a_g.png",           "road",         "100"},
                {"Backwall/road_a_fork.png",        "road",         "100"},
                {"Backwall/road_a_fork_big.png",    "road",         "100"},
                {"Backwall/road_a_inter.png",       "road",         "100"},
                {"Backwall/road_a_d_big.png",       "road",         "100"},
                {"Backwall/road_a_g.png",           "road",         "100"},
                {"Backwall/road_d.png",             "road",         "100"},
                {"Backwall/road_d_fork.png",        "road",         "100"},
                {"Backwall/road_d_turn.png",        "road",         "100"},
                {"Backwall/road_d_inter.png",       "road",         "100"},
                {"Backwall/road_d_end.png",         "road",         "100"},
                {"Backwall/road_s.png",             "road",         "100"},
                {"Backwall/road_s_fork.png",        "road",         "100"},
                {"Backwall/road_s_turn.png",        "road",         "100"},
                {"Backwall/road_s_inter.png",       "road",         "100"},
                {"Backwall/road_s_end.png",         "road",         "100"},

                {"Bullet/b_default.png",            "bullet",       "40"},
                {"Bullet/b_steel.png",              "bullet",       "40"},
                {"Bullet/b_squaremass.png",         "bullet",       "40"},
                {"Bullet/b_fury.png",               "bullet",       "40"},
                {"Bullet/b_mass.png",               "bullet",       "40"},
                {"Bullet/b_mass_small.png",         "bullet",       "40"},
                {"Bullet/b_vampire.png",            "bullet",       "40"},
                {"Bullet/b_patron.png",             "bullet",       "40"},
                {"Bullet/b_streamlined.png",        "bullet",       "40"},

                {"Gun/g_default.png",               "gun",          "40"},
                {"Gun/g_double.png",                "gun",          "40"},
                {"Gun/g_fury.png",                  "gun",          "40"},
                {"Gun/g_mortar.png",                "gun",          "40"},
                {"Gun/g_rocketd.png",               "gun",          "40"},
                {"Gun/g_mass.png",                  "gun",          "40"},
                {"Gun/g_power.png",                 "gun",          "40"},
                {"Gun/g_kkp.png",                   "gun",          "40"},
                {"Gun/g_sniper.png",                "gun",          "40"},
                {"Gun/g_vampire.png",               "gun",          "40"},
                {"Gun/g_osmos.png",                 "gun",          "40"},

                {"Wall/home1.png",                  "home",         "80"},
                {"Wall/home2.png",                  "home",         "80"},
                {"Wall/home3.png",                  "home",         "80"},
                {"Wall/home4.png",                  "home",         "80"},
                {"Wall/home5.png",                  "home",         "80"},
                {"Wall/home6.png",                  "home",         "80"},
                {"Wall/home7.png",                  "home",         "80"},
                {"Wall/home8.png",                  "home",         "80"},
                {"Wall/home9.png",                  "home",         "80"},
                {"Wall/home10.png",                 "home",         "80"},
                {"Wall/home11.png",                 "home",         "80"},
                {"Wall/home12.png",                 "home",         "80"},
                {"Wall/home13.png",                 "home",         "80"},
                {"Wall/home14.png",                 "home",         "80"},
                {"Wall/factory1.png",               "home",         "80"},
                {"Wall/factory2.png",               "home",         "80"},

                {"Wall/tree.png",                   "tree",         "20"},
                {"Wall/tree_snow.png",              "tree",         "20"},
                {"Wall/tree_wither.png",            "tree",         "20"},
                {"Wall/tree_wither_snow.png",       "tree",         "20"},

                {"Gaming/box_armor.png",            "box",          "60"},
                {"Gaming/box_gun.png",              "box",          "60"},
                {"Gaming/box_bullet.png",           "box",          "60"},
                {"Gaming/box_health.png",           "box",          "60"},
                {"Gaming/box_healthfull.png",       "box",          "60"},

                {"Sys/sys_null.png",                "system",       "-1000"},
                {"Sys/cursor.png",                  "system",       "-1000"},
                {"Sys/sys_tank.png",                "system",       "-1000"},
                {"Sys/error.png",                   "system",       "-1000"},
                {"Sys/cursor_aim_1.png",            "system",       "-1000"},
                {"Sys/cursor_aim_2.png",            "system",       "-1000"}
            };


    public static final String pathAnimRoot = "res/animation/"; //Engine: Путь к корню папки с анимацией
    public static final String[][] anim = //Описание анимации (Путь от корня, тип объекта, глубина)
            {
                {"Corps/a_default",                 "armor",        "81"},
                {"Corps/a_fortified",               "armor",        "81"},
                {"Corps/a_elephant",                "armor",        "81"},
                {"Corps/a_fury",                    "armor",        "81"},
                {"Corps/a_mite",                    "armor",        "81"},
                {"Corps/a_vampire",                 "armor",        "81"}
            };
}
