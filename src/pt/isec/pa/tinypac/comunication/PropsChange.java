package pt.isec.pa.tinypac.comunication;

public enum PropsChange {
    PROPS_GAME_BOARD("_game_board_"),
    PROPS_GAME_LIFE_STATUS_BAR("_game_life_status_bar_"),
    PROPS_GAME_TOP_MODAL("_game_modal_");
    private String name;
    PropsChange(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
