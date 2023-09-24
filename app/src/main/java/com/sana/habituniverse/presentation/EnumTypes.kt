package com.sana.habituniverse.presentation


enum class ProgressLevel(val title: String) {
    LEVEL_1("태양"),
    LEVEL_2("수성"),
    LEVEL_3("금성"),
    LEVEL_4("지구"),
    LEVEL_5("화성"),
    LEVEL_6("목성"),
    LEVEL_7("토성"),
    LEVEL_8("천왕성"),
    LEVEL_9("해왕성"),
    LEVEL_10("우주")
}

enum class CheckState(val value: Int) {
    NOT_CHECKED(-1),
    FAIL(0),
    SUCCESS(1),
    SKIP(2)
}
